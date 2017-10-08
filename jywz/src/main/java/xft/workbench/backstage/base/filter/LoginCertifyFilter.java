package xft.workbench.backstage.base.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import xft.workbench.backstage.base.util.CookieUtil;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.UUIDGenerator;
import xft.workbench.backstage.user.dao.LoginManangerDao;
import xft.workbench.backstage.user.model.UserLoginInfo;

import com.kayak.web.base.system.Global;
import com.kayak.web.base.system.SysBeans;
import com.kayak.web.base.util.Tools;
import com.opensymphony.oscache.util.StringUtil;


public class LoginCertifyFilter implements Filter {
    protected static Logger log = Logger.getLogger(LoginCertifyFilter.class);
    private String unfilterPage;
    private String loginPage;
    private String loginAction;
    private String errorPage;
    private String noAuthPrefix;

    public static String[] unfilters;

    public void init(FilterConfig arg0) throws ServletException {
        unfilterPage = arg0.getInitParameter("unfilter-page");
        loginPage = arg0.getInitParameter("login-page");
        errorPage = arg0.getInitParameter("error-page");
        loginAction = arg0.getInitParameter("loginAction");
        noAuthPrefix = arg0.getInitParameter("no-auth-prefix");
        LoginCertifyFilter.unfilters = unfilterPage.split("[,]");
    }

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // doFilter方法的第一个参数为ServletRequest对象。
        // 此对象给过滤器提供了对进入的信息（包括表单数据、cookie和HTTP请求头）的完全访问。
        // 第二个参数为ServletResponse，通常在简单的过滤器中忽略此参数。
        // 最后一个参数为FilterChain，此参数用来调用servlet或JSP页。

        // 注：因为本Filter类是脱离struts2框架的，
        // 所以在本Filter类里，不能调用ActionContext来取得会话信息

        // 如果处理HTTP请求，并且需要访问诸如getHeader或getCookies等在ServletRequest中无法得到的方法，就要把此request对象构造成HttpServletRequest
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 取得根目录所对应的绝对路径
        String currentURL = httpRequest.getRequestURI();//  currentURL:/workbench_bs/qryLoginUserMenus.json?aa=bb
        //String servletPath= httpRequest.getServletPath();// servletPath:/workbench_bs/qryLoginUserMenus.json
        String contextPath = httpRequest.getContextPath(); // contextPath:/workbench_bs
        StringBuffer requestURL = httpRequest.getRequestURL();// requestURL:http://localhost:8080/workbench_bs/index.html
        String webURL = requestURL.substring(0, requestURL.indexOf(contextPath) + 1);
        if (StringUtil.isEmpty(GlobalMessage.LoginURL)) {
            GlobalMessage.LoginURL = webURL;
        }

        // 截取到当前文件名用于比较
        String targetURL =
                currentURL.substring(
                        currentURL.indexOf("/", 1) == -1 ? 1 : currentURL.indexOf("/",
                                1), currentURL.length());


        //判断请求是否检查，是否直接通过get请转入jsessionid;
        String sessionid = httpRequest.getParameter("jsessionid");

        //当sessionid为不为空时，检查，是否直接通过get请转入jsessionid;
        if (!StringUtil.isEmpty(sessionid)) {
            CookieUtil.addCookie(GlobalMessage.SESSIONID_KEY, sessionid, httpResponse);//将Cookie值写入当前域。
        } else {
            //判断请求是否有会话id,如果没有则直接为其创建一个会话ID.
            sessionid = CookieUtil.getCookie(GlobalMessage.SESSIONID_KEY, httpRequest);
        }


        if (StringUtil.isEmpty(sessionid) || loginAction.equals(targetURL)) {
            sessionid = UUIDGenerator.getUUID().replace("-", "");
            httpRequest.setAttribute(GlobalMessage.SESSIONID_KEY, sessionid);
            CookieUtil.addCookie(GlobalMessage.SESSIONID_KEY, sessionid, httpResponse);
        }


        boolean dofilter = true;// 是否做过滤

        if (loginPage.equals(targetURL) || errorPage.equals(targetURL)
                || loginPage.equals(currentURL) || errorPage.equals(currentURL)
                || targetURL.startsWith(noAuthPrefix)) {// 登录页和错误页都不做session的判断，防止出现死循环
            dofilter = false;
        } else {
            // 跳过unfilterPage配置的页面检查
            for (String page : unfilters) {
                String p = page.trim();
                if (p.equals(targetURL) || p.equals(currentURL)) {
                    dofilter = false;
                    break;
                }
            }
        }


        if (dofilter && (targetURL.contains(".json") || targetURL.contains("index.html"))) {
        	//判断此会话有没有进行登录。没有登录则返回登录页面。sessionid
            LoginManangerDao loginManangerDao = SysBeans.getBean("loginManangerDao");
            Integer sys_user_id = null;
            //有权限访问则用request保存用户常用信息。
            UserLoginInfo userLoginInfo = null;
            try {
                sys_user_id = loginManangerDao.queryLoginSession(sessionid);

                if (userLoginInfo == null && sys_user_id != null && !StringUtil.isEmpty(sys_user_id.toString())) {

                    userLoginInfo = loginManangerDao.queryUserById(sys_user_id);

                    if (userLoginInfo == null) {
                        // 跳转到登录页
                        this.loseLogin(httpRequest, httpResponse, "用户信息不存在或被锁，请确认后再进行登录操作！");
                        return;
                    }

                } else if (userLoginInfo == null) {
                    // 跳转到登录页
                    this.loseLogin(httpRequest, httpResponse, "您未登录，请先登录再进行操作。");
                    return;
                }
                httpRequest.setAttribute(GlobalMessage.REQUEST_INFO_KEY, userLoginInfo);


            } catch (Exception e) {
                // 跳转到登录页
                log.info("系统请求过滤异常:" + e.getMessage());
                try {
                    this.loseLogin(httpRequest, httpResponse, "系统异常，请重新登录！");
                    return;
                } catch (Exception e1) {

                }

            }
        }


        // 加入filter链继续向下执行
        chain.doFilter(request, response);// 调用FilterChain对象的doFilter方法。Filter接口的doFilter方法取一个FilterChain对象作为它的一个参数。在调用此对象的doFilter方法时，激活下一个相关的过滤器。如果没有另一个过滤器与servlet或JSP页面关联，则servlet或JSP页面被激活。

    }


    private void loseLogin(HttpServletRequest httpRequest, HttpServletResponse response, String msg) throws Exception {
        Map<String, Object> msgMap = new HashMap<String, Object>();
        //平台类型：-1开发、1工厂、2实验室。
        String systemType = Global.getGlobalConf(GlobalMessage.SYSTEM_TYPE, "-1");
        //当为生产环境时，
        String loginUrl = httpRequest.getContextPath() + this.loginPage;


        msgMap.put("success", "false");
        msgMap.put("isLogin", "false");
        msgMap.put("returnmsg", msg);
        msgMap.put("loginUrl", loginUrl);
        msgMap.put("systemType", systemType);

        String servletPath = httpRequest.getServletPath();

        if (servletPath.contains("index.html")) {
            response.sendRedirect(loginUrl
                    + "?msg="
                    + Tools.urlEncode(msg));
        } else {
            OutputStream out = response.getOutputStream();
            out.write((new JSONObject(msgMap).toString())
                    .getBytes(Global.charset()));
            out.close();
        }
    }
}

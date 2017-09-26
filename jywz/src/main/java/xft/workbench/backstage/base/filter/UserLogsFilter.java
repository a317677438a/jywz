package xft.workbench.backstage.base.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import xft.workbench.backstage.base.util.CookieUtil;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.user.dao.SysUserLogsDao;
import xft.workbench.backstage.user.model.SysUserLogs;
import xft.workbench.backstage.user.model.UserLoginInfo;

import com.kayak.web.base.system.SysBeans;

@Component
public class UserLogsFilter implements Filter {
    protected static Logger log = Logger.getLogger(UserLogsFilter.class);
    private static ThreadLocal<String> requestParamsThreadLocal = new ThreadLocal<String>();
    private static List<String> requestmappingList = null;

    public static String getRequestParams() {
        try {
            return UserLogsFilter.requestParamsThreadLocal.get();
        } catch (Exception e) {
            return null;
        }

    }

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        SysUserLogs sysUserLogs = null;
        try {
            SysUserLogsDao sysUserLogsDao = SysBeans.getBean("sysUserLogsDao");

            String contentType = request.getContentType();

            if (contentType != null && (contentType.contains("application/json")
                    || contentType.contains("multipart/form-data"))) {
                //客户端IP
                String ipAddr = this.getIpAddr(request);
                //系统请求参数。
                String requestParams = null;
                if (contentType.contains("application/json"))
                    requestParams = this.getRequestParams(request);

                if (contentType.contains("multipart/form-data"))
                    requestParams = "{file}";//默认请求参数


                requestParamsThreadLocal.set(requestParams);//设置请求参数

                //得到用户信息
                UserLoginInfo userLoginInfo = (UserLoginInfo) request.getAttribute(GlobalMessage.REQUEST_INFO_KEY);

                //得到用户请求uri  比如: /workbench_bs/abslogin.json
                String uri = request.getRequestURI();
                String reqMapping = uri.substring(uri.indexOf("/", 1));
                if (requestmappingList == null) {
                    requestmappingList = sysUserLogsDao.queryRequestMapping();
                }
                //不需要记录用户日志时则直接跳过
                if (StringUtils.isNotEmpty(requestParams) && !"{}".equals(requestParams.trim()) &&
                        userLoginInfo != null &&
                        requestmappingList != null && requestmappingList.contains(reqMapping)) {
                    //需要记录用户日志。
                    //用户会话ID
                    String sessionid = CookieUtil.getCookie(GlobalMessage.SESSIONID_KEY, request);

                    sysUserLogs = new SysUserLogs(sessionid,
                            userLoginInfo.getId().toString(), userLoginInfo.getLoginname(),
                            reqMapping, requestParams, ipAddr, null, null);
                    sysUserLogsDao.addUserLogs(sysUserLogs);

                } else {
                    //	chain.doFilter(req, res);
                }
            }
        } catch (Exception e) {
            //出现系统异常问题，不进行处理。
            log.error("记录用户操作日志失败：" + (sysUserLogs == null ? "" : sysUserLogs.toString()));
        }

        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }


    /**
     * 获取客户端IP地址
     *
     * @param request
     * @return
     */
    private static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress != null && ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (ipAddress != null) {
                    ipAddress = inet.getHostAddress();
                }
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


    /**
     * 得到请求参数
     */
    @SuppressWarnings("rawtypes")
    protected String getRequestParams(HttpServletRequest localRequest) throws Exception {

        Map<String, Object> map = null;
        String contentType = localRequest.getContentType();
        String requestURI = localRequest.getRequestURI();
        boolean isAPI = requestURI.contains("/api/");

        if (contentType != null && contentType.contains("application/json")&&!isAPI) {

            BufferedReader reader = localRequest.getReader();
            StringBuffer buffer = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            return buffer.toString();

        }

        return "";

    }


}

package xft.workbench.backstage.base.util;

import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import xft.workbench.backstage.user.model.UserLoginInfo;

import com.kayak.web.base.system.Global;
import com.kayak.web.base.system.RequestSupport;
import com.opensymphony.oscache.util.StringUtil;

public class GlobalMessage {
    protected static Logger log = Logger.getLogger(GlobalMessage.class);
    /**
     * 保存在Cookie中处理会话的关键字:用户会话
     */
    public static final String SESSIONID_KEY = "C_USER";


  
    /**
     * 保存在Cookie中处理会话的关键字：平台号ID
     */
    public static final String COOKIES_PLATFORM_ID = "PLATFORM_ID";

    /**
     * 保存在Cookie中处理会话的关键字
     */
    public static final String REQUEST_INFO_KEY = "requset.info.key";
    /**
     * 用户密码录入次数。
     */
    public static final String PWD_ERR_TIMES = "Global.pwd_err_times";
    /**
     * 管理员配制。
     */
    public static final String ADMIN_LOGIN = "Global.admin_login";

    /**
     * 平台类型：-1开发、-2测试、-3云测试、1生产
     */
    public static final String SYSTEM_TYPE = "Global.SYSTEM_TYPE";


    /**
     * 系统登录页面。当系统与其他网站集成的时候，用的登录页面。
     */
    public static final String LOGIN_URL = "Global.login_url";


    /**
     * 不同平台类型时，加载的不同参数。
     */
    public static Properties param_props = new Properties();

    /**
     * 不同平台类型时，加载的不同web平台登录URL。
     */
    public static String LoginURL = null;

   
    /**
     * 导入时的线程数量
     */
    public static final String THREAD_NUM = "Global.THREAD_NUM";

    /**
     * 一键同步、自动同步文件上传路径
     */
    private static final String AUTO_UPDATE_PATH = "Global.AUTO_UPDATE_PATH";

    /**
     * 将用户登录会话信息加入map中去。
     *
     * @param map
     */
    public static void addMapSessionInfo(Map<String, Object> map) throws Exception {
        UserLoginInfo userLoginInfo = GlobalMessage.getSessionInfo();
        String admin = Global.getGlobalConf(GlobalMessage.ADMIN_LOGIN, "admin");
        map.put("sys_user_id", userLoginInfo.getId());
        map.put("loginname", userLoginInfo.getLoginname());
        map.put("sys_org_id", userLoginInfo.getSys_org_id());
        map.put("orgtype", userLoginInfo.getOrgtype());
        map.put("isadmin", admin.equals(userLoginInfo.getLoginname()));//是否为管理员。
    }


    /**
     * 将用户登录会话信息加入map中去。
     */
    public static Boolean isadmin() throws Exception {
        UserLoginInfo userLoginInfo = GlobalMessage.getSessionInfo();
        String admin = Global.getGlobalConf(GlobalMessage.ADMIN_LOGIN, "admin");
        return admin.equals(userLoginInfo.getLoginname());
    }

    /**
     * 获得用户登录会话信息。
     *
     * @return
     * @throws Exception
     */
    public static UserLoginInfo getSessionInfo() throws Exception {
        HttpServletRequest httpRequest = RequestSupport.getLocalRequest();
        UserLoginInfo userLoginInfo = (UserLoginInfo) httpRequest.getAttribute(GlobalMessage.REQUEST_INFO_KEY);
        return userLoginInfo;
    }


    /**
     * 获得管理员登录名信息。
     *
     * @return
     * @throws Exception
     */
    public static String getAdminName() throws Exception {
        return Global.getGlobalConf(GlobalMessage.ADMIN_LOGIN, "admin");
    }


    /**
     * 获得登录URL。
     *
     * @return
     */
    public static String getLoginURL() {
        /*if(!StringUtil.isEmpty(LoginURL)){
            return 	LoginURL;
		}*/

        String loginURL = Global.getGlobalConf(GlobalMessage.LOGIN_URL, "");
        if (StringUtil.isEmpty(loginURL)) {
            loginURL = GlobalMessage.param_props.getProperty(GlobalMessage.LOGIN_URL, "");
        }
        return loginURL;
    }


    /**
     * 平台类型：-1开发
     *
     * @return
     */
    public static String getSystemType() {
        String systemType = Global.getGlobalConf(GlobalMessage.SYSTEM_TYPE, "-1");
        return systemType;
    }


    /**
     * 获得用户登录的平台号信息。
     *
     * @return 没有则返回默认值：10000，
     * @throws Exception
     */
    public static String getPlatformId() throws Exception {
        HttpServletRequest httpRequest = RequestSupport.getLocalRequest();
        String platformid = CookieUtil.getCookie(GlobalMessage.COOKIES_PLATFORM_ID, httpRequest);
        return StringUtils.isEmpty(platformid) ? "10000" : platformid;
    }

   
}

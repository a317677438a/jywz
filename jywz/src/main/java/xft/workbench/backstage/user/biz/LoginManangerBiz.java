package xft.workbench.backstage.user.biz;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xft.workbench.backstage.base.util.CookieUtil;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.user.dao.LoginManangerDao;
import xft.workbench.backstage.user.model.UserLoginInfo;

import com.kayak.web.base.exception.KPromptException;
import com.kayak.web.base.system.RequestSupport;
import com.opensymphony.oscache.util.StringUtil;

@Service
public class LoginManangerBiz {
	
	@Resource
	private LoginManangerDao loginManangerDao;
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserLoginInfo login(UserLoginInfo userLoginInfo )
			throws KPromptException , Exception {
		/**
		 * 1、查询会话id是否登录，如果登录且用户有效则返回不需要进行登录
		 * 2、进行用户登录名和密码验证后如果用户有效并存入会话id登录信息。
		 */
		HttpServletRequest httpRequest=RequestSupport.getLocalRequest();
		String sessionid = (String)httpRequest.getAttribute(GlobalMessage.SESSIONID_KEY);
		if(StringUtil.isEmpty(sessionid)){
			throw new KPromptException("会话异常，请重新录入用户名和密码后进行登录！");
		}
		
		
		//对用户名和密码验证。
		UserLoginInfo userInfo = loginManangerDao.queryUserByLoginname(userLoginInfo.getLoginname());
		if(userInfo==null || userInfo.getId()==null){
			throw new KPromptException("请输入正确的用户名或密码！");
		}else{
			if(!userInfo.getPasswd().equals(userLoginInfo.getPasswd())){
				//登录密码密码错误
				throw new KPromptException("密码错误！请输入正确的密码！");
			}
		}
		
		//判断用户的有效性。
		if(!loginManangerDao.queryUserState(userInfo.getId().toString())){
			throw new KPromptException("该用户或用户对应的机构已经处于停用状态，请恢复后再进行登录！");
		}
		
		
		//设置其他用户会话信息失效。
		loginManangerDao.setUserOnlineLose(userInfo.getId());
		
		//登录成功，记录登录新会话信息。
		userInfo.setId(userInfo.getId());
		userInfo.setSessionid(sessionid);
		userInfo.setLoginip(LoginManangerBiz.getIpAddr(httpRequest));
		loginManangerDao.addUserOnline(userInfo);
		
		return userInfo;
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void logout()
			throws KPromptException , Exception {
		
		/**
		 * 1、根据注销会话id,注销会话。
		 */
		HttpServletRequest httpRequest=RequestSupport.getLocalRequest();
		String sessionid = CookieUtil.getCookie(GlobalMessage.SESSIONID_KEY, httpRequest);
		loginManangerDao.cancelLoginSession(sessionid);
		
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
			if (ipAddress !=null && ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				if(ipAddress!=null){
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
	
	
}

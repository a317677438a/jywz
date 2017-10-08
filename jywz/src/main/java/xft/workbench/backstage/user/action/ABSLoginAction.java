package xft.workbench.backstage.user.action;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.util.MD5Util;
import xft.workbench.backstage.user.biz.LoginManangerBiz;
import xft.workbench.backstage.user.biz.UserInfoManagerBiz;
import xft.workbench.backstage.user.model.UserInfo;
import xft.workbench.backstage.user.model.UserLoginInfo;

import com.kayak.web.base.service.abs.ComnServiceAbstract;


@Controller
public class ABSLoginAction extends ABSBaseController {
	protected static Logger log = Logger.getLogger(ABSLoginAction.class);
	@Resource
	private LoginManangerBiz loginManangerBiz;
	
	@Resource
	private UserInfoManagerBiz userInfoManagerBiz;
	
	@Resource
	private ComnServiceAbstract comnService;

	
	@RequestMapping(value = "/abslogin.json")
	public  @ResponseBody String  abslongin(){
		Map<String, Object> map = null; 
		Map<String, Object> userMap = new HashMap<String, Object>();
		try {
			map = this.getRequestParams();
			
			UserLoginInfo userLoginInfo = new UserLoginInfo();
			userLoginInfo.setLoginname(map.get("loginname").toString());
			userLoginInfo.setPasswd(MD5Util.toMD5(map.get("loginname").toString() + map.get("passwd").toString()));
			
			UserLoginInfo userLoginInfo2 = loginManangerBiz.login(userLoginInfo);
			userMap.put("id", userLoginInfo2.getId());//用户ID
			userMap.put("loginname", userLoginInfo2.getLoginname());//登录名
			userMap.put("role", userLoginInfo2.getRole());//角色
			userMap.put("name", userLoginInfo2.getName());//用户名称
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "登录成功", userMap);
	}
	
	
	
	
	/**
	 * 用户注销
	 */
	@RequestMapping(value = "/abslogout.json")
	public  @ResponseBody String logout(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			loginManangerBiz.logout();
			
			//String url = request.getContextPath() + "/views/login.html";
			//response.sendRedirect(url);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
		return this.returnUpdate(true, "退出成功", null);
	}
	
}


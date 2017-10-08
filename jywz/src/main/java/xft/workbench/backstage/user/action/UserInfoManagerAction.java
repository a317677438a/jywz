package xft.workbench.backstage.user.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.enumeration.user.UserStatus;
import xft.workbench.backstage.base.enumeration.user.UserType;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.user.biz.UserInfoManagerBiz;
import xft.workbench.backstage.user.model.UserInfo;
import xft.workbench.backstage.user.model.UserLoginInfo;

import com.kayak.web.base.service.abs.ComnServiceAbstract;
import com.kayak.web.base.system.Global;
import com.kayak.web.base.system.KResult;
import com.kayak.web.base.system.RequestSupport;



@Controller
public class UserInfoManagerAction extends ABSBaseController {
	
	@Resource(name="userInfoManagerBiz")
	private UserInfoManagerBiz userInfoManagerBiz;
	
	@Resource
	private ComnServiceAbstract comnService;

	/**
	 * 用户信息新增
	 * @return
	 */
	@RequestMapping(value = "/user/userAdd.json")
	public @ResponseBody String userAdd(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数
			
			UserInfo userInfo = new UserInfo();
			
			ObjectMapUtil.setObjectFileValue(userInfo, param);
			
			userInfo.setStatus(UserStatus.run.getValue().toString());//用户状态默认设置为"正常"
			
			userInfoManagerBiz.userAdd(userInfo);
			
			return this.updateReturnJson(true, "新增成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	
	/**
	 * 用户信息修改
	 * @return
	 */
	@RequestMapping(value = "/user/userModify.json")
	public @ResponseBody String userModify(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数

			UserInfo userInfo = new UserInfo();
			ObjectMapUtil.setObjectFileValue(userInfo, param);
			userInfoManagerBiz.userModify(userInfo);
			
			return this.updateReturnJson(true, "修改成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	
	/**
	 * 密码重置
	 * @return
	 */
	@RequestMapping(value = "/user/resetPasswd.json")
	public @ResponseBody String resetPasswd(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数

			userInfoManagerBiz.resetPasswd(param);
			
			return this.updateReturnJson(true, "修改成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}	
	
	/**
	 * 用户停用服务
	 * @return
	 */
	@RequestMapping(value = "/user/stopUserService.json")
	public @ResponseBody String stopUserService(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数

			Integer id = Integer.parseInt(param.get("id").toString());
			userInfoManagerBiz.stopUserService(id);
			
			return this.updateReturnJson(true, "设置成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}	

	/**
	 * 用户启用服务
	 * @return
	 */
	@RequestMapping(value = "/user/startUserService.json")
	public @ResponseBody String startUserService(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数

			Integer id = Integer.parseInt(param.get("id").toString());
			userInfoManagerBiz.startUserService(id);
			
			return this.updateReturnJson(true, "设置成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}	
	
	/**
	 * 用户信息列表查询
	 * @return
	 */
	@RequestMapping(value = "/user/userListQuery.json")
	public @ResponseBody String userListQuery() {
		try {
			Map<String, Object> map = this.getRequestParams();
			//传入参数可以start，limit可能不是字符串而是整形时，导致生成map后变成DOUBLE类型。
			if(map.containsKey("start") && map.containsKey("limit") ){
				map.put("start", Double.valueOf(map.get("start").toString()).intValue());
				map.put("limit", Double.valueOf(map.get("limit").toString()).intValue());
			}
			KResult result = comnService.comnQuery(map);
			return this.updateReturnJson(true, "查询成功", RequestSupport.result2JsonList(result));
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	
	
	
	/**
	 * 删除用户信息（真删除）
	 * @return
	 */
	@RequestMapping(value="/user/userDelete.json")
	public @ResponseBody String userDelete(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数

			Integer sys_user_id = Integer.parseInt(param.get("id").toString());
			
			userInfoManagerBiz.userDelete(sys_user_id);
			
			return this.updateReturnJson(true, "删除成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
		
	}
	
	/**
	 * 查询仓库员存在的仓库编码。
	 * @return
	 */
	
	@RequestMapping(value="/user/haveStorehouseCode.json")
	public @ResponseBody String haveStorehouseCode(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数

			Integer sys_user_id = null;
			if(param.containsKey("id")){
				 sys_user_id = Integer.parseInt(param.get("id").toString());
			}else {
				UserLoginInfo userLoginInfo = GlobalMessage.getSessionInfo();
				sys_user_id = userLoginInfo.getId();
			}
			
			List<String> resultsList=userInfoManagerBiz.haveStorehouseCode(sys_user_id);
			
			return this.updateReturnJson(true, "查询成功", resultsList);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	
	/**
	 * 查询其他仓库员有的仓库编码。
	 * @return
	 */
	
	@RequestMapping(value="/user/otherHaveStorehouseCode.json")
	public @ResponseBody String otherHaveStorehouseCode(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数
			Integer sys_user_id = null;
			if(param.containsKey("id")){
				 sys_user_id = Integer.parseInt(param.get("id").toString());
			}else {
				UserLoginInfo userLoginInfo = GlobalMessage.getSessionInfo();
				sys_user_id = userLoginInfo.getId();
			}
			
			
			List<String> resultsList=userInfoManagerBiz.otherHaveStorehouseCode(sys_user_id);
			
			return this.updateReturnJson(true, "查询成功", resultsList);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	
	/**
	 * 设置仓库员的仓库编码
	 * @return
	 */
	
	
	@RequestMapping(value="/user/setStorehouseCode.json")
	public @ResponseBody String setStorehouseCode(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数
			
			List<String> storehouseCodes = (List<String>)param.get("storehouseCodes");
			Integer sys_user_id = null;
			if(param.containsKey("id")){
				 sys_user_id = Integer.parseInt(param.get("id").toString());
			}else {
				UserLoginInfo userLoginInfo = GlobalMessage.getSessionInfo();
				sys_user_id = userLoginInfo.getId();
			}
			
			userInfoManagerBiz.setStorehouseCode(sys_user_id, storehouseCodes);
			
			return this.updateReturnJson(true, "设置成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	
	
	
	/**
	 * 查询仓库员存在的仓库编码的下拉列表。
	 * @return
	 */
	
	@RequestMapping(value="/user/haveStorehouseCodeList.json")
	public @ResponseBody String haveStorehouseCodeList(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数

			Integer sys_user_id = null;
			if(param.containsKey("id")){
				 sys_user_id = Integer.parseInt(param.get("id").toString());
			}else {
				UserLoginInfo userLoginInfo = GlobalMessage.getSessionInfo();
				sys_user_id = userLoginInfo.getId();
			}
			
			JSONArray resultsList=userInfoManagerBiz.haveStorehouseCodeJSON(sys_user_id);
			
			return this.updateReturnJson(true, "查询成功", resultsList);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	
	
}

package xft.workbench.backstage.user.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import xft.workbench.backstage.base.enumeration.OpeStatusEnum;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.user.model.MenuDao;
import xft.workbench.backstage.user.model.UserLoginInfo;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.exception.KPromptException;
import com.kayak.web.base.exception.KSqlException;
import com.kayak.web.base.exception.KSystemException;
import com.kayak.web.base.sql.SqlException;
import com.kayak.web.base.sql.SqlResult;
import com.opensymphony.oscache.util.StringUtil;

@Repository
public class LoginManangerDao {
	protected static Logger log = Logger.getLogger(LoginManangerDao.class);
	@Resource
	private ComnDao comnDao;
	
	/**
	 * 取会话id 对应的有效用户。
	 * 
	 * @param sessionid 会话id
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public Integer queryLoginSession(String sessionid)
			throws KPromptException, Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("jsstatus",OpeStatusEnum.start.getValue());
		params.put("sessionid", sessionid);
		SqlResult sr = comnDao.query("MS0000EQ001", params);
		if(sr.next()){
			return sr.getInteger("sys_user_id");
		}
		return null;
	}
	
	public List<MenuDao> getLoginUserRoleMenus() throws Exception{
		List<MenuDao> menus = new ArrayList<MenuDao>();
		Map<String,Object> params = new HashMap<String,Object>();
		GlobalMessage.addMapSessionInfo(params);
		params.put("status", OpeStatusEnum.start.getValue());
		SqlResult sr = comnDao.query("MS0000EQ010", params);
		while(sr.next()){
			MenuDao  menu = new MenuDao();
			ObjectMapUtil.sqlResultToObject(sr.getRow(), menu);
			menus.add(menu);
		}
		return menus;
	}
	
	
	/**
	 * 根据会话id注销会话，
	 * 
	 * @param sessionid 会话id
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public void cancelLoginSession(String sessionid)
			throws KPromptException, Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("jsstatus", OpeStatusEnum.stop.getValue());
		params.put("sessionid", sessionid);
		comnDao.exeUpdate("MS0000EM001", params);
	}
	
	/**
	 * 查询用户的的有效性.主要从用户和机构状态查询：当都为正常时才有效。
	 * 
	 * @param sys_user_id 用户id
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public Boolean queryUserState(String sys_user_id)
			throws KPromptException, Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sys_user_id", sys_user_id);
		SqlResult sr = comnDao.query("MS0000EQ002", params);
		if(sr.next()){
			int userState= sr.getInteger("userstatus");
			int orgState= sr.getInteger("org_status");
			if(OpeStatusEnum.start.getValue()==userState && 
					OpeStatusEnum.start.getValue()==orgState){
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * 查询用户的的登录名和密码等信息
	 * 
	 * @param loginname 登录名
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public UserLoginInfo queryUserByLoginname(String loginname)
			throws KPromptException, Exception {
		UserLoginInfo userLoginInfo = null;
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginname", loginname);
		SqlResult sr = comnDao.query("MS0000EQ003", params);
		
		if(sr.next()){
			userLoginInfo = new UserLoginInfo();
			userLoginInfo.setId(sr.getInteger("id"));
			userLoginInfo.setLoginname(sr.getString("loginname"));
			userLoginInfo.setPasswd(sr.getString("passwd"));
			userLoginInfo.setPwderrtimes(sr.getInteger("pwderrtimes"));
			return userLoginInfo;
		}
		return null;
	}
	
	
	
	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param sys_user_id 用户id
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public UserLoginInfo queryUserById(Integer sys_user_id)
			throws KPromptException, Exception {
		UserLoginInfo userLoginInfo = new UserLoginInfo();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sys_user_id", sys_user_id);
		params.put("userstatus", OpeStatusEnum.start.getValue());
		SqlResult sr = comnDao.query("MS0000EQ004", params);
		//将用户信息封装到对象UserLoginInfo中。
		userLoginInfo=(UserLoginInfo)ObjectMapUtil.sqlResultToObject(sr, userLoginInfo);
		
		return userLoginInfo;
	}
	
	/**
	 * 根据loginname查询用户信息
	 * 
	 * @param loginname 登录名
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public UserLoginInfo queryUserInfoByLoginname(String loginname)
			throws KPromptException, Exception {
		UserLoginInfo userLoginInfo = new UserLoginInfo();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginname", loginname);
		params.put("userstatus", OpeStatusEnum.start.getValue());
		SqlResult sr = comnDao.query("MS0000EQ009", params);
		//将用户信息封装到对象UserLoginInfo中。
		userLoginInfo=(UserLoginInfo)ObjectMapUtil.sqlResultToObject(sr, userLoginInfo);
		
		return userLoginInfo;
	}
	
	/**
	 * 密码输入错误次数清零
	 * 
	 * @param loginname 登录名
	 * @throws KSqlException
	 * @throws SqlException
	 * @throws SQLException
	 * @throws KSystemException
	 */
	public void clearUserPwderr(String loginname)
			throws KSqlException, KSystemException, SQLException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginname", loginname);
		comnDao.sqlUpdate("UPDATE sys_user SET pwderrtimes=0,pwderrlockdt='' "
				+ "WHERE loginname=$S{loginname}", null, params);
	}

	/**
	 * 密码输入错误次数累加
	 * 
	 * @param loginname 登录名
	 * @throws KSqlException
	 * @throws SqlException
	 * @throws SQLException
	 * @throws KSystemException
	 */
	public void addUserPwderrtimes(String loginname)
			throws KSqlException, KSystemException, SQLException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginname", loginname);
		comnDao.sqlUpdate("UPDATE sys_user SET pwderrtimes=pwderrtimes+1 "
				+ "WHERE loginname=$S{loginname}", null, params);
	}
	
	/**
	 * 设置用户的会话失效。
	 * @param sys_user_id
	 * @throws Exception
	 */
	public void setUserOnlineLose(Integer sys_user_id) throws Exception{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sys_user_id", sys_user_id);
		params.put("jsstatus", OpeStatusEnum.stop.getValue());
		comnDao.exeUpdate("MS0000EM002", params);
	}
	
	
	/**
	 * 增加用户登录会话记录。
	 * 
	 * @param loginname 登录名
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public void addUserOnline(UserLoginInfo userLoginInfo)
			throws KPromptException, Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sessionid", userLoginInfo.getSessionid());
		params.put("sys_user_id", userLoginInfo.getId());
		params.put("loginip", userLoginInfo.getLoginip());
		comnDao.exeUpdate("MS0000EA001", params);
	}
	
	/**
	 * 增加其他系统用户登录会话记录。
	 * 
	 * @param loginname 登录名
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public void addUserOnlineOther(String sessionid,String loginid)
			throws KPromptException, Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sessionid", sessionid);
		params.put("loginid", loginid);
		comnDao.exeUpdate("MS0000EA002", params);
	}
	
	/**
	 * 锁住用户
	 * 
	 * @param sys_user_id 用户id
	 * @throws KSqlException
	 * @throws SqlException
	 * @throws SQLException
	 * @throws KSystemException
	 */
	public void lockUser(Integer sys_user_id)
			throws KSqlException, KSystemException, SQLException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sys_user_id", sys_user_id);
		comnDao.sqlUpdate("UPDATE sys_user SET userstatus="+ OpeStatusEnum.stop.getValue() 
				+ " WHERE id=$S{sys_user_id}", null, params);
	}
	
	
	
	
	/**
	 * 查询用户的的有效菜单。
	 * 
	 * @param sys_user_id 用户id
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public List<MenuDao> queryUserMenus()
			throws KPromptException, Exception {
		List<MenuDao> menus = new ArrayList<MenuDao>();
		
		Map<String,Object> params = new HashMap<String,Object>();
		GlobalMessage.addMapSessionInfo(params);
		params.put("status", OpeStatusEnum.start.getValue());
		SqlResult sr = comnDao.query("MS0000EQ005", params);
		while(sr.next()){
			MenuDao  menu = new MenuDao();
			ObjectMapUtil.sqlResultToObject(sr.getRow(), menu);
			menus.add(menu);
		}
		return menus;
	}
	
	/**
	 * 根据用户所属机构类型的查询权限
	 */
	public List<MenuDao> queryUserMenusByOrgType()
			throws KPromptException, Exception {
		List<MenuDao> menus = new ArrayList<MenuDao>();
		
		Map<String,Object> params = new HashMap<String,Object>();
		GlobalMessage.addMapSessionInfo(params);
		SqlResult sr = comnDao.query("MS0000EQ014", params);
		while(sr.next()){
			MenuDao  menu = new MenuDao();
			ObjectMapUtil.sqlResultToObject(sr.getRow(), menu);
			menus.add(menu);
		}
		return menus;
	}
	
	/**
	 * 根据用户所占有的角色来查询权限
	 * 
	 * @param sys_user_id 用户id
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public List<MenuDao> queryUserMenusByRole()
			throws KPromptException, Exception {
		List<MenuDao> menus = new ArrayList<MenuDao>();
		
		Map<String,Object> params = new HashMap<String,Object>();
		GlobalMessage.addMapSessionInfo(params);
		SqlResult sr = comnDao.query("MS0000EQ015", params);
		while(sr.next()){
			MenuDao  menu = new MenuDao();
			ObjectMapUtil.sqlResultToObject(sr.getRow(), menu);
			menus.add(menu);
		}
		return menus;
	}
	
	
	/**
	 * 查询所有权限
	 * 
	 * @param sys_user_id 用户id
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public List<MenuDao> queryAllMenus()
			throws KPromptException, Exception {
		List<MenuDao> menus = new ArrayList<MenuDao>();
		
		SqlResult sr = comnDao.query("MS0000EQ016", null);
		while(sr.next()){
			MenuDao  menu = new MenuDao();
			ObjectMapUtil.sqlResultToObject(sr.getRow(), menu);
			menus.add(menu);
		}
		return menus;
	}
	
	
	/**
	 * 检查用户访问请求是否的的有效。
	 * 
	 * @param sys_user_id 用户id
	 * @param requstmapping
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public Boolean checkUserMenu(Integer sys_user_id,String requestmapping)
			throws KPromptException, Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sys_user_id", sys_user_id);
		params.put("status", OpeStatusEnum.start.getValue());
		params.put("requestmapping", requestmapping);
		SqlResult sr = comnDao.query("MS0000EQ006", params);
		if(sr.next()){
			Integer numa=sr.getInteger("numa");
			if(numa !=null && numa>0){
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/**
	 * 根据会话id注销会话，
	 * 
	 * @param sessionid 会话id
	 * @return
	 * @throws KPromptException
	 * @throws Exception
	 */
	public String  qryLoginid(String sessionid)
			throws KPromptException, Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sessionid", sessionid);
		SqlResult sr = comnDao.query("MS0000EQ011", params);
		if(sr.next()){
			String loginid=sr.getString("loginid");
			if(StringUtil.isEmpty(loginid)){
				return null;
			}else{
				return loginid;
			}
		}
		
		return null;
	}
	
	
	/**
	 * 
	* 描述 : 查询用户和项目是否存在
	*<p>                                                  
	            樊东新                                                                                                                                                                                          
	* @param loginName
	* @return
	* @throws Exception
	 */
	public boolean userIsExits(String loginName) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginname", loginName);
		SqlResult sqlResult = comnDao.exeQuery("MS0000EQ007", params);
		Integer num = sqlResult.getRow(0).getInteger("num");
		if (num !=null && num > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	* 描述 : 查询机构是否存在
	*<p>                                                  
	          樊东新                                                                                                                                                                                            
	* @return
	* @throws Exception
	 */
	public boolean OrgIsExits() throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		SqlResult sqlResult = comnDao.exeQuery("MS0000EQ008", params);
		Integer num = sqlResult.getRow(0).getInteger("num");
		if (num !=null && num > 0) {
			return true;
		}
		return false;
	}
	
}

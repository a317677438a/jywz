package xft.workbench.backstage.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.user.model.UserInfo;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.exception.KPromptException;
import com.kayak.web.base.exception.KSqlException;
import com.kayak.web.base.exception.KSystemException;
import com.kayak.web.base.sql.SqlResult;


@Repository(value = "userInfoManagerDao")
public class UserInfoManagerDao extends ComnDao{
	
	public void addUserInfo(UserInfo userInfo) throws Exception{
		
		Map<String, Object> param = ObjectMapUtil.getFieldVlaue2(userInfo);
		
		exeUpdate("MS0002EU01", param);
	}

	public void modifyUserInfo(UserInfo userInfo) throws Exception {
		
		Map<String, Object> param = ObjectMapUtil.getFieldVlaue2(userInfo);
		
		exeUpdate("MS0002EU02", param);
	}

	public void modifyPasswd(UserInfo userInfo) throws Exception {
		
		Map<String, Object> param = ObjectMapUtil.getFieldVlaue2(userInfo);
		
		exeUpdate("MS0002EU03", param);	
	}

	public void manageUserService(Integer id,Integer userstatus) throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("status", userstatus);
		
		exeUpdate("MS0002EU04", param);	
	}

	public void setUserRight(int sys_user_id, int sys_menu_detail_id) throws KPromptException, KSqlException, SQLException, KSystemException {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("sys_user_id", sys_user_id);
		param.put("sys_menu_detail_id", sys_menu_detail_id);
		
		exeUpdate("MS0002EU05", param);
	}

	public void delUserRight(int sys_user_id) throws KPromptException, KSqlException, SQLException, KSystemException {
		
		Map<String,Object> param = new HashMap<String, Object>();
		
		param.put("sys_user_id", sys_user_id);
		
		exeUpdate("MS0002EU06", param);
	}

	public Map<String, Map<String, Object>> getOrgAllMenu(Integer sys_org_id) 
			throws KPromptException, KSqlException, KSystemException, SQLException {
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("sys_org_id", sys_org_id);
		
		SqlResult sResult = this.exeQuery("MS0002EQ02", params);
		
		Map<String, Map<String, Object>> allMap = new HashMap<String,Map<String, Object>>();  
		while(sResult.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", sResult.getInteger("id"));
			map.put("menuid", sResult.getString("menuid"));
			map.put("menuname", sResult.getString("menuname"));
			map.put("squcing", sResult.getString("squcing"));
			map.put("upperid", sResult.getString("upperid"));
			map.put("children", new ArrayList<Map<String,Object>>());
			allMap.put(sResult.getString("menuid"), map);
		}
		return allMap;
	}

	public List<String> getUserRights(Integer sys_user_id) throws KPromptException, KSqlException, KSystemException, SQLException {
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("sys_user_id", sys_user_id);
		
		List<String> args= new ArrayList<String>();
		SqlResult rs = this.exeQuery("MS0002EQ03", params);
		while (rs.next()) {
			if(!args.contains(rs.getString("id"))){
				args.add(rs.getString("id"));
			}
			if(!args.contains(rs.getString("sys_menu_id"))){
				args.add(rs.getString("sys_menu_id"));
			}
			args.add(rs.getString("sys_menu_detail_id"));
			
		}
		
		return args;

	}

	/**
	 * 获取用户的id
	 * @param loginname
	 * @return
	 * @throws SQLException 
	 * @throws KSystemException 
	 * @throws KSqlException 
	 * @throws KPromptException 
	 */
	public Integer getSysUserId(String loginname) throws KPromptException, KSqlException, KSystemException, SQLException {
		Integer sys_user_id = null;
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("loginname", loginname);
		
		SqlResult rs = this.exeQuery("MS0002EQ05", params);
		
		while(rs.next()){
			sys_user_id = rs.getInteger("sys_user_id");
		}
		
		return sys_user_id;
	}

	/**
	 * 赋予用户对应机构的所有权限
	 * @param sys_user_id
	 * @param sys_org_id
	 * @throws KSystemException 
	 * @throws SQLException 
	 * @throws KSqlException 
	 * @throws KPromptException 
	 */
	public void giveOrgAllRight(Integer sys_user_id, String sys_org_id) throws KPromptException, KSqlException, SQLException, KSystemException {
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("sys_user_id", sys_user_id);
		params.put("sys_org_id", sys_org_id);
		
		this.exeUpdate("MS0002EU07", params);
		
	}

	/**
	 * 获取用户的登录账号及密码
	 * @param sys_user_id
	 * @return 
	 * @throws Exception 
	 */
	public UserInfo getLoginNameAndPasswd(String loginname) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("loginname", loginname);
		
		UserInfo userInfo = new UserInfo();
		
		SqlResult rs = this.exeQuery("MS0002EQ06", params);
		
		ObjectMapUtil.sqlResultToObject(rs, userInfo);
		
		return userInfo;
	}

	
	public void userDelete(Integer sys_user_id) throws KPromptException, KSqlException, SQLException, KSystemException {
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("sys_user_id", sys_user_id);
		
		this.exeUpdate("MS0002EU08", params);
		
	}

	
	public String getRequestMapping(String functionName) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("functionName", functionName);
		
		SqlResult rs = this.exeQuery("MS0303EU008", params);
		String requestmapping = null;
		while(rs.next()){
			requestmapping = rs.getString("requestmapping");
		}
		
		return requestmapping;
	}
	
	
	
	public List<String> haveStorehouseCode(Integer sys_user_id) throws KPromptException, KSqlException, SQLException, KSystemException {
		Map<String,Object> params = new HashMap<String,Object>();
		List<String>  results = new ArrayList<String>();
		params.put("sys_user_id", sys_user_id);
		
		SqlResult rs = this.exeQuery("MS0002EQ07", params);
		while(rs.next()){
			results.add(rs.getString("storehouse_code"));
		}
		return results;
		
	}
	
	
	public List<String> otherHaveStorehouseCode(Integer sys_user_id) throws KPromptException, KSqlException, SQLException, KSystemException {
		Map<String,Object> params = new HashMap<String,Object>();
		List<String>  results = new ArrayList<String>();
		params.put("sys_user_id", sys_user_id);
		
		SqlResult rs = this.exeQuery("MS0002EQ08", params);
		while(rs.next()){
			results.add(rs.getString("storehouse_code"));
		}
		return results;
		
	}
	
	public void delUserStroehouse(int sys_user_id) throws KPromptException, KSqlException, SQLException, KSystemException {
		
		Map<String,Object> param = new HashMap<String, Object>();
		
		param.put("jy_user_id", sys_user_id);
		
		exeUpdate("MS0002EU09", param);
	}
	
	
	public void addUserStroehouse(int sys_user_id,String stroehouseCode) throws KPromptException, KSqlException, SQLException, KSystemException {
		
		Map<String,Object> param = new HashMap<String, Object>();
		
		param.put("jy_user_id", sys_user_id);
		param.put("storehouse_code", stroehouseCode);
		
		exeUpdate("MS0002EU10", param);
	}
	
	
	
}

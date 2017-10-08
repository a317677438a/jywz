package xft.workbench.backstage.user.biz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xft.workbench.backstage.base.enumeration.user.UserStatus;
import xft.workbench.backstage.base.util.MD5Util;
import xft.workbench.backstage.user.dao.UserInfoManagerDao;
import xft.workbench.backstage.user.model.UserInfo;

import com.kayak.web.base.exception.KPromptException;
import com.kayak.web.base.exception.KSqlException;
import com.kayak.web.base.exception.KSystemException;

//表示业务逻辑层的bean对象
@Service(value = "userInfoManagerBiz")
public class UserInfoManagerBiz {
	
	@Resource(name="userInfoManagerDao")
	private UserInfoManagerDao userInfoManagerDao;
	
	/**
	 * 新增用户
	 * @param userInfo
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void userAdd(UserInfo userInfo) throws Exception {
		
		//登录名称校验：在执行新增sql前进行
		
		//密码加密处理
		String pwd = userInfo.getPasswd();
		if(isNull(pwd))
			throw new Exception("数据异常，密码为空！");
		
		userInfo.setPasswd(MD5Util.toMD5(userInfo.getLoginname() + userInfo.getPasswd()));
		
		//插入数据
		userInfoManagerDao.addUserInfo(userInfo);
		
		//获取新增用户的id
		//Integer sys_user_id = userInfoManagerDao.getSysUserId(userInfo.getLoginname());
		
		
	}

	/**
	 * 修改用户信息
	 * @param userInfo
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void userModify(UserInfo userInfo) throws Exception {
		
		userInfoManagerDao.modifyUserInfo(userInfo);
		
		//密码字段若不为空，则进行密码修改
		if(!isNull(userInfo.getPasswd())){
			userInfo.setPasswd(MD5Util.toMD5(userInfo.getLoginname() + userInfo.getPasswd()));
			userInfoManagerDao.modifyPasswd(userInfo);
		}

		
	}

	/**
	 * 用户密码重置
	 * @param param
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void resetPasswd(Map<String, Object> param) throws Exception {
		String passwd_old = param.get("passwd_old").toString();//原始密码
		String passwd_new = param.get("passwd_new").toString();//新密码
		String loginname = param.get("loginname").toString();//登录账号
		
		//查询数据库中的原始密码，将其与输入的原始密码比对
		UserInfo userInfo = userInfoManagerDao.getLoginNameAndPasswd(loginname);
		
		if(!userInfo.getPasswd().equals(MD5Util.toMD5(loginname + passwd_old)))
			throw new KPromptException("请输入正确的原始密码！");
		
		if(isNull(passwd_new.trim()))
			throw new KPromptException("密码为空，请重新输入！");
			
		//密码加密处理
		userInfo.setPasswd(MD5Util.toMD5(loginname + passwd_new));
		
		userInfoManagerDao.modifyPasswd(userInfo);
		
	}

	/**
	 * 暂停用户服务
	 * @param userInfo
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void stopUserService(Integer id) throws Exception {
		
		userInfoManagerDao.manageUserService(id,UserStatus.stop.getValue());
	}
	
	/**
	 * 启用用户服务
	 * @param userInfo
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void startUserService(Integer id) throws Exception {
		
		userInfoManagerDao.manageUserService(id,UserStatus.run.getValue());
	}	
	
	/**
	 * 设置用户权限
	 * @param param
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setUserRights(Map<String, Object> param) throws Exception {
		int sys_user_id = Integer.parseInt(param.get("sys_user_id").toString());
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) param.get("menuidArr");
		
		//先删除原来的权限
		userInfoManagerDao.delUserRight(sys_user_id);
		
		int sys_menu_detail_id = -1;
		for(int i=0;i<list.size();i++){
			sys_menu_detail_id = Integer.parseInt(list.get(i));
			userInfoManagerDao.setUserRight(sys_user_id,sys_menu_detail_id);
		}
		
	}

	private boolean isNull(Object arg) {
		if (arg == null || "".equals(arg.toString()))
			return true;
		else
			return false;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Map<String, Object>> getOrgAllMenu(Integer sys_org_id) throws KPromptException, KSqlException, KSystemException, SQLException {
		Map<String, Map<String, Object>> allMap= userInfoManagerDao.getOrgAllMenu(sys_org_id);
		List<Map<String, Object>> treeList = new ArrayList<Map<String,Object>>(); 
		List<String> dList = new ArrayList<String>(); 
		
		// 将儿子添加到父亲的children数组里，并将儿子记录到要删除的列表中
		for (String str : allMap.keySet()) {
			Map<String, Object> map = allMap.get(str);
			if (allMap.containsKey(map.get("upperid"))) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get(map.get("upperid")).get("children");
				list.add(map);
				allMap.get(map.get("upperid")).put("children", list);
				dList.add((String) map.get("menuid"));
			}
		}
		// 删除记录到列表中的儿子
		for (String str : dList) {
			allMap.remove(str);
		}
		// 将map转换成List
		for (String str : allMap.keySet()) {
			treeList.add(allMap.get(str));
		}
		return treeList;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<String> getUserMenu(Integer sys_user_id) throws KPromptException, KSqlException, KSystemException, SQLException {

		return userInfoManagerDao.getUserRights(sys_user_id);
	}

	/**
	 * 删除用户
	 * @param sys_user_id
	 * @throws KSystemException 
	 * @throws SQLException 
	 * @throws KSqlException 
	 * @throws KPromptException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void userDelete(Integer sys_user_id) throws KPromptException, KSqlException, SQLException, KSystemException {
		//判断该用户是否已经拥有项目，拥有项目则不能删除
		//注：判断语句写在删除语句中（详情参考sql）,
		//删除用户时，同时删除用户菜单权限
		userInfoManagerDao.userDelete(sys_user_id);
		
	}
	
	public String getRequestMapping(String functionName) throws Exception {
		return userInfoManagerDao.getRequestMapping(functionName);
	}

	
	
	public List<String> haveStorehouseCode(Integer sys_user_id) throws KPromptException, KSqlException, SQLException, KSystemException {
		
		
		return userInfoManagerDao.haveStorehouseCode(sys_user_id);
		
	}
	
	
	public List<String> otherHaveStorehouseCode(Integer sys_user_id) throws KPromptException, KSqlException, SQLException, KSystemException {
		
		
		return userInfoManagerDao.otherHaveStorehouseCode(sys_user_id);
		
	}
	
}

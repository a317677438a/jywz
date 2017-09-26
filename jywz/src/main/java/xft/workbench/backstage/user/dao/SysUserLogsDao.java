package xft.workbench.backstage.user.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import xft.workbench.backstage.user.model.SysUserLogs;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.exception.KSqlException;
import com.kayak.web.base.exception.KSystemException;
import com.kayak.web.base.sql.SqlResult;

@Repository
public class SysUserLogsDao {
	protected static Logger log = Logger.getLogger(SysUserLogsDao.class);
	@Resource
	private ComnDao comnDao;
	
	
	/**
	 * 用户日志记录
	 * @param sys_user_id
	 * @throws KSqlException
	 * @throws KSystemException
	 * @throws SQLException
	 */
	public void addUserLogs(SysUserLogs sysUserLogs)
			throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sessionid", sysUserLogs.getSessionid());
		params.put("sys_user_id", sysUserLogs.getSys_user_id());
		params.put("loginname", sysUserLogs.getLoginname());
		params.put("requestmapping", sysUserLogs.getRequestmapping());
		params.put("requestParams", sysUserLogs.getRequestParams());
		params.put("opip", sysUserLogs.getOpip());
		params.put("opdate", sysUserLogs.getOpdate());
		params.put("optime", sysUserLogs.getOptime());
		
		comnDao.exeUpdate("MS0000EA003", params);
	}
	
	
	/**
	 * 查询需要记录日志用户请求映射
	 * @return
	 * @throws Exception
	 */
	public List<String> queryRequestMapping()
			throws Exception {
		List<String> results= new ArrayList<String>();
		SqlResult sr = comnDao.query("MS0000EQ012", null);
		while(sr.next()){
			results.add(sr.getString("requestmapping"));
		}
		return results;
	}
	
}

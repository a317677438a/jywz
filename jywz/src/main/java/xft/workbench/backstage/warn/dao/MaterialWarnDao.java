package xft.workbench.backstage.warn.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.warn.model.MaterialWarn;
import xft.workbench.backstage.warn.model.Message;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.sql.SqlResult;

@Repository
public class MaterialWarnDao extends ComnDao{
	/**
	 * 新增信息
	 * 
	 */
	public void addMaterialWarn(MaterialWarn materialWarn) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(materialWarn);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY8001EU001", params);
	}
	
	
	/**
	 * 新增信息提示
	 * 
	 */
	public void addMessage(Message message) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(message);
		this.exeUpdate("JY8001EU002", params);
	}
	
	
	
	/**
	 * 读信息提示
	 * 
	 */
	public void readMessage(Integer messageId) throws Exception{
		Map<String, Object> params =new HashMap<String, Object>();
		if(messageId != null && !StringUtils.isEmpty(messageId.toString())){
			params.put("id", messageId);
		}
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY8001EU003", params);
	}
	
	/**
	 * 删除信息
	 * 
	 */
	public void deleteMaterialWarn(Integer materialWarnId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", materialWarnId);
		this.exeUpdate("JY8001ED001", params);
	}
	
	/**
	 * 查询所有的预警信息
	 * 
	 */
	public SqlResult queryAllMaterialWarns() throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		SqlResult sr =this.exeQuery("JY8001EQ004", params);
		return sr;
	}
}

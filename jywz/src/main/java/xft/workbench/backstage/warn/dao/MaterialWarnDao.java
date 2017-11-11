package xft.workbench.backstage.warn.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.warn.model.MaterialWarn;

import com.kayak.web.base.dao.ComnDao;

@Repository
public class MaterialWarnDao extends ComnDao{
	/**
	 * 新增物资使用信息
	 * 
	 */
	public void addMaterialWarn(MaterialWarn materialWarn) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(materialWarn);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY8001EU001", params);
	}
	
	
	/**
	 * 新增物资使用信息
	 * 
	 */
	public void deleteMaterialWarn(Integer materialWarnId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", materialWarnId);
		this.exeUpdate("JY8001ED001", params);
	}
}

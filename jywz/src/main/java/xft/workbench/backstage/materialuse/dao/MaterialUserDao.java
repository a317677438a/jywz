package xft.workbench.backstage.materialuse.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xft.workbench.backstage.apply.model.ApplyDetail;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.materialuse.model.MaterialUse;
import xft.workbench.backstage.materialuse.model.OwnMaterialInfo;
import xft.workbench.backstage.stock.model.Stock;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.sql.SqlResult;
@Repository
public class MaterialUserDao extends ComnDao{
	/**
	 * 新增物资使用信息
	 * 
	 */
	public void addMaterialUser(MaterialUse materialUser) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(materialUser);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY5001EU001", params);
	}
	
	
	
	/**
	 * 查询当前用户物资持有信息（申请物资数量）
	 * 
	 */
	public List<OwnMaterialInfo> queryApplyNumber() throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		GlobalMessage.addMapSessionInfo(params);
		SqlResult sr =this.exeQuery("JY5001EQ003", params);
		List<OwnMaterialInfo> results= new ArrayList<OwnMaterialInfo>();
		results = (List<OwnMaterialInfo>)ObjectMapUtil.sqlResultToObject(sr,OwnMaterialInfo.class);
		return results;
	}
}

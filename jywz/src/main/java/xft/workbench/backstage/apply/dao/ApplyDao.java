package xft.workbench.backstage.apply.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xft.workbench.backstage.apply.model.Apply;
import xft.workbench.backstage.apply.model.ApplyDetail;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.sql.SqlResult;

@Repository
public class ApplyDao extends ComnDao{
	/**
	 * 新增申请--基础信息
	 * 
	 */
	public void addApply(Apply apply) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(apply);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY3001EU001", params);
	}
	
	/**
	 * 新增申请--基础信息
	 * 
	 */
	public Integer queryApplyIdByCode(String  applyCode) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("apply_code", applyCode);
		SqlResult rs =this.exeQuery("JY3001EQ001", params);
		if(rs.next()){
			return rs.getInteger("id");
		}
		return null;
	}
	
	
	
	/**
	 * 新增申请物资明细
	 * 
	 */
	public void addApplyDetail(ApplyDetail applyDetail) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(applyDetail);
		this.exeUpdate("JY3001EU002", params);
	}
	
	
	/**
	 * 修改申请--基础信息
	 * 
	 */
	public void modifyApply(Apply apply) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(apply);
		this.exeUpdate("JY3001EU003", params);
	}
	
	
	
	/**
	 * 领导审核修改申请--基础信息
	 * 
	 */
	public void reviewApply(Apply apply) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(apply);
		this.exeUpdate("JY3001EU004", params);
	}
	
	
	/**
	 * 删除物资明细信息
	 * 
	 */
	public void deleteApplyDetail(Integer applyId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", applyId); 
		this.exeUpdate("JY3001ED001", params);
	}
	
	
}

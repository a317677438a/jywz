package xft.workbench.backstage.apply.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	 * 查询一条申请信息
	 * 
	 */
	public Apply queryApplyByid(Integer applyId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", applyId);
		GlobalMessage.addMapSessionInfo(params);
		SqlResult sr =this.exeQuery("JY3001EQ002", params);
		
		Apply apply = new Apply();
		apply=(Apply)ObjectMapUtil.sqlResultToObject(sr, apply);
		return apply;
	}
	
	
	/**
	 * 查询一条申请主表的明细信息
	 * 
	 */
	public List<ApplyDetail> queryApplyDetailByid(Integer applyId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", applyId);
		SqlResult sr =this.exeQuery("JY3001EQ003", params);
		
		List<ApplyDetail> results= new ArrayList<ApplyDetail>();
		while (sr.next()) {
			ApplyDetail detail = new ApplyDetail();
			detail=(ApplyDetail)ObjectMapUtil.sqlResultToObject(sr, detail);
			results.add(detail);
		}
		
		return results;
	}
	
	
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
	 * 查询申请表ID--基础信息
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
	 * 查询物资在某仓库的入库数量。
	 * 
	 */
	public Integer queryPutinNumber(String  putin_storehouse_code,
			Integer jy_material_id,Integer status) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("putin_storehouse_code", putin_storehouse_code);
		params.put("jy_material_id", jy_material_id);
		params.put("status", status);
		SqlResult rs =this.exeQuery("JY3001EQ004", params);
		if(rs.next()){
			return rs.getInteger("putin_number");
		}
		return null;
	}
	
	/**
	 * 查询物资在某仓库的出库数量。
	 * 
	 */
	public Integer queryPutoutNumber(String  putout_storehouse_code,
			Integer jy_material_id,Integer status) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("putout_storehouse_code", putout_storehouse_code);
		params.put("jy_material_id", jy_material_id);
		params.put("status", status);
		SqlResult rs =this.exeQuery("JY3001EQ005", params);
		if(rs.next()){
			return rs.getInteger("putout_number");
		}
		return null;
	}
	
	
	/**
	 * 查询某个用户一个物资申请的总数。
	 * 
	 */
	public Integer queryApplyNumber(
			Integer jy_material_id,Integer status) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jy_material_id", jy_material_id);
		params.put("status", status);
		GlobalMessage.addMapSessionInfo(params);
		SqlResult rs =this.exeQuery("JY3001EQ006", params);
		if(rs.next()){
			return rs.getInteger("apply_number");
		}
		return null;
	}
	
	
	/**
	 * 查询某个用户使用的某个物资的总数
	 * 
	 */
	public Integer queryUseNumber(
			Integer jy_material_id,Integer status) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jy_material_id", jy_material_id);
		params.put("status", status);
		GlobalMessage.addMapSessionInfo(params);
		SqlResult rs =this.exeQuery("JY3001EQ009", params);
		if(rs.next()){
			return rs.getInteger("use_number");
		}
		return null;
	}
	
	
	/**
	 * 查询某个用户退回的某个物资的总数
	 * 
	 */
	public Integer queryBackNumber(
			Integer jy_material_id,Integer status) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jy_material_id", jy_material_id);
		params.put("status", status);
		GlobalMessage.addMapSessionInfo(params);
		SqlResult rs =this.exeQuery("JY3001EQ010", params);
		if(rs.next()){
			return rs.getInteger("putin_number");
		}
		return null;
	}
	
	
	
	/**
	 * 查询申请主表的状态
	 * 
	 */
	public Integer queryApplyStatus(
			Integer apply_id) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", apply_id);
		SqlResult rs =this.exeQuery("JY3001EQ007", params);
		if(rs.next()){
			return rs.getInteger("status");
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
	 * 修改申请单状态信息
	 * 
	 */
	public void modifyApplyStatus(Integer applyId,Integer status) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", applyId); 
		params.put("status", status); 
		this.exeUpdate("JY3001EU005", params);
	}
	
	
	/**
	 * 修改申请单状态信息
	 * 
	 */
	public void modifyApplyReceive(Integer applyId,Integer status) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", applyId); 
		params.put("status", status); 
		this.exeUpdate("JY3001EU006", params);
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
	
	
	/**
	 * 删除物资主表
	 * 
	 */
	public void deleteApply(Integer applyId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", applyId); 
		this.exeUpdate("JY3001ED002", params);
	}
	
}

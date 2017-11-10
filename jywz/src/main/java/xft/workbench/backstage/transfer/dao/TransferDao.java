package xft.workbench.backstage.transfer.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xft.workbench.backstage.apply.model.Apply;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.transfer.model.Transfer;
import xft.workbench.backstage.transfer.model.TransferDetail;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.sql.SqlResult;

@Repository
public class TransferDao extends ComnDao{


	/**
	 * 查询一条信息
	 * 
	 */
	public Transfer queryTransferByid(Integer transferId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", transferId);
		GlobalMessage.addMapSessionInfo(params);
		SqlResult sr =this.exeQuery("JY7001EU002", params);
		
		Transfer transfer = new Transfer();
		transfer=(Transfer)ObjectMapUtil.sqlResultToObject(sr, transfer);
		return transfer;
	}
	
	/**
	 * 新增
	 * 
	 */
	public void addTransfer(Transfer transfer) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(transfer);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY7001EU001", params);
	}
	
	
	
	/**
	 * 查询ID--基础信息
	 * 
	 */
	public Integer queryTransferIdByCode(String  transfer_code) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transfer_code", transfer_code);
		SqlResult rs =this.exeQuery("JY7001EQ001", params);
		if(rs.next()){
			return rs.getInteger("id");
		}
		return null;
	}
	
	
	/**
	 * 查询申请主表的状态
	 * 
	 */
	public Integer queryTransferStatus(
			Integer transfer_id) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", transfer_id);
		SqlResult rs =this.exeQuery("JY7001EQ004", params);
		if(rs.next()){
			return rs.getInteger("status");
		}
		return null;
	}
	
	
	/**
	 * 新增明细
	 * 
	 */
	public void addTransferDetail(TransferDetail transferDetail) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(transferDetail);
		this.exeUpdate("JY7001EU002", params);
	}
	
	
	/**
	 * 修改信息
	 * 
	 */
	public void modifyTransfer(Transfer transfer) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(transfer);
		this.exeUpdate("JY7001EU003", params);
	}
	
	
	/**
	 * 修改状态信息
	 * 
	 */
	public void modifyTransferStatus(Integer transferId,Integer transferStatus) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", transferId);
		params.put("status", transferStatus);
		this.exeUpdate("JY7001EU004", params);
	}
	
	
	/**
	 * 删除主表
	 * 
	 */
	public void deleteTransfer(Integer transferId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", transferId); 
		this.exeUpdate("JY7001ED001", params);
	}
	
	
	/**
	 * 删除明细信息
	 * 
	 */
	public void deleteTransferDetail(Integer transferId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", transferId); 
		this.exeUpdate("JY7001ED002", params);
	}
	
	
	
	
}

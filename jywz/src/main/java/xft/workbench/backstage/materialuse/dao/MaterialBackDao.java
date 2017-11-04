package xft.workbench.backstage.materialuse.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.stock.model.Stock;
import xft.workbench.backstage.stock.model.StockDetails;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.sql.SqlResult;

@Repository
public class MaterialBackDao extends ComnDao{
	/**
	 * 新增--基础信息
	 * 
	 */
	public void addMaterialBack(Stock stock) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(stock);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY6001EU001", params);
	}
	
	
	/**
	 * 获取基础信息id
	 * 
	 */
	public int getStockId(String putin_code) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("putin_code", putin_code);
		SqlResult sqlResult = this.exeQuery("JY6001EQ001", params);
		while (sqlResult.next()) {
			return sqlResult.getInteger("id");
		}
		return 0;
	}
	
	
	/**
	 * 获取基础信息id
	 * 
	 */
	public int getStockStatus(Integer id) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		SqlResult sqlResult = this.exeQuery("JY6001EQ004", params);
		while (sqlResult.next()) {
			return sqlResult.getInteger("status");
		}
		return -1;
	}
	
	
	/**
	 * 新增入库明细
	 * 
	 */
	public void addMaterialBackDetail(StockDetails stockDetails) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(stockDetails);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY6001EU002", params);
	}
	
	
	
	/**
	 * 删除入库单主信息
	 * 
	 */
	public void deleteOneStock(Integer id) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		exeUpdate("JY2001ED001", params);
		
	}
	/**
	 * 删除入库单主详细信息
	 * 
	 */
	public void deleteOneStockDetails(Integer id) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		exeUpdate("JY2001ED002", params);
	}
	
	/**
	 * 确认入库
	 * 
	 */
	public void confirmation(Integer id) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		exeUpdate("JY2001EU004", params);
	}
	
	
	/**
	 * 修改入库单信息
	 * 
	 */
	public void modifyMaterialBack(Stock stock) throws Exception {
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(stock);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY6001EU003", params);
	}
	
	
}

package xft.workbench.backstage.stock.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.stock.model.Stock;
import xft.workbench.backstage.stock.model.StockDetails;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.sql.SqlResult;

@Repository
public class StockDao extends ComnDao{
	
	/**
	 * 新增入库--基础信息
	 * 
	 */
	public void addStock(Stock stock) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(stock);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY2001EU001", params);
	}
	/**
	 * 获取基础信息id
	 * 
	 */
	public int getStockId(String crt_date,String crt_time) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("crt_date", crt_date);
		params.put("crt_time", crt_time);
		SqlResult sqlResult = this.exeQuery("JY2001EQ001", params);
		while (sqlResult.next()) {
			return sqlResult.getInteger("id");
		}
		return 0;
	}
	/**
	 * 新增入库明细
	 * 
	 */
	public void addStockDetail(StockDetails stockDetails) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(stockDetails);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY2001EU002", params);
	}
	/**
	 * 查询物资已经对应的物资id
	 * 
	 */
	public List<Map<String, Object>> getMaterialAndId() throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orgtype","");
		List<Map<String, Object>>  list = new ArrayList<Map<String, Object>>();
		SqlResult rs = exeQuery("JY2001EQ002",params);
		while(rs.next()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", rs.getString("key"));
			map.put("value", rs.getString("value"));
			list.add(map);
		}
		return list;
	}
}

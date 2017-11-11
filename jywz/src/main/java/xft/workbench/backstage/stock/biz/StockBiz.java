package xft.workbench.backstage.stock.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kayak.web.base.sql.SqlRow;

import xft.workbench.backstage.stock.dao.StockDao;
import xft.workbench.backstage.stock.model.Stock;
import xft.workbench.backstage.stock.model.StockDetails;
import xft.workbench.backstage.type.model.Material;

@Service
public class StockBiz {
	
	@Autowired
	private StockDao stockDao;
	
	/**
	 * 新增入库
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addStock(Stock stock,List<StockDetails> stockDetails) throws Exception{
		
		//新增入库基础信息
		stockDao.addStock(stock);
		
		//查询入库基础信息id
		int id = stockDao.getStockId(stock.getPutin_code());
		
		//新增入库明细
		for (StockDetails stockDetail : stockDetails) {
			stockDetail.setJy_storehouse_in_id(id);
			stockDao.addStockDetail(stockDetail);
		}
	}
	/**
	 * 查询物资已经对应的物资id
	 * 
	 */
	public List<Map<String, Object>> getMaterialAndId() throws Exception{
		return stockDao.getMaterialAndId();
	}
	/**
	 * 查询入库单对应的入库明细
	 * 
	 */
	public List<SqlRow> fromStockGetMaterial(Integer id) throws Exception{
		return stockDao.fromStockGetMaterial(id);
	}
	/**
	 * 删除入库单
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteOneStock(Integer id) throws Exception {
		
		// 删除入库单主信息
		stockDao.deleteOneStock(id);
		
		// 删除入库单详细信息
		stockDao.deleteOneStockDetails(id);
	}
	/**
	 * 修改入库单信息
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void modifyOneStock(Stock stock,List<StockDetails> stockDetails) throws Exception {
		
		// 修改入库信息
		stockDao.modifyOneStock(stock);
		
		Integer id = Integer.parseInt(stock.getId().toString());
		// 删除入库单详细信息
		stockDao.deleteOneStockDetails(id);
		
		//修改入库详细信息
		for (StockDetails stockDetail : stockDetails) {
			stockDetail.setJy_storehouse_in_id(stock.getId());
			stockDao.modifyStockDetail(stockDetail);
		}
	}
	/**
	 * 确认入库
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void inboundGoodsConfirmation(Integer id) throws Exception {
		
	
		stockDao.inboundGoodsConfirmation(id);
		
	}
}

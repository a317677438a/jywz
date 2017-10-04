package xft.workbench.backstage.stock.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xft.workbench.backstage.stock.dao.StockDao;
import xft.workbench.backstage.stock.model.Stock;
import xft.workbench.backstage.stock.model.StockDetails;

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
		int id = stockDao.getStockId(stock.getCrt_date(),stock.getCrt_time());
		
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
}

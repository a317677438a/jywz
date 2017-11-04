package xft.workbench.backstage.materialuse.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xft.workbench.backstage.base.enumeration.apply.PutinStatus;
import xft.workbench.backstage.base.enumeration.apply.PutinType;
import xft.workbench.backstage.materialuse.dao.MaterialBackDao;
import xft.workbench.backstage.stock.model.Stock;
import xft.workbench.backstage.stock.model.StockDetails;

import com.kayak.web.base.exception.KPromptException;

@Service
public class MaterialBackBiz {

	@Autowired
	MaterialBackDao materialBackDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addMaterialBack(Stock stock,List<StockDetails> details ) throws Exception{
		
		//新增主表：
		stock.setPutin_type(PutinType.back.getValue());
		stock.setStatus(PutinStatus.wait.getValue().toString());
		materialBackDao.addMaterialBack(stock);
		
		//得到ID；
		Integer stockId=materialBackDao.getStockId(stock.getPutin_code());
		
		//新增明细
		for(int i =0 ;i<details.size();i++){
			StockDetails detail = details.get(i);
			detail.setJy_storehouse_in_id(stockId);
			materialBackDao.addMaterialBackDetail(detail);
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void modifyMaterialBack(Stock stock,List<StockDetails> details ) throws Exception{
		Integer status = materialBackDao.getStockStatus(stock.getId());
		if(PutinStatus.wait.getValue()!=status){
			throw new KPromptException("此状态不可进行删除操作！");
		}
		
		//修改主表：
		materialBackDao.modifyMaterialBack(stock);
		
		//先删除物资明细
		materialBackDao.deleteOneStockDetails(stock.getId());
		
		//新增明细
		for(int i =0 ;i<details.size();i++){
			StockDetails detail = details.get(i);
			detail.setJy_storehouse_in_id(stock.getId());
			materialBackDao.addMaterialBackDetail(detail);
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteMaterialBack(Integer stockId) throws Exception{
		Integer status = materialBackDao.getStockStatus(stockId);
		if(PutinStatus.wait.getValue()!=status){
			throw new KPromptException("此状态不可进行删除操作！");
		}
		//删除主表
		materialBackDao.deleteOneStock(stockId);
		//删除明细表
		materialBackDao.deleteOneStockDetails(stockId);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void confirmation(Integer stockId) throws Exception{
		Integer status = materialBackDao.getStockStatus(stockId);
		if(PutinStatus.wait.getValue()!=status){
			throw new KPromptException("此状态不可进行删除操作！");
		}
		//确认入库
		materialBackDao.confirmation(stockId);
	}
	
	
}

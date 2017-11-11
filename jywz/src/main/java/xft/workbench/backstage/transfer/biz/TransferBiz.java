package xft.workbench.backstage.transfer.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xft.workbench.backstage.base.enumeration.apply.PutinStatus;
import xft.workbench.backstage.base.enumeration.apply.PutinType;
import xft.workbench.backstage.base.enumeration.apply.PutoutStatus;
import xft.workbench.backstage.base.enumeration.apply.PutoutType;
import xft.workbench.backstage.base.enumeration.apply.TransferStatus;
import xft.workbench.backstage.stock.dao.StockDao;
import xft.workbench.backstage.stock.model.Stock;
import xft.workbench.backstage.stock.model.StockDetails;
import xft.workbench.backstage.storehouseout.dao.StorehoseoutDao;
import xft.workbench.backstage.storehouseout.model.Storehoseout;
import xft.workbench.backstage.storehouseout.model.StorehoseoutDetail;
import xft.workbench.backstage.transfer.dao.TransferDao;
import xft.workbench.backstage.transfer.model.Transfer;
import xft.workbench.backstage.transfer.model.TransferDetail;

import com.kayak.web.base.exception.KPromptException;
@Service
public class TransferBiz {
	
	@Autowired
	private TransferDao transferDao;
	
	@Autowired
	private StorehoseoutDao storehoseoutDao;
	
	@Autowired
	private StockDao stockDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addTransfer(Transfer transfer,List<TransferDetail> details ) throws Exception{
		
		//新增主表：
		transfer.setStatus(TransferStatus.wait.getValue());
		transferDao.addTransfer(transfer);
		
		//得到ID；
		Integer transferId=transferDao.queryTransferIdByCode(transfer.getTransfer_code()); 
		
		//新增明细
		for(int i =0 ;i<details.size();i++){
			TransferDetail detail = details.get(i);
			detail.setJy_transfer_id(transferId);
			transferDao.addTransferDetail(detail);
		}
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void modifyTransfer(Transfer transfer,List<TransferDetail> details ) throws Exception{
		
		//新增主表：
		transferDao.modifyTransfer(transfer);
		
		//先删除物资明细
		transferDao.deleteTransferDetail(transfer.getId());
		
		//新增明细
		for(int i =0 ;i<details.size();i++){
			TransferDetail detail = details.get(i);
			detail.setJy_transfer_id(transfer.getId());
			transferDao.addTransferDetail(detail);
		}
	}
	
	
	public void comfirmTransfer(Integer transferId,Transfer transfer) throws Exception{
		Integer statusInteger = transferDao.queryTransferStatus(transferId);
		if(TransferStatus.wait.getValue()!=statusInteger){
			throw new KPromptException("此状态不可进行删除操作！");
		}
		
		if(transfer == null){
			transfer= this.queryTransferByid(transferId);
		}
		
		List<TransferDetail> transferDetails=transferDao.queryTransferDetailByid(transferId);
		
		if(transferDetails == null || transferDetails.size()==0){
			throw new KPromptException("此次仓储调拨不存在物资明细！");
		}
		
		
		//生成出库单。
		Storehoseout storehoseout = new Storehoseout(
				null, //id
				transfer.getTransfer_code(), //putout_code
				PutoutType.transfer.getValue(), //putout_type
				transfer.getPutout_user(), //putout_user
				transfer.getTransfer_date(), //putout_date
				transfer.getPutout_storehouse_code(), //putout_storehouse_code
				transfer.getPutin_user(), //apply_user
				transfer.getPutin_storehouse_code(), //
				PutoutStatus.ok.getValue(), //status
				transfer.getRemark(), //
				null, //inputuser
				null, //crt_date
				null);//crt_time
		
		storehoseoutDao.addStorehoseout(storehoseout);
		Integer storehoseoutId = storehoseoutDao.queryStorehouseOutIdByCode(storehoseout.getPutout_code());
		
		for(int i = 0;transferDetails!=null && i<transferDetails.size();i++){
			TransferDetail transferDetail= transferDetails.get(i);
			StorehoseoutDetail storehoseoutDetail = new StorehoseoutDetail(
					storehoseoutId, //jy_storehouse_out_id
					transferDetail.getJy_material_id(), //jy_material_id
					transferDetail.getTransfer_number());//putout_number
			storehoseoutDao.addStorehoseoutDetail(storehoseoutDetail);
		}
		
		
		//生成入库单。
		Stock stock = new Stock(
				null, //id
				transfer.getTransfer_code(),//putin_code
				PutinType.transfer.getValue(), //putin_type
				transfer.getPutin_user(), //putin_user
				transfer.getTransfer_date(), //putin_date
				transfer.getPutin_storehouse_code(), //putin_storehouse_code
				transfer.getPutout_user(), //cancel_user
				null, //contract_no
				transfer.getPutout_storehouse_code(), //putout_storehouse_code
				PutinStatus.ok.getValue().toString(), //status
				transfer.getRemark(), //remark
				null, //inputuser
				null, //crt_date
				null);//crt_time
		
		stockDao.addStock(stock);
		Integer stockId=stockDao.getStockId(stock.getPutin_code());
		
		for(int i = 0;transferDetails!=null && i<transferDetails.size();i++){
			TransferDetail transferDetail= transferDetails.get(i);
			StockDetails details= new StockDetails(
					stockId, //jy_storehouse_in_id
					transferDetail.getJy_material_id(), //jy_material_id
					transferDetail.getTransfer_number());//putin_number
			stockDao.addStockDetail(details);
		}
		
		
		//确认调拨
		transferDao.modifyTransferStatus(transferId, TransferStatus.ok.getValue());
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteTransfer(Integer transferId) throws Exception{
		
		Integer statusInteger = transferDao.queryTransferStatus(transferId);
		if(TransferStatus.wait.getValue()!=statusInteger){
			throw new KPromptException("此状态不可进行删除操作！");
		}
		//删除主表
		transferDao.deleteTransfer(transferId);
		//删除明细表
		transferDao.deleteTransferDetail(transferId);
	}
	
	
	public Transfer queryTransferByid(Integer transferId) throws Exception{
		return transferDao.queryTransferByid(transferId);
	}
	
	
	
}

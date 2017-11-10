package xft.workbench.backstage.transfer.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xft.workbench.backstage.base.enumeration.apply.TransferStatus;
import xft.workbench.backstage.transfer.dao.TransferDao;
import xft.workbench.backstage.transfer.model.Transfer;
import xft.workbench.backstage.transfer.model.TransferDetail;

import com.kayak.web.base.exception.KPromptException;
@Service
public class TransferBiz {
	
	@Autowired
	private TransferDao transferDao;
	
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
		
		//生成出入库单。TODO
		
		
		
		
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

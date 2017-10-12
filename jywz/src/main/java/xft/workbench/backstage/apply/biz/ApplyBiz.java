package xft.workbench.backstage.apply.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kayak.web.base.exception.KPromptException;

import xft.workbench.backstage.apply.dao.ApplyDao;
import xft.workbench.backstage.apply.model.Apply;
import xft.workbench.backstage.apply.model.ApplyDetail;
import xft.workbench.backstage.base.enumeration.apply.ApplyStatus;
import xft.workbench.backstage.base.enumeration.apply.PutinStatus;
import xft.workbench.backstage.base.enumeration.apply.PutoutStatus;
import xft.workbench.backstage.base.enumeration.apply.PutoutType;
import xft.workbench.backstage.base.enumeration.apply.UseStatus;
import xft.workbench.backstage.storehouseout.dao.StorehoseoutDao;
import xft.workbench.backstage.storehouseout.model.Storehoseout;
import xft.workbench.backstage.storehouseout.model.StorehoseoutDetail;

@Service
public class ApplyBiz {
	@Autowired
	private ApplyDao applyDao;
	
	@Autowired
	private StorehoseoutDao storehoseoutDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addApply(Apply apply,List<ApplyDetail> details ) throws Exception{
		
		//新增主表：
		apply.setStatus(ApplyStatus.wait.getValue());
		applyDao.addApply(apply);
		
		//得到ID；
		Integer applyId=applyDao.queryApplyIdByCode(apply.getApply_code()); 
		
		//新增明细
		for(int i =0 ;i<details.size();i++){
			ApplyDetail detail = details.get(i);
			detail.setJy_apply_id(applyId);
			applyDao.addApplyDetail(detail);
		}
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void modifyApply(Apply apply,List<ApplyDetail> details) throws Exception{
		
		//新增主表：
		applyDao.modifyApply(apply);
		
		//先删除物资明细
		applyDao.deleteApplyDetail(apply.getId());
		
		//新增明细
		for(int i =0 ;i<details.size();i++){
			ApplyDetail detail = details.get(i);
			detail.setJy_apply_id(apply.getId());
			applyDao.addApplyDetail(detail);
		}
	}
	
	
	public void reviewApply(Apply apply) throws Exception{
		//同意或拒绝
		applyDao.addApply(apply);
	}
	
	
	public Integer queryMaterialStoreNumber(String storehouse_code,Integer material_id) throws Exception{
		
		//查询物资在仓库的存量=入库数量-出库数据
		Integer putinNumber = applyDao.queryPutinNumber(storehouse_code, material_id, PutinStatus.ok.getValue());
		if(putinNumber==null) putinNumber=0;
		Integer putoutNumber = applyDao.queryPutoutNumber(storehouse_code, material_id, PutoutStatus.ok.getValue());
		if(putoutNumber==null) putoutNumber=0;
		return putinNumber-putoutNumber;
	}
	
	
	public Integer queryMaterialOwnNumber(Integer material_id) throws Exception{
		//查询当前用户一个物资持有的总数=申请物资-使用数据-退回数量。
		Integer applyNumber = applyDao.queryApplyNumber(material_id, ApplyStatus.receive.getValue());
		if(applyNumber==null) applyNumber=0;
		Integer useNumber = applyDao.queryUseNumber(material_id, UseStatus.use.getValue());
		if(useNumber==null) useNumber=0;
		Integer backNumber = applyDao.queryBackNumber(material_id, PutinStatus.ok.getValue());
		return applyNumber-useNumber-backNumber;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteApply(Integer applyId) throws Exception{
		
		Integer statusInteger = applyDao.queryApplyStatus(applyId);
		if(ApplyStatus.wait.getValue()!=statusInteger){
			throw new KPromptException("此状态不可进行删除操作！");
		}
		//删除主表
		applyDao.deleteApply(applyId);
		//删除明细表
		applyDao.deleteApplyDetail(applyId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void receiveApplyMaterial(Integer applyId,
			Integer applyStatus,Integer checkUserId) throws Exception{
		//接收申请物资；
		
		if(ApplyStatus.receive.getValue()==applyStatus){//接收
			
			//新增出库单
			Apply apply = applyDao.queryApplyByid(applyId);
			
			if(apply == null){
				throw new KPromptException("申请单无记录！");
			}
			
			if(apply.getApply_user() != checkUserId){
				throw new KPromptException("认证用户不为申请人，请申请人进行身份认证！");
			}
			
			//修改申请单状态。
			applyDao.modifyApplyReceive(applyId, ApplyStatus.receive.getValue());
			
			Storehoseout storehoseout = new Storehoseout(
					null, 
					apply.getApply_code(), 
					PutoutType.apply.getValue(), 
					apply.getStorehouse_user(), 
					null, 
					apply.getApply_storehouse_code(), 
					apply.getApply_user(), 
					null, 
					PutoutStatus.ok.getValue(), 
					apply.getRemark(), 
					null, 
					null, 
					null);
			
			storehoseoutDao.addStorehoseout(storehoseout);
			
			//新增出库明细
			Integer putout_id = storehoseoutDao.queryStorehouseOutIdByCode(storehoseout.getPutout_code());
			
			List<ApplyDetail> details= applyDao.queryApplyDetailByid(applyId);
			
			for(int i=0; i< details.size();i++){
				ApplyDetail detail = (ApplyDetail)details.get(i);
				
				StorehoseoutDetail storehoseoutDetail = new StorehoseoutDetail(
						putout_id, 
						detail.getJy_material_id(), 
						detail.getApply_number());
				storehoseoutDao.addStorehoseoutDetail(storehoseoutDetail);
			}
			
			
		}else if(ApplyStatus.reject.getValue()==applyStatus){//拒绝
			applyDao.modifyApplyStatus(applyId, ApplyStatus.reject.getValue());
		}else{
			throw new KPromptException("接收状态异常！");
		}
		
	}
	
}

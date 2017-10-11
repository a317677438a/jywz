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

@Service
public class ApplyBiz {
	@Autowired
	private ApplyDao applyDao;
	
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
		//查询当前用户一个物资持有的总数。
		Integer number = applyDao.queryOwnNumber(material_id, ApplyStatus.receive.getValue());
		if(number==null) number=0;
		return number;
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
	
}

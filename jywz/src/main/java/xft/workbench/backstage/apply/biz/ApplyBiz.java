package xft.workbench.backstage.apply.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xft.workbench.backstage.apply.dao.ApplyDao;
import xft.workbench.backstage.apply.model.Apply;
import xft.workbench.backstage.apply.model.ApplyDetail;
import xft.workbench.backstage.base.enumeration.apply.ApplyStatus;

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
	
	
}

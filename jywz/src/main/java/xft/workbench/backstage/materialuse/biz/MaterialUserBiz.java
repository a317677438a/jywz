package xft.workbench.backstage.materialuse.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xft.workbench.backstage.apply.dao.ApplyDao;
import xft.workbench.backstage.base.enumeration.apply.PutinStatus;
import xft.workbench.backstage.base.enumeration.apply.UseStatus;
import xft.workbench.backstage.materialuse.dao.MaterialUserDao;
import xft.workbench.backstage.materialuse.model.MaterialUse;
import xft.workbench.backstage.materialuse.model.OwnMaterialInfo;

@Service
public class MaterialUserBiz {
	@Autowired
	private MaterialUserDao materialUserDao;
	
	
	@Autowired
	private ApplyDao applyDao;
	
	/**
	 * 新增物资使用信息
	 * 
	 */
	public void addMaterialUser(MaterialUse materialUse) throws Exception{
		materialUserDao.addMaterialUser(materialUse);
	}
	
	
	/**
	 * 查询当前用户物资持有信息（申请物资数量）
	 * 
	 */
	public List<OwnMaterialInfo> queryOwnMaterialInfo(Map<String, Object> params) throws Exception{
		
		//查询当前用户物资申请物资数量
		List<OwnMaterialInfo> listsInfos=materialUserDao.queryApplyNumber(params);
	
		for(int i =0; listsInfos != null && i<listsInfos.size();i++ ){
			OwnMaterialInfo info = listsInfos.get(i);
			
			Integer useNumber = applyDao.queryUseNumber(info.getJy_material_id(), UseStatus.use.getValue());
			if(useNumber==null) useNumber=0;
			Integer backNumber = applyDao.queryBackNumber(info.getJy_material_id(), PutinStatus.ok.getValue());
			if(backNumber==null) backNumber=0;
			
			info.setUse_number(useNumber);
			info.setBack_number(backNumber);
			Integer applyNumber = info.getApply_number();
			if(applyNumber==null) applyNumber=0;
			
			info.setOwn_number(applyNumber-useNumber-backNumber);
		}
		return listsInfos;
	}
	
}

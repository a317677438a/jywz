package xft.workbench.backstage.materialuse.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xft.workbench.backstage.materialuse.dao.MaterialUserDao;
import xft.workbench.backstage.materialuse.model.MaterialUse;

@Service
public class MaterialUserBiz {
	@Autowired
	private MaterialUserDao materialUserDao;
	
	/**
	 * 新增物资使用信息
	 * 
	 */
	public void addMaterialUser(MaterialUse materialUse) throws Exception{
		materialUserDao.addMaterialUser(materialUse);
	}
	
	
	
}

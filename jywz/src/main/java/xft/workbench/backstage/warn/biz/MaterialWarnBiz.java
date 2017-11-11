package xft.workbench.backstage.warn.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xft.workbench.backstage.warn.dao.MaterialWarnDao;
import xft.workbench.backstage.warn.model.MaterialWarn;

@Service
public class MaterialWarnBiz {

	@Autowired
	private MaterialWarnDao materialWarnDao;
	
	public void addMaterialWarn(MaterialWarn materialWarn) throws Exception{
		materialWarnDao.addMaterialWarn(materialWarn);
	}
	
	public void deleteMaterialWarn(Integer materialWarnId) throws Exception{
		materialWarnDao.deleteMaterialWarn(materialWarnId);
	}
	
}

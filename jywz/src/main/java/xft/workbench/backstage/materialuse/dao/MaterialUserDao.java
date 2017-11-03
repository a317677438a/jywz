package xft.workbench.backstage.materialuse.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.materialuse.model.MaterialUse;
import xft.workbench.backstage.stock.model.Stock;

import com.kayak.web.base.dao.ComnDao;
@Repository
public class MaterialUserDao extends ComnDao{
	/**
	 * 新增物资使用信息
	 * 
	 */
	public void addMaterialUser(MaterialUse materialUser) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(materialUser);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY5001EU001", params);
	}
}

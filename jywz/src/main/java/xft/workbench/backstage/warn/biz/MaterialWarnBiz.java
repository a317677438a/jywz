package xft.workbench.backstage.warn.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kayak.web.base.sql.SqlResult;

import xft.workbench.backstage.apply.biz.ApplyBiz;
import xft.workbench.backstage.base.enumeration.apply.MessageStatus;
import xft.workbench.backstage.warn.dao.MaterialWarnDao;
import xft.workbench.backstage.warn.model.MaterialWarn;
import xft.workbench.backstage.warn.model.Message;

@Service
public class MaterialWarnBiz {

	@Autowired
	private MaterialWarnDao materialWarnDao;
	
	@Autowired
	private ApplyBiz applyBiz;
	
	public void addMaterialWarn(MaterialWarn materialWarn) throws Exception{
		materialWarnDao.addMaterialWarn(materialWarn);
	}
	
	public void deleteMaterialWarn(Integer materialWarnId) throws Exception{
		materialWarnDao.deleteMaterialWarn(materialWarnId);
	}
	
	//进行物资预警查询
	public void materialWarn() throws Exception{
		SqlResult sr=materialWarnDao.queryAllMaterialWarns();
		
		sr.resetCursor();
        while (sr.next()) {
        	String warn_storehouse_code = sr.getString("warn_storehouse_code");
        	Integer jy_material_id = sr.getInteger("jy_material_id");
        	Integer warn_number = sr.getInteger("warn_number");
        	Integer store_number = applyBiz.queryMaterialStoreNumber(warn_storehouse_code, jy_material_id);
        	if(store_number>warn_number){
        		continue;
        	}else{
        		//记录提示消息；
        		String warn_storehouse_name = sr.getString("warn_storehouse_name");
        		String material_name = sr.getString("material_name");
        		Integer inputuser = sr.getInteger("inputuser"); 
        		Message message = new Message(
        				null, 
        				inputuser,
        				"仓库物资预警", 
        				warn_storehouse_name+"的"+material_name+"仓库数量："+store_number+
        				",达到预警值："+warn_number+",请注意！", 
        				MessageStatus.noread.getValue(), 
        				null, 
        				null, 
        				null);
        		materialWarnDao.addMessage(message);
        	}
        }
	}
	
	
	
	public void readMessage(Integer messageId) throws Exception{
			materialWarnDao.readMessage(messageId);
	}
}

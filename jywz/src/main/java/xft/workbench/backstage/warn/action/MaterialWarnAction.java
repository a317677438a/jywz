package xft.workbench.backstage.warn.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.warn.biz.MaterialWarnBiz;
import xft.workbench.backstage.warn.model.MaterialWarn;
@Controller
public class MaterialWarnAction extends ABSBaseController{

	@Autowired
	private MaterialWarnBiz materialWarnBiz;
	
	
	@RequestMapping(value = "/materialwarn/add.json")
	public  @ResponseBody String  addMaterialWarn(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Map<String, Object> materialWarnInfo = (Map<String, Object>) params.get("materialWarnInfo");
			MaterialWarn materialWarn = new MaterialWarn();
			ObjectMapUtil.setObjectFileValue(materialWarn, materialWarnInfo);
			
			materialWarnBiz.addMaterialWarn(materialWarn);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "新增成功", null);
	}
	
	
	@RequestMapping(value = "/materialwarn/delete.json")
	public  @ResponseBody String  deleteMaterialWarn(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Integer id = Integer.valueOf((String)params.get("id"));
			
			materialWarnBiz.deleteMaterialWarn(id);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "删除成功", null);
	}
	
	
	
}

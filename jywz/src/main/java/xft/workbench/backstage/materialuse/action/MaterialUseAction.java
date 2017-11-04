package xft.workbench.backstage.materialuse.action;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.apply.biz.ApplyBiz;
import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.materialuse.biz.MaterialUserBiz;
import xft.workbench.backstage.materialuse.model.MaterialUse;
import xft.workbench.backstage.materialuse.model.OwnMaterialInfo;

@Controller
public class MaterialUseAction extends ABSBaseController{
	
	@Autowired
	MaterialUserBiz materialUserBiz;
	
	
	@Autowired 
	private ApplyBiz applyBiz;
	
	
	
	@RequestMapping(value = "/materialuse/materialOwnNumber.json")
	public  @ResponseBody String  materialOwnNumber(){
		Map<String, Object> params = null; 
		JSONObject object = new JSONObject();
		try {
			params = this.getRequestParams();
			Integer material_id = Integer.valueOf((String)params.get("material_id"));
			Integer ownNumber=applyBiz.queryMaterialOwnNumber( material_id);
			object.put("ownNumber", ownNumber);
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "查询物资库存成功", object);
	}
	
	
	@RequestMapping(value = "/materialuse/add.json")
	public  @ResponseBody String  addApply(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Map<String, Object> materialUseInfo = (Map<String, Object>) params.get("materialUseInfo");
			MaterialUse materialUse = new MaterialUse();
			ObjectMapUtil.setObjectFileValue(materialUse, materialUseInfo);
			
			materialUserBiz.addMaterialUser(materialUse);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "新增成功", null);
	}
	
	
	@RequestMapping(value = "/materialown/materialsOwnNumber.json")
	public  @ResponseBody String  materialsOwnNumber(){
		Map<String, Object> params = null; 
		JSONArray resultsArray=new JSONArray();
		try {
			params = this.getRequestParams();
			List<OwnMaterialInfo> results=materialUserBiz.queryOwnMaterialInfo(params);
			for (int i = 0; results !=null &&  i < results.size(); i++) {
				JSONObject object= new JSONObject(results.get(i));
				resultsArray.put(object);
			}  
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "查询物资持有信息成功", resultsArray);
	}
	
}

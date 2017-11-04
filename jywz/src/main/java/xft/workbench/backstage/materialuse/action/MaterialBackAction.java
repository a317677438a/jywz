package xft.workbench.backstage.materialuse.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.materialuse.biz.MaterialBackBiz;
import xft.workbench.backstage.stock.model.Stock;
import xft.workbench.backstage.stock.model.StockDetails;

@Controller
public class MaterialBackAction extends ABSBaseController{
	@Autowired
	MaterialBackBiz materialBackBiz;
	
	@RequestMapping(value = "/materialback/add.json")
	public  @ResponseBody String  materialBackAdd(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Map<String, Object> stockInfo = (Map<String, Object>) params.get("materialBackInfo");
			Stock stock = new Stock();
			ObjectMapUtil.setObjectFileValue(stock, stockInfo);
			
			List<Map<String, Object>> applyDetailList = (List<Map<String, Object>>)params.get("materialBackDetails");
			List<StockDetails> details = new ArrayList<StockDetails>();
			for(int i =0;i<applyDetailList.size();i++){
				Map<String, Object> applyDetailMap = applyDetailList.get(i);
				StockDetails detail = new StockDetails();
				ObjectMapUtil.setObjectFileValue(detail, applyDetailMap);
				details.add(detail);
			}
			materialBackBiz.addMaterialBack(stock, details);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "新增成功", null);
	}
	
	
	@RequestMapping(value = "/materialback/modify.json")
	public  @ResponseBody String  materialBackmodify(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Map<String, Object> stockInfo = (Map<String, Object>) params.get("materialBackInfo");
			Stock stock = new Stock();
			ObjectMapUtil.setObjectFileValue(stock, stockInfo);
			
			List<Map<String, Object>> applyDetailList = (List<Map<String, Object>>)params.get("materialBackDetails");
			List<StockDetails> details = new ArrayList<StockDetails>();
			for(int i =0;i<applyDetailList.size();i++){
				Map<String, Object> applyDetailMap = applyDetailList.get(i);
				StockDetails detail = new StockDetails();
				ObjectMapUtil.setObjectFileValue(detail, applyDetailMap);
				details.add(detail);
			}
			materialBackBiz.modifyMaterialBack(stock, details);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "修改成功", null);
	}
	
	
	@RequestMapping(value = "/materialback/confirm.json")
	public  @ResponseBody String  materialBackConfirm(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Integer id = Integer.valueOf((String)params.get("id")) ;
			materialBackBiz.confirmation(id);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "确认入库成功", null);
    }
	
	
	@RequestMapping(value = "/materialback/delete.json")
	public  @ResponseBody String  materialBackDelete(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Integer id = Integer.valueOf((String)params.get("id")) ;
			materialBackBiz.deleteMaterialBack(id);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "删除成功", null);
    }
	
}

package xft.workbench.backstage.apply.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.apply.biz.ApplyBiz;
import xft.workbench.backstage.apply.model.Apply;
import xft.workbench.backstage.apply.model.ApplyDetail;
import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.stock.model.Stock;
import xft.workbench.backstage.user.model.UserLoginInfo;
@Controller
public class ApplyAction extends ABSBaseController{

	@Autowired 
	private ApplyBiz applyBiz;
	
	@RequestMapping(value = "/apply/addApply.json")
	public  @ResponseBody String  addApply(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Map<String, Object> applyInfo = (Map<String, Object>) params.get("applyInfo");
			Apply apply = new Apply();
			ObjectMapUtil.setObjectFileValue(apply, applyInfo);
			
			List<Map<String, Object>> applyDetailList = (List<Map<String, Object>>)params.get("applyDetailList");
			List<ApplyDetail> applyDetails = new ArrayList<ApplyDetail>();
			for(int i =0;i<applyDetailList.size();i++){
				Map<String, Object> applyDetailMap = applyDetailList.get(i);
				ApplyDetail detail = new ApplyDetail();
				ObjectMapUtil.setObjectFileValue(detail, applyDetailMap);
				applyDetails.add(detail);
			}
			applyBiz.addApply(apply, applyDetails);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "新增成功", null);
	}
	
	
	@RequestMapping(value = "/apply/modifyApply.json")
	public  @ResponseBody String  modifyApply(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Map<String, Object> applyInfo = (Map<String, Object>) params.get("applyInfo");
			Apply apply = new Apply();
			ObjectMapUtil.setObjectFileValue(apply, applyInfo);
			
			List<Map<String, Object>> applyDetailList = (List<Map<String, Object>>)params.get("applyDetailList");
			List<ApplyDetail> applyDetails = new ArrayList<ApplyDetail>();
			for(int i =0;i<applyDetailList.size();i++){
				Map<String, Object> applyDetailMap = applyDetailList.get(i);
				ApplyDetail detail = new ApplyDetail();
				ObjectMapUtil.setObjectFileValue(detail, applyDetailMap);
				applyDetails.add(detail);
			}
			applyBiz.modifyApply(apply, applyDetails);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "修改成功", null);
	}
	
	
	@RequestMapping(value = "/apply/reviewApply.json")
	public  @ResponseBody String  reviewApply(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Apply apply = new Apply();
			ObjectMapUtil.setObjectFileValue(apply, params);
			
			applyBiz.reviewApply(apply);
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "审核成功", null);
	}
	
}
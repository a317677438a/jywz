package xft.workbench.backstage.apply.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.apply.biz.ApplyBiz;
import xft.workbench.backstage.apply.model.Apply;
import xft.workbench.backstage.apply.model.ApplyDetail;
import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.MD5Util;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.stock.model.Stock;
import xft.workbench.backstage.user.biz.LoginManangerBiz;
import xft.workbench.backstage.user.model.UserLoginInfo;
@Controller
public class ApplyAction extends ABSBaseController{

	@Autowired 
	private ApplyBiz applyBiz;
	
	@Autowired 
	private LoginManangerBiz loginManangerBiz;
	
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
	
	
	@RequestMapping(value = "/apply/materialOwnNumber.json")
	public  @ResponseBody String  materialOwnNumber(){
		Map<String, Object> params = null; 
		JSONObject object = new JSONObject();
		try {
			params = this.getRequestParams();
			String storehouse_code = (String)params.get("storehouse_code");
			Integer material_id = Integer.valueOf((String)params.get("material_id"));
			
			Integer storeNumber= applyBiz.queryMaterialStoreNumber(storehouse_code, material_id);
			Integer ownNumber=applyBiz.queryMaterialOwnNumber( material_id);
			
			object.put("storeNumber", storeNumber);
			object.put("ownNumber", ownNumber);
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "查询物资库存成功", object);
	}
	
	

	@RequestMapping(value = "/apply/deleteApply.json")
	public  @ResponseBody String  deleteApply(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			String apply_id = (String)params.get("id");
			Integer applyId = Integer.valueOf(apply_id);
			
			applyBiz.deleteApply(applyId);
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "删除成功", null);
	}
	
	@RequestMapping(value = "/apply/receiveApplyMaterial.json")
	public  @ResponseBody String  receiveApplyMaterial(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			
			UserLoginInfo userLoginInfo = new UserLoginInfo();
			userLoginInfo.setLoginname(params.get("loginname").toString());
			userLoginInfo.setPasswd(MD5Util.toMD5(params.get("loginname").toString() + params.get("passwd").toString()));
			Integer checkUserId= loginManangerBiz.checkUserLoginInfo(userLoginInfo);
			
			Integer applyId = Integer.valueOf((String)params.get("id"));
			Integer applyStatus = Integer.valueOf((String)params.get("status"));//2、审批拒绝,4、已领用
			applyBiz.receiveApplyMaterial(applyId, applyStatus, checkUserId);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "操作成功", null);
	}
	
	
	
	
}

package xft.workbench.backstage.transfer.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.apply.biz.ApplyBiz;
import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.MD5Util;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.transfer.biz.TransferBiz;
import xft.workbench.backstage.transfer.model.Transfer;
import xft.workbench.backstage.transfer.model.TransferDetail;
import xft.workbench.backstage.user.biz.LoginManangerBiz;
import xft.workbench.backstage.user.model.UserLoginInfo;

import com.kayak.web.base.exception.KPromptException;
@Controller
public class TransferAction extends ABSBaseController{

	@Autowired
	private TransferBiz transferBiz;
	
	@Autowired
	private ApplyBiz applyBiz;
	
	
	@Autowired 
	private LoginManangerBiz loginManangerBiz;
	
	@RequestMapping(value = "/transfer/storeNumber.json")
	public  @ResponseBody String  materialOwnNumber(){
		Map<String, Object> params = null; 
		JSONObject object = new JSONObject();
		try {
			params = this.getRequestParams();
			String storehouse_code = (String)params.get("storehouse_code");
			Integer material_id = Integer.valueOf((String)params.get("material_id"));
			
			Integer storeNumber= applyBiz.queryMaterialStoreNumber(storehouse_code, material_id);
			
			object.put("storeNumber", storeNumber);
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "查询物资库存成功", object);
	}
	
	
	
	@RequestMapping(value = "/transfer/addTransfer.json")
	public  @ResponseBody String  addTransfer(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Map<String, Object> transferInfo = (Map<String, Object>) params.get("transferInfo");
			Transfer transfer = new Transfer();
			ObjectMapUtil.setObjectFileValue(transfer, transferInfo);
			
			List<Map<String, Object>> transferDetailList = (List<Map<String, Object>>)params.get("transferDetails");
			List<TransferDetail> transferDetails = new ArrayList<TransferDetail>();
			for(int i =0;i<transferDetailList.size();i++){
				Map<String, Object> transferDetailMap = transferDetailList.get(i);
				TransferDetail detail = new TransferDetail();
				ObjectMapUtil.setObjectFileValue(detail, transferDetailMap);
				transferDetails.add(detail);
			}
			transferBiz.addTransfer(transfer, transferDetails);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "新增成功", null);
	}
	
	
	@RequestMapping(value = "/transfer/modifyTransfer.json")
	public  @ResponseBody String  modifyTransfer(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Map<String, Object> transferInfo = (Map<String, Object>) params.get("transferInfo");
			Transfer transfer = new Transfer();
			ObjectMapUtil.setObjectFileValue(transfer, transferInfo);
			
			List<Map<String, Object>> transferDetailList = (List<Map<String, Object>>)params.get("transferDetails");
			List<TransferDetail> transferDetails = new ArrayList<TransferDetail>();
			for(int i =0;i<transferDetailList.size();i++){
				Map<String, Object> transferDetailMap = transferDetailList.get(i);
				TransferDetail detail = new TransferDetail();
				ObjectMapUtil.setObjectFileValue(detail, transferDetailMap);
				transferDetails.add(detail);
			}
			transferBiz.modifyTransfer(transfer, transferDetails);
			
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "修改成功", null);
	}
	
	
	
	@RequestMapping(value = "/transfer/comfirmTransfer.json")
	public  @ResponseBody String  comfirmTransfer(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			UserLoginInfo userLoginInfo = GlobalMessage.getSessionInfo();
			Integer transferId = Integer.valueOf((String)params.get("id"));
		    
			Transfer transfer =transferBiz.queryTransferByid(transferId);
			
			//认证用户名与密码
			UserLoginInfo loginInfo = new UserLoginInfo();
			loginInfo.setLoginname(params.get("loginname").toString());
			loginInfo.setPasswd(MD5Util.toMD5(params.get("loginname").toString() + params.get("passwd").toString()));
			Integer checkUserId= loginManangerBiz.checkUserLoginInfo(loginInfo);
			
			if( (transfer.getPutin_user()==userLoginInfo.getId() && transfer.getPutout_user()==checkUserId) ||
					(transfer.getPutout_user()==userLoginInfo.getId() && transfer.getPutin_user()==checkUserId)){
		    	//通过验证
		    }else{
		    	//不通过
		    	throw new KPromptException("请另一位仓库员进行身份认证！");
		    }
			
			transferBiz.comfirmTransfer(transferId,transfer); 
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "审核成功", null);
	}
	
	
	
	@RequestMapping(value = "/transfer/deleteTransfer.json")
	public  @ResponseBody String  deleteApply(){
		Map<String, Object> params = null; 
		try {
			params = this.getRequestParams();
			Integer transferId = Integer.valueOf((String)params.get("id"));
			
			transferBiz.deleteTransfer(transferId);
		} catch (Exception e) {// 获取返回提示的错误
			return this.updateErrorJson(e);
		}
		return this.updateReturnJson(true, "删除成功", null);
	}
	
}

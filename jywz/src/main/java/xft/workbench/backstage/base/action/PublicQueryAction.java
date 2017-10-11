package xft.workbench.backstage.base.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.bcel.generic.NEW;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.base.util.GlobalMessage;

import com.kayak.web.base.service.abs.ComnServiceAbstract;
import com.kayak.web.base.sql.SqlResult;
import com.kayak.web.base.system.KResult;
import com.kayak.web.base.system.RequestSupport;

@Controller
public class PublicQueryAction extends ABSBaseController {

	@Resource
	private ComnServiceAbstract comnService;

	/**
	 * grid列表查询结果集
	 * 
	 * @return
	 */
	@RequestMapping(value = "/base/listquery.json")
	public @ResponseBody String queryJsonList() {
		try {
			Map<String, Object> map = this.getRequestParams();
			//传入参数可以start，limit可能不是字符串而是整形时，导致生成map后变成DOUBLE类型。
			if(map.containsKey("start") && map.containsKey("limit") ){
				map.put("start", Double.valueOf(map.get("start").toString()).intValue());
				map.put("limit", Double.valueOf(map.get("limit").toString()).intValue());
			}
			GlobalMessage.addMapSessionInfo(map);
			KResult result = comnService.comnQuery(map);
			return this.updateReturnJson(true, "查询成功", RequestSupport.result2JsonList(result));
		} catch (Exception e) {
			return this.updateReturnJson(false, e.getMessage(), null);
		}

	}

	
	/**
	 * 查询一条结果集信息请求
	 * 通过exeID,与其他条件参数共
	 * @return
	 */
	@RequestMapping(value = "/base/infoquery.json")
	public @ResponseBody String queryJsonInfo() {
		try {
			//得到查询后结果集
			Map<String, Object> map = this.getRequestParams();
			GlobalMessage.addMapSessionInfo(map);
			SqlResult sr=comnService.getComnDao().query(map);
			
			//将结果集mapping to HashMap
			JSONObject jo;
			sr.resetCursor();
			if (sr.next()) {
				 jo = new JSONObject(sr.getRow());
			}else{
				throw new Exception("无查询结果信息");
			}
			
			return this.updateReturnJson(true, "查询成功", jo);
		} catch (Exception e) {
			return this.updateReturnJson(false, e.getMessage(), null);
		}

	}
	
	
	/**
	 * 查询一条结果集信息请求
	 * 通过exeID,与其他条件参数共
	 * @return
	 */
	@RequestMapping(value = "/base/getCode.json")
	public @ResponseBody String getCode() {
		try {
			//得到查询后结果集
			Map<String, Object> map = this.getRequestParams();
			
			String codeType=(String)map.get("codeType");
			if(StringUtils.isEmpty(codeType)){
				codeType="SS";
			}
			DateFormat dFormat =new  SimpleDateFormat("yyyyMMddhhMMss");
			
			JSONObject resultCode = new JSONObject();
			resultCode.put("code", codeType+dFormat.format(new Date()));
			
			return this.updateReturnJson(true, "查询成功", resultCode);
		} catch (Exception e) {
			return this.updateReturnJson(false, e.getMessage(), null);
		}

	}
	
	
	
	/**
	 * 查询所有物资列表信息
	 * @return
	 */
	@RequestMapping(value = "/base/getALLMaterialList.json")
	public @ResponseBody String getALLMaterialList() {
		try {
			//得到查询后结果集
			Map<String, Object> map = this.getRequestParams();
			
			SqlResult sr=comnService.getComnDao().query(map);
			JSONArray resultsArray = new JSONArray();
			while(sr.next()){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("key", sr.getString("id"));
				jsonObject.put("value", sr.getString("codeName"));
				resultsArray.put(jsonObject);
			}
			return this.updateReturnJson(true, "查询成功", resultsArray);
		} catch (Exception e) {
			return this.updateReturnJson(false, e.getMessage(), null);
		}

	}

}

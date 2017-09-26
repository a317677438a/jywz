package xft.workbench.backstage.base.action;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import xft.workbench.backstage.base.filter.UserLogsFilter;
import xft.workbench.backstage.base.system.MesInterface;
import xft.workbench.backstage.base.util.JsonUtil;
import xft.workbench.backstage.base.util.MessageXMLHandler;

import com.kayak.web.base.action.BaseController;
import com.kayak.web.base.exception.KPromptException;
import com.kayak.web.base.system.Global;
import com.kayak.web.base.system.RequestSupport;
import com.kayak.web.base.util.Tools;
import com.opensymphony.oscache.util.StringUtil;

public class ABSBaseController extends BaseController {
	protected static Logger log = Logger.getLogger(ABSBaseController.class);
	
	/**
	 * 设置请求参数
	 */
	@SuppressWarnings("rawtypes")
	protected Map<String, Object> getRequestParams() throws Exception{
		
			HttpServletRequest localRequest =(HttpServletRequest)RequestSupport.getLocalRequest();
			Map<String, Object> map = null;
			String contentType = localRequest.getContentType();

			if (contentType != null && contentType.contains("application/json")) {
				map = getJsonParameters(localRequest);
			} else {
				Map params = localRequest.getParameterMap();
				if (params == null || params.size() <= 0) {
					return null;
				}
				map = convertRequestParameters(params, false);
			}
			if (map != null && map.size() > 0)
				log.info("##### request parameters : "
						+ JsonUtil.objectToJson(map));
			
			return map;
		
	}
	
	@SuppressWarnings("all")
	public  Map<String, Object> getJsonParameters(HttpServletRequest r) throws Exception{
		BufferedReader reader = null;
		try {
			String requestParams=UserLogsFilter.getRequestParams();
			if(StringUtil.isEmpty(requestParams)){
				reader = r.getReader();
				StringBuffer buffer = new StringBuffer();
				String str; 
				while ((str = reader.readLine()) != null) {
					buffer.append(str);
				}
				requestParams=buffer
						.toString();
			}
			
			JSONObject json= new JSONObject(requestParams);
			JsonUtil.toObjectValueString(json);
			
			//对json数据与接口进行判断。
			HttpServletRequest localRequest =(HttpServletRequest)RequestSupport.getLocalRequest();
			String uri = localRequest.getRequestURI();
			String url = uri.substring(uri.indexOf("/",1)+1);
			String filePath=MesInterface.getMesInterfacePath(url+"_req");
			if(filePath!=null && !StringUtil.isEmpty(filePath))
				MessageXMLHandler.parserCheckXMLJson(filePath, json);
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.putAll((Map<String, Object>) JsonUtil.jsonToMap(json
					.toString()));
			// 将数组标记的字符串转成数组
			// transParamsArray(map);
			// 调试使用,打印转换钱的字符串
			// map.put("JSON_STRING", buffer.toString());
			return map;
		} catch (Exception e) {
			throw e;
		} finally{
			if(reader!=null){
				reader.close();
			}  
		}
	}
	
	/**
	 * 取得参数集<br />
	 * 对从request中取得的原始参数集Map进行处理后返回
	 *
	 * @param paramMap
	 *            从request中取得的参数parameter原始Map对象
	 * @param transCharset
	 *            取参数值时是否进行编码转换（转换成系统编码），默认false
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private  Map<String, Object> convertRequestParameters(Map paramMap,
			boolean transCharset) throws Exception{
		Map pm = paramMap;
		if (pm == null || pm.size() < 0) {
			return null;
		}
		Iterator it = pm.keySet().iterator();
		Map<String, Object> map = new HashMap<String, Object>();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object val;
			if (transCharset) {// 转换String值编码
				val = Tools.string2SysCharset(pm.get(key));
			} else {
				val = pm.get(key);
			}
			if (val != null && val.getClass().isArray()) {
				Object[] vals = (Object[]) val;
				if (vals.length == 1) {
					val = vals[0];
				}
			}
			map.put(key, val);
		}
		return dealNullParams(map);
	}
	
	
	
	/**
	 * 因为前台送的参数值不管是null或是""（空字符串），后台这里接收时都会是""（空字符串）<br />
	 * 这样后台就不能判断真正null值，所以在这里约定：<br />
	 * 前台送参数时，如果是null的，就送一个"null"字符串过来<br />
	 * 后台接收参数时，把值为"null"字符串的参数值置null
	 */
	private Map<String, Object> dealNullParams(Map<String, Object> params) throws Exception{
		Map<String, Object> pms = new HashMap<String, Object>();

		Set<Map.Entry<String, Object>> set = params.entrySet();
		for (Map.Entry<String, Object> en : set) {
			String key = en.getKey();
			Object value = en.getValue();

			if (value == null) {
				pms.put(key, null);
			} else if (value.getClass().isArray()) {
				Object[] objs = (Object[]) value;
				List<Object> list = new ArrayList<Object>();
				for (Object obj : objs) {

					if (Global.NULL_VALUE.equals(obj)) {
						list.add(null);
					} else {
						list.add(obj);
					}
				}
				pms.put(key, list.toArray());
			} else if (Global.NULL_VALUE.equals(value)) {
				pms.put(key, null);
			} else {
				pms.put(key, value);
			}
		}
		return pms;
	}
	
	/**
	 * 构造返回到前端的通用更新结果JSON格式字符串
	 *
	 * @param success
	 *            更新是否成功
	 * @param returnmsg
	 *            返回信息
	 * @param returndata
	 *            返回数据:可以为Map<String,Object>/List<Object>/JSONObject/JSONArray 以及其他八大基础数据类型
	 */
	protected String updateReturnJson(boolean result, String returnmsg,
			Object returndata) {
		returnmsg = Tools.trimString(returnmsg);
		if (returndata == null)
			returndata = new HashMap<String, Object>();

		// 设置返回结果
		JSONObject json = new JSONObject();
		String strResult;
		try {
			json.put("success", result);
			json.put("returnmsg", returnmsg);
			
			JsonUtil.objectInJson(json, "returndata", returndata);
			//将返结果完部转成字符类型。
			JsonUtil.toObjectValueString(json);
			strResult = json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			 strResult ="{\"returnmsg\":"+e.getMessage()+",\"success\":false,\"returndata\":\"\"}}";
		}
		
		log.info("##### update result : " + strResult);

		return strResult;
	}
	
	
	
	/**
	 * 构造返回到前端的通用更新结果JSON格式字符串
	 *
	 * @param success
	 *            更新是否成功
	 * @param returnmsg
	 *            返回信息
	 * @param returndata
	 *            返回数据:可以为Map<String,Object>/List<Object>/JSONObject/JSONArray 以及其他八大基础数据类型
	 * @return
	 */
	protected String updateErrorJson(Exception exc) {
		
		exc.printStackTrace();
		
		// 设置返回结果
		JSONObject json = new JSONObject();
		String strResult;
		try {
			json.put("success", false);
			if(exc instanceof KPromptException){
				json.put("returnmsg", exc.getMessage());
			}else{
				log.error(exc.getMessage());
				json.put("returnmsg", "系统异常！");
			}
			//将返结果完部转成字符类型。
			JsonUtil.toObjectValueString(json);
			strResult = json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			 strResult ="{\"returnmsg\":"+e.getMessage()+",\"success\":false,\"returndata\":\"\"}}";
		}
		
		log.info("##### error result : " + strResult);
        return strResult;
	}
	
	
	
	public static void main(String[] args) throws Exception{
		JSONObject json1 = new JSONObject();
		json1.put("success", true);
		JSONObject json2 = new JSONObject();
		json2.put("success", false);
		JSONObject json3 = new JSONObject();
		json3.put("json1", json1);
		json3.put("json2", json2);
		System.out.println(json3.toString());
		@SuppressWarnings("unchecked")
		Iterator<String> keys = json3.keys();
		while (keys.hasNext()) {
			String keyString =keys.next();
			System.out.println(keyString+":"+json3.get(keyString));
			
		}
		
		JSONObject json4 = new JSONObject("{'success':true,'returnmsg':'查询成功','returndata':{'results':3,'rows':[{'group_state':1,'group_type':'1','avg_rate':0,'group_code':'assetgroup03','remark':null,'pack_positionblan':0,'product_type':null,'rownumpagesql':1,'group_fullname':'资产包测试3','id':9,'group_date':'20151121','namedsec':null,'group_property':'1','asset_num':2},{'group_state':3,'group_type':'1','avg_rate':0,'group_code':'assetgroup02','remark':null,'pack_positionblan':0,'product_type':null,'rownumpagesql':2,'group_fullname':'资产包测试2','id':8,'group_date':'20151121','namedsec':'测试交易对手1','group_property':'1','asset_num':2},{'group_state':2,'group_type':'1','avg_rate':0,'group_code':'assetgroup01','remark':null,'pack_positionblan':0,'product_type':1,'rownumpagesql':3,'group_fullname':'资产包测试1','id':7,'group_date':'20151121','namedsec':'长银第三期贷款支持证券项目','group_property':'1','asset_num':2}]}}");
		JsonUtil.toObjectValueString(json4);
		System.out.println(json4.toString());
	}
	
}

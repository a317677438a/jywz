package xft.workbench.backstage.base.action;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.base.util.EnumUtil;
import com.kayak.web.base.cache.CacheDict;
import com.kayak.web.base.service.abs.ComnServiceAbstract;
import com.kayak.web.base.system.Global;
import com.kayak.web.base.system.KResult;
import com.kayak.web.base.system.RequestSupport;

@Scope("prototype")
@Controller
public class DictQueryAction extends ABSBaseController{
	
	@Resource
	private ComnServiceAbstract comnService;
	/**
	 * 获取数据字典JSON结构结果集(业务参数：如，证件类型、国家，用户可以维护)
	 */
	@RequestMapping(value = "/base/dictquery.json")
	public @ResponseBody String dictJson() {
		String str;
		try {
			Map<String, String> map = CacheDict.getCacheDict((String)this.getRequestParams().get("dict"));
			
			if (map == null) {
				str = "{}";
			} else {
				str = CacheDict.toJson(map).toString();
			}
			if (!"false".equals(Global.getGlobalConf("Global.REQUEST_RESULT_LOG")))
				log.info("##### dict json result : " + str);
			return this.updateReturnJson(true, "查询完毕", jsonstrToArraystr(str));
		} catch (Exception e) {
			return this.updateReturnJson(true, "查询完毕", "[]");
		}
	}
	
	
	/**
	 * 获取数据字典JSON结构结果集(系统参数：增加将会对系统版本有影响 ，一般不可以用户进行维护)
	 */
	@RequestMapping(value = "/base/enumquery.json")
	public @ResponseBody String enumJson() {
		String str;
		try {
			JSONObject json = EnumUtil.enumToJSON((String)this.getRequestParams().get("enum"));
			
			if (json == null) {
				str = "{}";
			} else {
				str = json.toString();
			}
			if (!"false".equals(Global.getGlobalConf("Global.REQUEST_RESULT_LOG")))
				log.info("##### dict json result : " + str);
			return this.updateReturnJson(true, "查询完毕", jsonstrToArraystr(str));
		} catch (Exception e) {
			return this.updateReturnJson(true, "查询完毕", "[]");
		}
	}
	
	
	/**
	 * 获取数据字典JSON结构结果集(系统参数：增加将会对系统版本有影响 ，一般不可以用户进行维护)
	 */
	@RequestMapping(value = "/base/paramquery.json")
	public @ResponseBody String paramJson() {
		String str;
		try {
			Map<String, Object> map = this.getRequestParams();
			//一个param_type参数 一个exeid=MS0000EQ001
			KResult result = comnService.comnQuery(map);
			return this.updateReturnJson(true, "查询成功", RequestSupport.result2JsonList(result)
					.replace("param_code", "key")
					.replace("param_name", "value"));
			
		} catch (Exception e) {
			return this.updateReturnJson(true, "查询完毕", "[]");
		}
	}
	
	
	private JSONArray jsonstrToArraystr(String jsonstr) {
		JSONArray array = new JSONArray();
		try {
			/*//先增加一个空值。由前台自己控制。暂不加
			JSONObject jsonK=new JSONObject();
			jsonK.put("key", "");
			jsonK.put("value","请选择");
			array.put(jsonK);*/
			
			
			JSONObject json=new JSONObject(jsonstr);
			@SuppressWarnings("unchecked")
			Iterator<String> keys=json.keys();
			while(keys.hasNext()) {
				String key = (String) keys.next();
				JSONObject jsonItem=new JSONObject();
				jsonItem.put("key", key);
				jsonItem.put("value", (String)json.get(key));
				array.put(jsonItem);
			}
		} catch (Exception e) {
			return new JSONArray();
		}
		
		return array;
	}
}

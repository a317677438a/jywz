package xft.workbench.backstage.base.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.kayak.web.base.sql.SqlRow;

/**
 * Gson类库的封装工具类，专门负责解析json数据</br> 内部实现了Gson对象的单例
 * 
 * @author chenzh
 * @version 1.0
 * @since 2015-11-26
 */
public class JsonUtil {
	
	private static Gson	gson	= new Gson();
	
	/**
	 * 将对象转换成json格式
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJson(Object ts) {
		String jsonStr = null;
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}
	
	/**
	 * 将对象转换成json格式空值保留
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJsonNull(Object ts) {
		Gson g = new GsonBuilder().serializeNulls().create();
		String jsonStr = "";
		if (g != null) {
			jsonStr = g.toJson(ts);
		}
		return jsonStr;
	}
	
	/**
	 * 将对象转换成json格式(并自定义日期格式)
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJsonDateSerializer(Object ts,
			final String dateformat) {
		String jsonStr = null;
		gson = new GsonBuilder()
				.registerTypeHierarchyAdapter(Date.class,
						new JsonSerializer<Date>() {
							public JsonElement serialize(Date src,
									Type typeOfSrc,
									JsonSerializationContext context) {
								SimpleDateFormat format = new SimpleDateFormat(
										dateformat);
								return new JsonPrimitive(format.format(src));
							}
						}).setDateFormat(dateformat).create();
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}
	
	/**
	 * 将json格式转换成list对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr) {
		List<?> objList = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<?>>() {
			}.getType();
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}
	
	/**
	 * 将json格式转换成list对象，并准确指定类型
	 * 
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr, java.lang.reflect.Type type) {
		List<?> objList = null;
		if (gson != null) {
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}
	
	/**
	 * 将json格式转换成map对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<?, ?> jsonToMap(String jsonStr) {
		Map<?, ?> objMap = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<?, ?>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}
	
	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Object jsonToBean(String jsonStr, Class<?> cl) {
		Object obj = null;
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return obj;
	}
	
	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @param cl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBeanDateSerializer(String jsonStr, Class<T> cl,
			final String pattern) {
		Object obj = null;
		gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
					public Date deserialize(JsonElement json, Type typeOfT,
							JsonDeserializationContext context)
							throws JsonParseException {
						SimpleDateFormat format = new SimpleDateFormat(pattern);
						String dateStr = json.getAsString();
						try {
							return format.parse(dateStr);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						return null;
					}
				}).setDateFormat(pattern).create();
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return (T) obj;
	}
	
	/**
	 * 根据
	 * 
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static Object getJsonValue(String jsonStr, String key) {
		Object rulsObj = null;
		Map<?, ?> rulsMap = jsonToMap(jsonStr);
		if (rulsMap != null && rulsMap.size() > 0) {
			rulsObj = rulsMap.get(key);
		}
		return rulsObj;
	}
	
	private static Map<String, Object> treeListToArrayMap(
			List<Map<String, Object>> dataList, String id, String childNode,
			Map<String, Object> dataMap) {
		if (dataMap == null) {
			dataMap = new LinkedTreeMap<String, Object>();
		}
		for (int index = 0; index < dataList.size(); index++) {
			Map<String, Object> map = dataList.get(index);
			dataMap.put((String) map.get(id), map);
			// 是否有子节点
			Object o = map.get(childNode);
			if (o != null && o instanceof List) {
				treeListToArrayMap((List<Map<String, Object>>) o, id,
						childNode, dataMap);
				// String value = (String) m.get(id);
				// if (value == null) {
				// return null;
				// }
				// dataMap.put((String) m.get(id), m);
				// System.out.println(m.get(id));
			}
		}
		return dataMap;
	}
	
	private static Map<String, Object> treeListToArrayMapWithOutChild(
			List<Map<String, Object>> dataList, String id,
			Map<String, Object> dataMap) {
		if (dataMap == null) {
			dataMap = new LinkedTreeMap<String, Object>();
		}
		for (int index = 0; index < dataList.size(); index++) {
			Map<String, Object> map = dataList.get(index);
			dataMap.put((String) map.get(id), map);
		}
		return dataMap;
	}
	
	/**
	 * 将object转成JSONObject对象然后封装到JSONObject
	 *
	 */
	public static void objectInJson(JSONObject json,String key,Object object) throws Exception{
		if(object instanceof String){
			String str = (String)object;
			if(str.indexOf("{")==0){//当String中包含｛｝时。
				JSONObject returndata_json = new JSONObject(str);
				json.put(key, returndata_json);
			}else if(str.indexOf("[")==0){//当String中包含[]时。
				JSONArray returndata_json = new JSONArray(str);
				json.put(key, returndata_json);
			}else{
				json.put(key, str);
			}
		}else if(object instanceof JSONObject || object instanceof JSONArray){
			json.put(key, object);
		}else if(object instanceof SqlRow){
			JSONObject returndata_json = new JSONObject(object);
			json.put(key, returndata_json);
		}else if(object instanceof Map){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)object;
			Set<String> set = map.keySet();
			JSONObject returndata_json = new JSONObject();
			for(String map_key:set){
				objectInJson(returndata_json,map_key,map.get(map_key));
			}
			json.put(key, returndata_json);
		}else if(object instanceof List){ 
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>)object;
			JSONArray returndata_array = new JSONArray();
			for(Object list_obj:list){
				objectInArray(returndata_array,list_obj);
			}
			json.put(key, returndata_array);
		}else{
			json.put(key, object);
		}
	}
	
	
	/**
	 * 将object转成JSONArray对象然后封装到JSONArray
	 * 
	 * @param array
	 * @param object
	 * @return
	 */
	public static void objectInArray(JSONArray array,Object object) throws Exception{
		if(object instanceof String){
			String str = (String)object;
			if(str.indexOf("{")==-1){//当String中不包含｛｝时。
				array.put(str);
			}else{
				JSONObject returndata_json = new JSONObject(str);
				array.put(returndata_json);
			}
			
		}else if(object instanceof Map){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)object;
			Set<String> set = map.keySet();
			JSONObject returndata_json = new JSONObject();
			for(String map_key:set){
				objectInJson(returndata_json,map_key,map.get(map_key));
			}
			array.put(returndata_json);
		}else if(object instanceof SqlRow){
			JSONObject returndata_json = new JSONObject(object);
			array.put(returndata_json);
		}else{
			array.put(object);
		}
	}
	/**
	 * 
	* 描述 : 将JSONObject中对像中值都转成String
	* @param json
	* @throws Exception
	 */
	public static void toObjectValueString(JSONObject json)throws Exception{
		@SuppressWarnings("unchecked")
		Iterator<String> keys=json.keys();
		while(keys.hasNext()){
			String key= keys.next();
			Object value=json.get(key);
			if(value instanceof JSONObject){
				toObjectValueString((JSONObject)value);
			}else if(value instanceof JSONArray){
				toArrayValueString((JSONArray)value);
			}else{
				if(value!=null && !"null".equals(value.toString()) 
						&& !"NULL".equals(value.toString()))
					json.put(key,value.toString().trim());
			    else
				    json.put(key,"");
			}
		}			
	}
	/**
	* 描述 : 将JSONArray中对像中值都转成String
	* @param json
	* @throws Exception
	 */
	public static void toArrayValueString(JSONArray array)throws Exception{
		for(int i=0;i<array.length();i++){
			Object value=array.get(i);
			if(value instanceof JSONObject){
				toObjectValueString((JSONObject)value);
			}else if(value instanceof JSONArray){
				toArrayValueString((JSONArray)value);
			}else{
				if(value!=null && !"null".equals(value.toString()) 
						&& !"NULL".equals(value.toString()))
					array.put(i, value.toString().trim());
				else
					array.put(i, "");
			}
		}
	}
	public static void main(String[] args) {
		Map<String, Object> params = (Map<String, Object>) JsonUtil
				.jsonToMap("{\"exeid\":\"ESYS_100003\",\"user_id\":\"\",\"user_type1\":\"1\",\"user_type2\":\"2\",\"user_type3\":\"3\",\"user_type4\":\"4\"}");
		System.out.println(params);
	}
	
}
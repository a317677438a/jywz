package xft.workbench.backstage.base.service;




import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import xft.workbench.backstage.base.util.GlobalMessage;

import com.kayak.web.base.cache.CacheDict;
import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.exception.KPromptException;
import com.kayak.web.base.sql.SqlResult;
import com.kayak.web.base.system.SysBeans;
import com.kayak.web.base.util.Tools;


//表示业务逻辑层的bean对象
@Service(value = "publicService")
public class PublicService {
	private static Logger log = Logger.getLogger(PublicService.class);
	
	@Resource(name = "comnDao")
	private ComnDao comnDao;
	
	/**
	 * 
	* 描述 : 通过基础数据类型 的key值 获得 基础数据类型 的value值            <br> 
	*<p>   
	* @param  dict 基础数据类型
	* @param  itemkey 基础数据类型 的key值                                            
	* @return
	* @throws Exception
	 */
	public String  GetItemval(String dict,String itemkey) throws Exception {
		if(Tools.strIsEmpty(dict) || Tools.strIsEmpty(itemkey)){
			log.error("传入参数有误！");
			throw new Exception("传入参数有误！");
		}
		Map<String, String> map = CacheDict.getCacheDict(dict);
		String itemval=map.get(itemkey);
		if(itemval !=null){
			return itemval;
		}
		return "";
	}
	
	
	/**
	 * 
	* 描述 : 通过基础数据类型 的value值 获得 基础数据类型 的key值            <br> 
	*<p>   
	* @param  dict 基础数据类型
	* @param  itemvalue 基础数据类型 的value值                                            
	* @return
	* @throws Exception
	 */
	public String  GetItemkey(String dict,String itemvalue) throws Exception {
		if(Tools.strIsEmpty(dict) || Tools.strIsEmpty(itemvalue)){
			log.error("传入参数有误！");
			throw new Exception("传入参数有误！");
		}
		Map<String, String> map = CacheDict.getCacheDict(dict);
		for(Map.Entry<String,String> entry: map.entrySet()){
			String value = entry.getValue();
			if(value.equals(itemvalue)){
				return entry.getKey();
			}
		}
		return "";
	}
	
	
	public static void userProjectRightCheck(Integer projectId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ts_project_info_id", projectId);
		GlobalMessage.addMapSessionInfo(params);
		ComnDao comnDao = SysBeans.getBean("comnDao");
		SqlResult sqlResult = comnDao.exeQuery("MS0005EQ001", params);
		if (sqlResult.getRow(0).getInteger("val") == 0) {
			throw new KPromptException("您没有该项目的相关权限！");
		}
	}
	
	public static void userAssetPackRightCheck(Integer packId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ts_assetpack_info_id", packId);
		GlobalMessage.addMapSessionInfo(params);
		ComnDao comnDao = SysBeans.getBean("comnDao");
		SqlResult sqlResult = comnDao.exeQuery("MS0005EQ002", params);
		if (sqlResult.getRow(0).getInteger("val") == 0) {
			throw new KPromptException("您没有该资产包的相关权限！");
		}
	}
	
	public static void userProductRightCheck(Integer prodId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ts_prod_base_id", prodId);
		GlobalMessage.addMapSessionInfo(params);
		ComnDao comnDao = SysBeans.getBean("comnDao");
		SqlResult sqlResult = comnDao.exeQuery("MS0005EQ003", params);
		if (sqlResult.getRow(0).getInteger("val") == 0) {
			throw new KPromptException("您没有该产品的相关权限！");
		}
	}
	
	public static void userProdPlanRightCheck(Integer planId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ts_prod_base_temp_id", planId);
		GlobalMessage.addMapSessionInfo(params);
		ComnDao comnDao = SysBeans.getBean("comnDao");
		SqlResult sqlResult = comnDao.exeQuery("MS0005EQ004", params);
		if (sqlResult.getRow(0).getInteger("val") == 0) {
			throw new KPromptException("您没有该产品方案的相关权限！");
		}
	}
}

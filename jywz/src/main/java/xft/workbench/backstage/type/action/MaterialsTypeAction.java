package xft.workbench.backstage.type.action;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.sql.SqlResult;
import com.kayak.web.base.sql.SqlRow;

import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.enumeration.IsValid;
import xft.workbench.backstage.base.enumeration.OrgTypeEnum;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.type.biz.TypeBiz;
import xft.workbench.backstage.type.model.Type;

/**
 *Title:   
 *Description: 物资类型类 
 *Company:kayak  
 *Makedate:2017-9-28 下午2:43:52 
 * @author huangyao
 *
 */
@Controller
public class MaterialsTypeAction extends ABSBaseController{
	
	@Autowired
	private TypeBiz typerBiz;
	
	@Autowired
	private ComnDao comnDao;
	/**
	 * 
	 * 新增物资类型
	 */
	@RequestMapping(value="/materialsType/addType.json")
	public @ResponseBody String addtype(){
		try {
			Map<String, Object> params = this.getRequestParams();
			Type type = new Type();
			ObjectMapUtil.setObjectFileValue(type,params);
			typerBiz.addtype(type);
			return updateReturnJson(true, "新增物资类型成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 
	 * 查询所有物资类型
	 * 
	 */
//	@RequestMapping(value="/materialsType/getAllType.json")
//	public @ResponseBody String getAllType(){
//		try {
//			Map<String, Object> params = this.getRequestParams();
//			String code = params.get("code").toString();
//			String name = params.get("name").toString();
//			List<SqlRow> allType = typerBiz.getAllType(code,name);
//			JSONObject returndata = new JSONObject();
//			returndata.put("allType", new JSONArray(allType));
//			return updateReturnJson(true, "查询物资类型成功", returndata);
//		} catch (Exception e) {
//			return this.updateErrorJson(e);
//		}
//	}
	@RequestMapping(value="/materialsType/getAllType.json")
	public @ResponseBody String getAllType(){
		/*
		 * 性能优化处理：后台每次只查询10条数据，
		 * 需要再单独查询一次总数据量返回给前台用于分页
		*/ 
		try {
			Map<String, Object> map = this.getRequestParams();
			//传入参数可以start，limit可能不是字符串而是整形时，导致生成map后变成DOUBLE类型。
			if(map.containsKey("start") && map.containsKey("limit") ){
				map.put("start", Double.valueOf(map.get("start").toString()).intValue());
				map.put("limit", Double.valueOf(map.get("limit").toString()).intValue());
				//当检测params中含有"limit" 和"start"参数时，自动进行分页
				//map.remove("start");
				//map.remove("limit");
			}
			
			GlobalMessage.addMapSessionInfo(map);//用户id
			
			JSONArray arr = new JSONArray();
			SqlResult result = comnDao.exeQuery("JY0001EQ001", map);
			
			while(result.next()){
				JSONObject jo = new JSONObject(result.getRow());
				arr.put(jo);
			}
			
			//查询总的数量
			Integer totalNum = null;
			SqlResult rs = comnDao.exeQuery("JY0001EQ002", map);
			while(rs.next()){
				totalNum = rs.getInteger("num");
			}
			JSONObject obj = new JSONObject();
			
			obj.put("results", totalNum);
			obj.put("rows", arr);
			
			return this.updateReturnJson(true, "查询成功", obj);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
}

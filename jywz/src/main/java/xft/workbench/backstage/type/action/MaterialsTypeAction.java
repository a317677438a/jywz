package xft.workbench.backstage.type.action;


import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.sql.SqlResult;

import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.type.biz.TypeBiz;
import xft.workbench.backstage.type.model.Material;
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
	private TypeBiz typeBiz;
	
	@Autowired
	private ComnDao comnDao;
	/**
	 * 
	 * 新增物资类型时生成物资编号
	 */
	@RequestMapping(value="/materialsType/getMaterialType.json")
	public @ResponseBody String getMaterialType(){
		try {
			Integer num = typeBiz.getMaterialType();
			
			String numS = String.valueOf(num);
			Integer size = numS.length();
			if(numS.length()<4){
				for(int i =0;i<4-size;i++){
					numS ="0"+numS;
				}
			}
			
			numS = "WZLX"+numS;
			
			return updateReturnJson(true, "查询成功", numS);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
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
			typeBiz.addtype(type);
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
	/**
	 * 修改物资类型
	 * 
	 */
	@RequestMapping(value="/materialsType/modifyType.json")
	public @ResponseBody String modifytype(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数
			Type type = new Type();
			ObjectMapUtil.setObjectFileValue(type, param);
			typeBiz.modifytype(type);
			return updateReturnJson(true, "修改物资类型成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 删除物资类型
	 * 
	 */
	@RequestMapping(value="/materialsType/deleteType.json")
	public @ResponseBody String deleteType(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数
			Integer id = Integer.parseInt(param.get("id").toString());
			typeBiz.deletetype(id);
			return updateReturnJson(true, "删除物资类型成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 
	 * 查询所有物资
	 * 
	 */
	@RequestMapping(value="/materialsType/getAllMaterial.json")
	public @ResponseBody String getAllMaterial(){
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
			SqlResult result = comnDao.exeQuery("JY0002EQ001", map);
			
			while(result.next()){
				JSONObject jo = new JSONObject(result.getRow());
				arr.put(jo);
			}
			
			//查询总的数量
			Integer totalNum = null;
			SqlResult rs = comnDao.exeQuery("JY0002EQ002", map);
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
	/**
	 * 
	 * 根据id查询物资
	 * 
	 */
	@RequestMapping(value="/materialsType/getMaterialById.json")
	public @ResponseBody String getMaterialById(){
		/*
		 * 性能优化处理：后台每次只查询10条数据，
		 * 需要再单独查询一次总数据量返回给前台用于分页
		*/ 
		try {
			Map<String, Object> map = this.getRequestParams();
			
			JSONObject jo = new JSONObject();
			SqlResult result = comnDao.exeQuery("JY0002EQ001", map);
			
			if(result.next()){
				 jo = new JSONObject(result.getRow());
			}
			
			return this.updateReturnJson(true, "查询成功", jo);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 
	 * 新增物资时生成物资编号
	 */
	@RequestMapping(value="/materialsType/getMaterialNum.json")
	public @ResponseBody String getMaterialNum(){
		try {
			Integer num = typeBiz.getMaterialNum();
			
			String numS = String.valueOf(num);
			Integer size = numS.length();
			if(numS.length()<6){
				for(int i =0;i<6-size;i++){
					numS ="0"+numS;
				}
			}
			 
			
			numS = "WZ"+numS;
			
			return updateReturnJson(true, "查询成功", numS);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 
	 * 新增物资
	 */
	@RequestMapping(value="/materialsType/addMaterial.json")
	public @ResponseBody String addMaterial(){
		try {
			Map<String, Object> params = this.getRequestParams();
			Integer.parseInt(params.get("jy_material_type_id").toString());
			System.out.println(params);
			Material material = new Material();
			ObjectMapUtil.setObjectFileValue(material,params);
			typeBiz.addmaterial(material);
			return updateReturnJson(true, "新增物资成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 查询物资类型编号与物资类型名称
	 * 
	 */
	@RequestMapping(value="/materialsType/getMaterialTypeAndName.json")
	public @ResponseBody String getMaterialTypeAndName(){
		try {
			List<Map<String, Object>> list = typeBiz.getMaterialTypeAndName();
			return this.updateReturnJson(true, "查询成功", new JSONArray(list));
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 修改物资信息
	 * 
	 */
	@RequestMapping(value="/materialsType/modifyMaterial.json")
	public @ResponseBody String modifyMaterial(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数
			Material material = new Material();
			ObjectMapUtil.setObjectFileValue(material, param);
			typeBiz.modifyMaterial(material);
			return updateReturnJson(true, "修改物资信息成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 删除物资
	 * 
	 */
	@RequestMapping(value="/materialsType/deleteMaterial.json")
	public @ResponseBody String deleteMaterial(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数
			Integer id = Integer.parseInt(param.get("id").toString());
			typeBiz.deleteMaterial(id);
			return updateReturnJson(true, "删除物资成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
}

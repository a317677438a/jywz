package xft.workbench.backstage.stock.action;

import java.util.ArrayList;
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
import com.kayak.web.base.sql.SqlRow;

import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.stock.biz.StockBiz;
import xft.workbench.backstage.stock.dao.StockDao;
import xft.workbench.backstage.stock.model.Stock;
import xft.workbench.backstage.stock.model.StockDetails;
import xft.workbench.backstage.type.model.Material;
import xft.workbench.backstage.user.model.UserLoginInfo;


/**
 *Title:   
 *Description: 入库管理 
 *Company:kayak  
 *Makedate:2017-10-3 下午4:43:52 
 * @author huangyao
 *
 */
@Controller
public class StockManagement extends ABSBaseController{
	
	@Autowired
	private StockBiz stockBiz;
	
	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private ComnDao comnDao;
	
	/**
	 * 新增入库单
	 * 
	 */
	@RequestMapping(value="/stockManagement/addStock.json")
	public @ResponseBody String addStock(){
		try {
			Map<String, Object> params = this.getRequestParams();
			Map<String, Object> stockInfo = (Map<String, Object>) params.get("stockInfo");
			Stock stock = new Stock();
			ObjectMapUtil.setObjectFileValue(stock, stockInfo);
			
			UserLoginInfo userLoginInfo = GlobalMessage.getSessionInfo();
			stock.setPutin_user(userLoginInfo.getId());
			stock.setInputuser(String.valueOf(userLoginInfo.getId()));
			
			List<Map<String, Object>> stockDetailsList = (List<Map<String, Object>>) params.get("stockDetailsList");
			List<StockDetails> stockDetails = new ArrayList<StockDetails>();
			for (Map<String, Object> map : stockDetailsList) {
				StockDetails stockDetail = new StockDetails();
				ObjectMapUtil.setObjectFileValue(stockDetail, map);
				stockDetails.add(stockDetail);
			}
			stockBiz.addStock(stock,stockDetails);
			return updateReturnJson(true, "添加入库信息成功！", null);
		} catch (Exception e) {
			return updateErrorJson(e);
		}
	}
	/**
	 * 查询物资已经对应的物资id
	 * 
	 */
	@RequestMapping(value="/stockManagement/getMaterialAndId.json")
	public @ResponseBody String getMaterialAndId(){
		try {
			List<Map<String, Object>> list = stockBiz.getMaterialAndId();
			return this.updateReturnJson(true, "查询成功", new JSONArray(list));
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 
	 * 查询所有入库单
	 * 
	 */
	@RequestMapping(value="/stockManagement/getAllStock.json")
	public @ResponseBody String getAllStock(){
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
			
			UserLoginInfo userLoginInfo = GlobalMessage.getSessionInfo();
			map.put("putin_user", userLoginInfo.getId());
			
			JSONArray arr = new JSONArray();
			SqlResult result = comnDao.exeQuery("JY2001EQ003", map);
			
			while(result.next()){
				JSONObject jo = new JSONObject(result.getRow());
				arr.put(jo);
			}
			
			//查询总的数量
			Integer totalNum = null;
			SqlResult rs = comnDao.exeQuery("JY2001EQ004", map);
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
	 * 查询入库单对应的入库明细
	 * 
	 */
	@RequestMapping(value="/stockManagement/fromStockGetMaterial.json")
	public @ResponseBody String fromStockGetMaterial(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数
			Integer id = Integer.parseInt(param.get("id").toString());
			List<SqlRow> list = stockBiz.fromStockGetMaterial(id);
			return this.updateReturnJson(true, "查询成功", new JSONArray(list));
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 删除入库单
	 * 
	 */
	@RequestMapping(value="/stockManagement/deleteOneStock.json")
	public @ResponseBody String deleteOneStock(){
		try {
			Map<String, Object> param = this.getRequestParams();//获取请求参数
			Integer id = Integer.parseInt(param.get("id").toString());
			stockBiz.deleteOneStock(id);
			return updateReturnJson(true, "删除入库单成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 修改入库单信息
	 * 
	 */
	@RequestMapping(value="/stockManagement/modifyOneStock.json")
	public @ResponseBody String modifyOneStock(){
		try {
			Map<String, Object> params = this.getRequestParams();
			Map<String, Object> stockInfo = (Map<String, Object>) params.get("stockInfo");
			Stock stock = new Stock();
			ObjectMapUtil.setObjectFileValue(stock, stockInfo);
			
			List<Map<String, Object>> stockDetailsList = (List<Map<String, Object>>) params.get("stockDetailsList");
			List<StockDetails> stockDetails = new ArrayList<StockDetails>();
			for (Map<String, Object> map : stockDetailsList) {
				StockDetails stockDetail = new StockDetails();
				ObjectMapUtil.setObjectFileValue(stockDetail, map);
				stockDetails.add(stockDetail);
			}
			stockBiz.modifyOneStock(stock,stockDetails);
			return updateReturnJson(true, "修改物资信息成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
	/**
	 * 入库查询统计
	 * 
	 */
	@RequestMapping(value="/stockManagement/procurementStorage.json")
	public @ResponseBody String procurementStorage(){
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
			SqlResult result = comnDao.exeQuery("JY2001EQ006", map);
			
			while(result.next()){
				JSONObject jo = new JSONObject(result.getRow());
				arr.put(jo);
			}
			
			//查询总的数量
			Integer totalNum = null;
			SqlResult rs = comnDao.exeQuery("JY2001EQ007", map);
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
	 * 确认入库
	 * 
	 */
	@RequestMapping(value="/stockManagement/inboundGoodsConfirmation.json")
	public @ResponseBody String inboundGoodsConfirmation(){
		try {
			Map<String, Object> params = this.getRequestParams();
			Integer id = Integer.valueOf((String)(params.get("id")));
			stockBiz.inboundGoodsConfirmation(id);
			return updateReturnJson(true, "确认入库成功", null);
		} catch (Exception e) {
			return this.updateErrorJson(e);
		}
	}
}

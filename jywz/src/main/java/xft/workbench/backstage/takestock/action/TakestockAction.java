package xft.workbench.backstage.takestock.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.util.FileUtil;

import com.kayak.web.base.exception.KPromptException;
import com.kayak.web.base.service.abs.ComnServiceAbstract;
import com.kayak.web.base.sql.SqlResult;
import com.kayak.web.base.system.KResult;
@Controller
public class TakestockAction extends ABSBaseController{

	@Resource
	private ComnServiceAbstract comnService;
	/**
	 * grid列表查询结果集
	 * 
	 * @return
	 */
	@RequestMapping(value = "/takestock/materialNumber.json")
	public @ResponseBody String queryJsonList() {
		try {
			Map<String, Object> map = this.getRequestParams();
			
			return this.updateReturnJson(true, "查询成功", this.gerResult(map));
		} catch (Exception e) {
			return this.updateReturnJson(false, e.getMessage(), null);
		}

	}
	


	
	@RequestMapping(value = "/takestock/downloadTakestock.json")
	 public @ResponseBody
	    String downloadDetailOut(HttpServletRequest request, HttpServletResponse response) {
	        BufferedOutputStream bos = null;
	        try {
	            Map<String, Object> params = this.getRequestParams();
	            bos = new BufferedOutputStream(response.getOutputStream());
	           
	            String fileName="盘点明细-"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".csv";
	            
	           
	            response.setCharacterEncoding("UTF-8");
	            // 设置文档打开类型
	            response.setContentType("application/octet-stream;charset=GBK");
	            // 设置报文头为attachment响应类型
	            response.setHeader("Content-disposition",
	                    "attachment;" + FileUtil.encodeFileName(request, fileName));

	            byte[] bytes = this.createFileOut(params);  // 读取字节流

	            if (null == bytes || bytes.length == 0) {
	                throw new KPromptException("文件服务器上不存在此文件！");
	            }
	            response.setHeader("Content-Length", String.valueOf(bytes.length));

	            bos.write(bytes, 0, bytes.length);
	            return updateReturnJson(true, "文件下载成功", null);
	        } catch (Exception e) {
	            try {
	                response.setContentType("text/html;charset=UTF-8");
	                byte[] bb = this.updateErrorJson(e).getBytes();
	                bos.write(bb, 0, bb.length);
	            } catch (UnsupportedEncodingException e1) {
	                e1.printStackTrace();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	            return null;
	        } finally {
	            try {
	                if (bos != null) {
	                    bos.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
	private byte[] createFileOut(Map<String, Object> map) throws Exception{
		
		JSONArray  array= this.gerResult(map);
		
    	StringBuffer fileContent = new StringBuffer("仓库名称,物资名称,物资类型,规格型号,供应商,入库数量,出库数量,库存数量");
    	fileContent.append("\r\n");
    	
    	for(int i=0;array!=null && i<array.length();i++){
    		JSONObject object = array.getJSONObject(i); 
    		fileContent.append(object.getString("putin_storehouse_name")+",")
    					.append(object.getString("material_name")+",")
    					.append(object.getString("material_type_name")+",")
    					.append(object.getString("model")+",")
    					.append(object.getString("supplier")+",")
    					.append(object.getString("putin_number")+",")
    					.append(object.getString("putout_number")+",")
    					.append(object.getString("store_number"))
    					.append("\r\n");
    	}
    	
    	return fileContent.toString().getBytes();
    	
    }
	
	
	private JSONArray gerResult(Map<String, Object> map) throws Exception{
		//查仓库物资入库信息exeid:'JY8001EQ001'
		map.put("exeid", "JY8001EQ001");
		KResult resultIn = comnService.comnQuery(map);
		
		JSONArray arrayIn =new JSONArray();
		SqlResult sResultIn =resultIn.getSResult();
		sResultIn.resetCursor();
		try {
			while (sResultIn.next()) {
				JSONObject jo = new JSONObject(sResultIn.getRow());
				if(!StringUtils.isEmpty(jo.getString("jy_material_id")) && 
						!StringUtils.isEmpty(jo.getString("putin_number")) &&
						!"null".equals(jo.getString("putin_number"))){
					arrayIn.put(jo);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		if(arrayIn.length()==0){
			 throw new KPromptException("无法查到相关物资库存信息！");
		}
		
		//查仓库物资出库信息exeid:'JY8001EQ002'
		map.put("exeid", "JY8001EQ002");
		KResult resultOut = comnService.comnQuery(map);
		
		SqlResult sResult =resultOut.getSResult();
		sResult.resetCursor();
		Map<String, Integer> out_map=new HashMap<String, Integer>();
		try {
			while (sResult.next()) {
				out_map.put(sResult.getInteger("jy_material_id").toString(),sResult.getInteger("putout_number"));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		for(int i =0;arrayIn !=null && i<arrayIn.length();i++){
			JSONObject object=arrayIn.getJSONObject(i);
			Integer putin_number = object.getInt("putin_number");
			if(out_map.containsKey(object.getString("jy_material_id"))){
				Integer putout_number=(Integer)out_map.get(object.getString("jy_material_id"));
				object.put("putout_number", putout_number);
				object.put("store_number", putin_number-putout_number);
			}else{
				object.put("putout_number", 0);
				object.put("store_number", putin_number);
			}
		}
		
		return arrayIn;
	}
	
	
}

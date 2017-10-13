package xft.workbench.backstage.storehouseout.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.enumeration.apply.PutoutStatus;
import xft.workbench.backstage.base.enumeration.apply.PutoutType;
import xft.workbench.backstage.base.util.EnumUtil;
import xft.workbench.backstage.base.util.FileUtil;
import xft.workbench.backstage.base.util.GlobalMessage;

import com.kayak.web.base.exception.KPromptException;
import com.kayak.web.base.service.abs.ComnServiceAbstract;
import com.kayak.web.base.system.KResult;

@Controller
public class StorehouseoutAction extends ABSBaseController{

	
	@Resource
	private ComnServiceAbstract comnService;
	 /**
     * 描述 : 文档下载、打开
     * <p>
     * 樊东新
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/storehouseout/downloadDetail.json")
    public @ResponseBody
    String downloadDetail(HttpServletRequest request, HttpServletResponse response) {
        BufferedOutputStream bos = null;
        try {
            Map<String, Object> params = this.getRequestParams();
            bos = new BufferedOutputStream(response.getOutputStream());
           
            String fileName="出库明细-"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".csv";
            
            GlobalMessage.addMapSessionInfo(params);
			KResult result = comnService.comnQuery(params);
			JSONArray array=result.getArrayJson();

            response.setCharacterEncoding("UTF-8");
            // 设置文档打开类型
            response.setContentType("application/octet-stream;charset=UTF-8");
            // 设置报文头为attachment响应类型
            response.setHeader("Content-disposition",
                    "attachment;" + FileUtil.encodeFileName(request, fileName));

            byte[] bytes = this.createFile(array);  // 读取字节流

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
	
     
    private byte[] createFile(JSONArray array) throws Exception{
    	StringBuffer fileContent = new StringBuffer("出库单号,出库类型,物资名称,物资类型,出库数量,规格型号,供应商,出库时间,出库仓库,出库状态");
    	fileContent.append("\r\n");
    	
    	for(int i=0;i<array.length();i++){
    		JSONObject obj= (JSONObject)array.get(i);
    		fileContent.append(obj.getString("putout_code"))
    					.append(EnumUtil.getDescValueByEnumValue(PutoutType.class,obj.getString("putout_type")))
    					.append(obj.getString("material_name"))
    					.append(obj.getString("material_type_name"))
    					.append(obj.getString("putout_number"))
    					.append(obj.getString("model"))
    					.append(obj.getString("supplier"))
    					.append(obj.getString("putout_date"))
    					.append(obj.getString("putout_storehouse_name"))
    					.append(EnumUtil.getDescValueByEnumValue(PutoutStatus.class,obj.getString("putoutstatus")))
    					.append(obj.getString("\r\n"));
    	}
    	
    	
    	
    	return fileContent.toString().getBytes("UTF-8");
    	
    }
	
	
}

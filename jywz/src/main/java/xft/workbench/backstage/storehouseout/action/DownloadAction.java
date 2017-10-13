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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xft.workbench.backstage.base.action.ABSBaseController;
import xft.workbench.backstage.base.enumeration.apply.PutinStatus;
import xft.workbench.backstage.base.enumeration.apply.PutinType;
import xft.workbench.backstage.base.enumeration.apply.PutoutStatus;
import xft.workbench.backstage.base.enumeration.apply.PutoutType;
import xft.workbench.backstage.base.util.EnumUtil;
import xft.workbench.backstage.base.util.FileUtil;
import xft.workbench.backstage.base.util.GlobalMessage;

import com.kayak.web.base.exception.KPromptException;
import com.kayak.web.base.service.abs.ComnServiceAbstract;
import com.kayak.web.base.sql.SqlResult;
import com.kayak.web.base.system.KResult;

@Controller
public class DownloadAction extends ABSBaseController{

	
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
    String downloadDetailOut(HttpServletRequest request, HttpServletResponse response) {
        BufferedOutputStream bos = null;
        try {
            Map<String, Object> params = this.getRequestParams();
            bos = new BufferedOutputStream(response.getOutputStream());
           
            String fileName="出库明细-"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".csv";
            
            GlobalMessage.addMapSessionInfo(params);
			KResult result = comnService.comnQuery(params);
			
			SqlResult sResult=result.getSResult();
			
            response.setCharacterEncoding("UTF-8");
            // 设置文档打开类型
            response.setContentType("application/octet-stream;charset=GBK");
            // 设置报文头为attachment响应类型
            response.setHeader("Content-disposition",
                    "attachment;" + FileUtil.encodeFileName(request, fileName));

            byte[] bytes = this.createFileOut(sResult);  // 读取字节流

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
	
     
    private byte[] createFileOut(SqlResult sResult) throws Exception{
    	StringBuffer fileContent = new StringBuffer("出库单号,出库类型,物资名称,物资类型,出库数量,规格型号,供应商,出库时间,出库仓库,出库状态");
    	fileContent.append("\r\n");
    	
    	while(sResult.next()){
    		fileContent.append(sResult.getString("putout_code")+",")
    					.append(EnumUtil.getDescValueByEnumValue(PutoutType.class,sResult.getString("putout_type"))+",")
    					.append(sResult.getString("material_name")+",")
    					.append(sResult.getString("material_type_name")+",")
    					.append(sResult.getString("putout_number")+",")
    					.append(sResult.getString("model")+",")
    					.append(sResult.getString("supplier")+",")
    					.append(sResult.getString("putout_date")+",")
    					.append(sResult.getString("putout_storehouse_name")+",")
    					.append(EnumUtil.getDescValueByEnumValue(PutoutStatus.class,sResult.getString("putoutstatus")))
    					.append("\r\n");
    	}
    	
    	return fileContent.toString().getBytes();
    	
    }
    
    
    
    
    
    
    
    
    
    /**
     * 描述 : 文档下载、打开
     * <p>
     * 樊东新
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/storehousein/downloadDetail.json")
    public @ResponseBody
    String downloadDetailIn(HttpServletRequest request, HttpServletResponse response) {
        BufferedOutputStream bos = null;
        try {
            Map<String, Object> params = this.getRequestParams();
            bos = new BufferedOutputStream(response.getOutputStream());
           
            String fileName="入库明细-"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".csv";
            
            GlobalMessage.addMapSessionInfo(params);
			KResult result = comnService.comnQuery(params);
			
			SqlResult sResult=result.getSResult();
			
            response.setCharacterEncoding("UTF-8");
            // 设置文档打开类型
            response.setContentType("application/octet-stream;charset=GBK");
            // 设置报文头为attachment响应类型
            response.setHeader("Content-disposition",
                    "attachment;" + FileUtil.encodeFileName(request, fileName));

            byte[] bytes = this.createFileIn(sResult);  // 读取字节流

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
	
     
    private byte[] createFileIn(SqlResult sResult) throws Exception{
    	StringBuffer fileContent = new StringBuffer("入库单号,入库类型,物资名称,物资类型,入库数量,规格型号,供应商,入库时间,入库仓库,入库状态");
    	fileContent.append("\r\n");
    	
    	while(sResult.next()){
    		fileContent.append(sResult.getString("putin_code")+",")
    					.append(EnumUtil.getDescValueByEnumValue(PutinType.class,sResult.getString("putin_type"))+",")
    					.append(sResult.getString("material_name")+",")
    					.append(sResult.getString("material_type_name")+",")
    					.append(sResult.getString("putin_number")+",")
    					.append(sResult.getString("model")+",")
    					.append(sResult.getString("supplier")+",")
    					.append(sResult.getString("putin_date")+",")
    					.append(sResult.getString("putin_storehouse_name")+",")
    					.append(EnumUtil.getDescValueByEnumValue(PutinStatus.class,sResult.getString("putinstatus")))
    					.append("\r\n");
    	}
    	
    	return fileContent.toString().getBytes();
    	
    }
    
    
    
    /**
     * 描述 : 文档下载、打开
     * <p>
     * 樊东新
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/apply/downloadDetail.json")
    public @ResponseBody
    String downloadDetailApply(HttpServletRequest request, HttpServletResponse response) {
        BufferedOutputStream bos = null;
        try {
            Map<String, Object> params = this.getRequestParams();
            bos = new BufferedOutputStream(response.getOutputStream());
           
            String fileName="物资申请明细-"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".csv";
            
            GlobalMessage.addMapSessionInfo(params);
			KResult result = comnService.comnQuery(params);
			
			SqlResult sResult=result.getSResult();
			
            response.setCharacterEncoding("UTF-8");
            // 设置文档打开类型
            response.setContentType("application/octet-stream;charset=GBK");
            // 设置报文头为attachment响应类型
            response.setHeader("Content-disposition",
                    "attachment;" + FileUtil.encodeFileName(request, fileName));

            byte[] bytes = this.createFileApply(sResult);  // 读取字节流

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
	
     
    private byte[] createFileApply(SqlResult sResult) throws Exception{
    	StringBuffer fileContent = new StringBuffer("申领单号,物资名称,物资类型,申领数量,规格型号,供应商,申领时间,申领仓库,申领状态");
    	fileContent.append("\r\n");
    	
    	while(sResult.next()){
    		fileContent.append(sResult.getString("apply_code")+",")
    					.append(sResult.getString("material_name")+",")
    					.append(sResult.getString("material_type_name")+",")
    					.append(sResult.getString("apply_number")+",")
    					.append(sResult.getString("model")+",")
    					.append(sResult.getString("supplier")+",")
    					.append(sResult.getString("apply_date")+",")
    					.append(sResult.getString("apply_storehouse_name")+",")
    					.append(EnumUtil.getDescValueByEnumValue(PutinStatus.class,sResult.getString("applystatus")))
    					.append("\r\n");
    	}
    	
    	return fileContent.toString().getBytes();
    	
    }
    
    
	
	
}

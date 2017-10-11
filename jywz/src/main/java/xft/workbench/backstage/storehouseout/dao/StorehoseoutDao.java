package xft.workbench.backstage.storehouseout.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xft.workbench.backstage.base.util.GlobalMessage;
import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.storehouseout.model.Storehoseout;
import xft.workbench.backstage.storehouseout.model.StorehoseoutDetail;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.sql.SqlResult;

@Repository
public class StorehoseoutDao extends ComnDao{
	/**
	 * 查询物资出库信息主表id，通过出库单号
	 * 
	 */
	public Integer queryStorehouseOutIdByCode(String  putout_code) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("putout_code", putout_code);
		SqlResult rs =this.exeQuery("JY4001EQ001", params);
		if(rs.next()){
			return rs.getInteger("id");
		}
		return null;
	}
	
	
	
	/**
	 * 新增出库单主表--基础信息
	 * 
	 */
	public void addStorehoseout(Storehoseout storehoseout) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(storehoseout);
		GlobalMessage.addMapSessionInfo(params);
		this.exeUpdate("JY4001EU001", params);
	}
	
	
	/**
	 * 新增出库单物资明细
	 * 
	 */
	public void addStorehoseoutDetail(StorehoseoutDetail storehoseoutDetail) throws Exception{
		Map<String, Object> params = ObjectMapUtil.getFieldVlaue2(storehoseoutDetail);
		this.exeUpdate("JY4001EU002", params);
	}
	
	
	
}

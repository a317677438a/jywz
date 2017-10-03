package xft.workbench.backstage.type.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.type.model.Material;
import xft.workbench.backstage.type.model.Type;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.exception.KPromptException;
import com.kayak.web.base.exception.KSqlException;
import com.kayak.web.base.exception.KSystemException;
import com.kayak.web.base.sql.SqlResult;
import com.kayak.web.base.sql.SqlRow;

@Repository
public class TypeDao extends ComnDao{
	/**
	 * 新增物资类型
	 */
	public void add(Type type) throws Exception {
		Map<String,Object> params  = ObjectMapUtil.getFieldVlaue2(type);
		this.exeUpdate("JY0001EU001", params);
	}
	/**
	 * 查询所有物资类型
	 * 
	 */
	public List<SqlRow> getAllType(String code,String name) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>(); 
		params.put("code", code);
		params.put("name", name);
		List<SqlRow> allType = new ArrayList<SqlRow>();
		SqlResult sResult = this.exeQuery("JY0001EQ002", params);
		while (sResult.next()) {
			allType.add(sResult.getRow());
		}
		return allType;
	}
	/**
	 * 
	 * 获取物资类型编码
	 */
	public Integer getMaterialType() throws Exception{
		Map<String, Object> params = new HashMap<String, Object>(); 
		params.put("code",null);
		SqlResult sResult = this.exeQuery("JY0001EQ003", params);
		Integer num = null;
		if(sResult.next()) {
			num = sResult.getInteger("num");
		}
		return num;
	}
	/**
	 * 
	 * 获取物资编码
	 */
	public Integer getMaterialNum() throws Exception{
		Map<String, Object> params = new HashMap<String, Object>(); 
		params.put("code",null);
		SqlResult sResult = this.exeQuery("JY0002EQ003", params);
		Integer num = null;
		if(sResult.next()) {
			num = sResult.getInteger("num");
		}
		return num;
	}
	/**
	 * 
	 * 检查物资类型是否被使用
	 * 
	 */
	public Integer checkTypeStatus(Integer id) throws KSqlException, KPromptException, KSystemException, SQLException {
		
		Integer counts = null;
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("id", id);
		
		SqlResult rs = exeQuery("JY0001EQ004", params);
		
		while(rs.next())
			counts = rs.getInteger("counts");
			
		return counts;
	}
	/**
	 * 修改物资类型
	 * @param id
	 */
	public void modifyCode(Type type) throws Exception {
		Map<String, Object> param = ObjectMapUtil.getFieldVlaue2(type);
		
		exeUpdate("JY0001EU002", param);
		
	}
	/**
	 * 删除物资类型
	 * @param id
	 */
	public void deleteCode(Integer id) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("id", id);
		
		exeUpdate("JY0001ED001", params);
		
		
	}
	/**
	 * 新增物资
	 */
	public void addMaterial(Material material) throws Exception {
		Map<String,Object> params  = ObjectMapUtil.getFieldVlaue2(material);
		this.exeUpdate("JY0002EU001", params);
	}
	/**
	 * 查询物资类型编号与物资类型名称
	 */
	public List<Map<String, Object>> getMaterialTypeAndName() throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orgtype","");
		List<Map<String, Object>>  list = new ArrayList<Map<String, Object>>();
		SqlResult rs = exeQuery("JY0002EQ004",params);
		while(rs.next()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", rs.getString("key"));
			map.put("value", rs.getString("value"));
			list.add(map);
		}
		return list;
	}
}

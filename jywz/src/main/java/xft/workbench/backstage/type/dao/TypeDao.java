package xft.workbench.backstage.type.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xft.workbench.backstage.base.util.ObjectMapUtil;
import xft.workbench.backstage.type.model.Type;

import com.kayak.web.base.dao.ComnDao;
import com.kayak.web.base.sql.SqlResult;
import com.kayak.web.base.sql.SqlRow;

@Repository
public class TypeDao extends ComnDao{
	/*
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
}

package xft.workbench.backstage.type.biz;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kayak.web.base.sql.SqlRow;

import xft.workbench.backstage.type.dao.TypeDao;
import xft.workbench.backstage.type.model.Type;



@Service
public class TypeBiz {
	
	@Autowired
	private TypeDao typeDao;
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addtype(Type type) throws Exception {
		// 添加机构
		typeDao.add(type);
	}
	
	public List<SqlRow> getAllType(String code,String name) throws Exception{
		return typeDao.getAllType(code, name);
	}
}

package xft.workbench.backstage.type.biz;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kayak.web.base.exception.KPromptException;

import xft.workbench.backstage.type.dao.TypeDao;

@Service
public class CheckTypeService {
	
	@Autowired
	private TypeDao typeDao;
	
	/**
	 * 
	 * 检查物资类型是否被使用
	 * @param code
	 */
	public boolean checkCodeStatus(Integer id) throws Exception{
		//非空判断
		if(isNull(id))
			throw new KPromptException("物资类型编码获取失败");
		
		Integer counts = typeDao.checkTypeStatus(id);
		
		if(counts>0)
			return false;//已被使用
		
		return true;
		
	}
	private boolean isNull(Object arg) {
		if (arg == null || "".equals(arg.toString()))
			return true;
		else
			return false;
	}
}
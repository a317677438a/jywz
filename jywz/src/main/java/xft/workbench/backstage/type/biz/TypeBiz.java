package xft.workbench.backstage.type.biz;



import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kayak.web.base.exception.KPromptException;
import com.kayak.web.base.sql.SqlRow;

import xft.workbench.backstage.type.dao.TypeDao;
import xft.workbench.backstage.type.model.Material;
import xft.workbench.backstage.type.model.Type;



@Service
public class TypeBiz {
	
	@Autowired
	private TypeDao typeDao;
	
	@Autowired
	private CheckTypeService checkTypeService;
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addtype(Type type) throws Exception {
		// 添加物资类型
		typeDao.add(type);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addmaterial(Material material) throws Exception {
		// 添加物资
		typeDao.addMaterial(material);
	}
	
	public List<SqlRow> getAllType(String code,String name) throws Exception{
		return typeDao.getAllType(code, name);
	}
	
	public Integer getMaterialType() throws Exception{
		return typeDao.getMaterialType();
	}
	/**
	 * 
	 * 新增物资类型时生成物资编号
	 */
	public Integer getMaterialNum() throws Exception{
		return typeDao.getMaterialNum();
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void modifytype(Type type) throws Exception {
		//判断物资类型状态：如果物资类型被使用，则不能进行此操作
		//false表示没有被使用，true表示被使用
		boolean flag = checkTypeService.checkCodeStatus(type.getId());
		
		if(!flag)
			throw new KPromptException("物资类型被使用了，不允许此操作！");
		// 修改物资类型
		typeDao.modifyCode(type);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deletetype(Integer id) throws Exception {
		
		boolean flag = checkTypeService.checkCodeStatus(id);
		
		if(!flag)
			throw new KPromptException("物资类型被使用了，不允许此操作！");
		// 删除物资类型
		typeDao.deleteCode(id);
	}
	/**
	 * 新增物资时获取物资类型与物资类型名称
	 * 
	 */
	public List<Map<String, Object>> getMaterialTypeAndName() throws Exception{
		return typeDao.getMaterialTypeAndName();
	}
	/**
	 * 修改物资信息
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void modifyMaterial(Material material) throws Exception {
		
		// 修改物资信息
		typeDao.modifyMaterial(material);
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void modifyMaterialUselimit(Integer materialId,Integer use_limit) throws Exception {
		
		// 修改物资申领限制
		typeDao.modifyMaterialUselimit(materialId, use_limit);
	}
	
	
	
	/**
	 * 删除物资
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteMaterial(Integer id) throws Exception {
		
		boolean flag = checkTypeService.checkMaterialStatus(id);
		
		if(!flag)
			throw new KPromptException("物资被使用了，不允许此操作！");
		// 删除物资
		typeDao.deleteMaterial(id);
	}
}

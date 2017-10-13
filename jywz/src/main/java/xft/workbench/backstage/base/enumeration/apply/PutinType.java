package xft.workbench.backstage.base.enumeration.apply;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 用户状态
 * @author pl
 *
 */
public enum PutinType {

	@EnumValue("1")
	@EnumDesc("采购入库")
	apply(1),
	
	@EnumValue("2")
	@EnumDesc("退库入库")
	back(2),
	
	@EnumValue("3")
	@EnumDesc("移库入库")
	transfer(3);
	
	private Integer value;
	
	public Integer getValue(){
		return this.value;
	}
	
	PutinType(Integer value){
		this.value = value;
	}
}

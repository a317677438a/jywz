package xft.workbench.backstage.base.enumeration.apply;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 用户状态
 * @author pl
 *
 */
public enum PutoutType {

	@EnumValue("1")
	@EnumDesc("申领出库")
	apply(1),
	
	@EnumValue("3")
	@EnumDesc("移库出库")
	transfer(3);
	
	private Integer value;
	
	public Integer getValue(){
		return this.value;
	}
	
	PutoutType(Integer value){
		this.value = value;
	}
}

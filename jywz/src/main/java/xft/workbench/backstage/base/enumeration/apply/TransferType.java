package xft.workbench.backstage.base.enumeration.apply;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 
 * @author pl
 *
 */
public enum TransferType {

	@EnumValue("1")
	@EnumDesc("转库")
	type_1(1),
	
	@EnumValue("2")
	@EnumDesc("退库")
	type_2(2),
	
	@EnumValue("3")
	@EnumDesc("调拨")
	type_3(3);
	
	private Integer value;
	
	public Integer getValue(){
		return this.value;
	}
	
	TransferType(Integer value){
		this.value = value;
	}
}

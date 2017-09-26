package xft.workbench.backstage.base.enumeration;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;


public enum AuditEnum {

	@EnumValue("0")
	@EnumDesc("待复核")
	pend_check(0),
	
	@EnumValue("1")
	@EnumDesc("核准")
	approval(1),
	
	@EnumValue("2")
	@EnumDesc("拒绝")
	refuse(2);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	AuditEnum(Integer value) {
		this.value = value;
	} 
}

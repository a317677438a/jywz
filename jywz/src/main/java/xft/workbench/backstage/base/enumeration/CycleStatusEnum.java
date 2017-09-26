package xft.workbench.backstage.base.enumeration;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum CycleStatusEnum {
	@EnumValue("0")
	@EnumDesc("准备期")
	buyready(0),
	
	@EnumValue("1")
	@EnumDesc("购买确认")
	buyconfirm(1),
	
	@EnumValue("2")
	@EnumDesc("待复核")
	pend_check(2);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	CycleStatusEnum(Integer value) {
		this.value = value;
	} 
}

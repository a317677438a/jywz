package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum RateType {

	@EnumValue("1")
	@EnumDesc("固定利率")
	fixed(1),
	
	@EnumValue("2")
	@EnumDesc("浮动利率")
	floats(2),
	
	@EnumValue("3")
	@EnumDesc("其他")
	other(3);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	RateType(Integer value) {
		this.value = value;
	}
}

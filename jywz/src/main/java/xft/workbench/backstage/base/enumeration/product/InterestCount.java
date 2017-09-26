package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum InterestCount {
	
	@EnumValue("1")
	@EnumDesc("Act/360")
	sanLiuLing(1),
	
	@EnumValue("2")
	@EnumDesc("Act/365")
	sanLiuWu(2),
	
	@EnumValue("3")
	@EnumDesc("Act/Act")
	actuallDay(3);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	} 
	
	InterestCount(Integer value) {
		this.value = value;
	} 

}

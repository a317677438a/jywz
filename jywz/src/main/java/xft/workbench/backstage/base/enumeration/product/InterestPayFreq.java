package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum InterestPayFreq {

	@EnumValue("1")
	@EnumDesc("按月付息")
	payByMonth(1),
	
	@EnumValue("2")
	@EnumDesc("按季付息")
	payBySeason(2),
	
	@EnumValue("3")
	@EnumDesc("按半年付息")
	payByHalfyear(3),
	
	@EnumValue("4")
	@EnumDesc("按年付息")
	payByYear(4),
	
	@EnumValue("5")
	@EnumDesc("到期付息")
	oncePayAll(5),
	
	@EnumValue("6")
	@EnumDesc("其他")
	others(6);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	InterestPayFreq(Integer value) {
		this.value = value;
	} 
	
	
}

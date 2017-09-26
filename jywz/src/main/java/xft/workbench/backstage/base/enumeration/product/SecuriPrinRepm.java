package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum SecuriPrinRepm {
	
	@EnumValue("1")
	@EnumDesc("过手型")
	paththrough(1),
	
	@EnumValue("2")
	@EnumDesc("计划摊还型")
	AmortizationPlan(2),
	
	@EnumValue("3")
	@EnumDesc("到期一次性还本付息")
	OncePay(3);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	} 
	
	SecuriPrinRepm(Integer value) {
		this.value = value;
	} 
}

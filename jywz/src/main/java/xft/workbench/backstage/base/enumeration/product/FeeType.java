package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum FeeType {

	@EnumValue("1")
	@EnumDesc("税金")
	tax(1),
	
	@EnumValue("2")
	@EnumDesc("费用")
	fee(2),
	
	@EnumValue("3")
	@EnumDesc("机构报酬")
	payOfInstitution(3);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	FeeType(Integer value) {
		this.value = value;
	} 
	
}

package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum BondType {
	
	@EnumValue("1")
	@EnumDesc("优先级")
	prior(1),
	
	@EnumValue("2")
	@EnumDesc("劣后级")
	secondary(2);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	BondType(Integer value) {
		this.value = value;
	} 
}

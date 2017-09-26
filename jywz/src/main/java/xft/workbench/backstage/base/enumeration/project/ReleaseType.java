package xft.workbench.backstage.base.enumeration.project;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum ReleaseType {
	
	@EnumValue("1")
	@EnumDesc("标准")
	standard(1),
	
	@EnumValue("2")
	@EnumDesc("非标")
	nonstandard(2);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	ReleaseType(Integer value) {
		this.value = value;
	} 

}

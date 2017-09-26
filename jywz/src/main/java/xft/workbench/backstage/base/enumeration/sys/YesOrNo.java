package xft.workbench.backstage.base.enumeration.sys;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum YesOrNo {
	
	@EnumValue("0")
	@EnumDesc("否")
	no(0),
	
	@EnumValue("1")
	@EnumDesc("是")
	yes(1);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	YesOrNo(Integer value) {
		this.value = value;
	} 

}

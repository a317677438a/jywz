package xft.workbench.backstage.base.enumeration.pack;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum StrSrceenOP {

	@EnumValue("regex")
	@EnumDesc("包含")
	regex("regex"),
	
	@EnumValue("not")
	@EnumDesc("不包含")
	not("not"),
	
	@EnumValue("equality")
	@EnumDesc("等于")
	equality("equality"),
	
	
	@EnumValue("ne")
	@EnumDesc("不等于")
	ne("ne");
	
	private String value;
	
	public String getValue() {
		return this.value;
	}
	
	StrSrceenOP(String value) {
		this.value = value;
	} 
}

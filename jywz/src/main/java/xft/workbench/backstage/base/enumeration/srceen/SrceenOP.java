package xft.workbench.backstage.base.enumeration.srceen;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum SrceenOP {
	

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
	ne("ne"),
	
	@EnumValue("gt")
	@EnumDesc("大于")
	gt("gt"),
	
	@EnumValue("lt")
	@EnumDesc("小于")
	lt("lt"),
	
	@EnumValue("gte")
	@EnumDesc("大于等于")
	gte("gte"),
	
	@EnumValue("lte")
	@EnumDesc("小于等于")
	lte("lte");
	
	private String value;
	
	public String getValue() {
		return this.value;
	}
	
	SrceenOP(String value) {
		this.value = value;
	} 

}

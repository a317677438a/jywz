package xft.workbench.backstage.base.enumeration.pack;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum PackStatus {

	@EnumValue("1")
	@EnumDesc("未标记")
	beformark("1"),
	
	
	@EnumValue("2")
	@EnumDesc("已标记")
	marked("2");
	
	private String value;
	
	public String getValue() {
		return this.value;
	}
	
	PackStatus(String value) {
		this.value = value;
	} 
}

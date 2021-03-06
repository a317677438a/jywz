package xft.workbench.backstage.base.enumeration;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum IsValid {
	
	@EnumValue("0")
	@EnumDesc("正常")
	run(0),
	
	@EnumValue("1")
	@EnumDesc("无效")
	delete(1);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	IsValid(Integer value) {
		this.value = value;
	} 
}

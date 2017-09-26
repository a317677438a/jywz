package xft.workbench.backstage.base.enumeration;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;


public enum OpeStatusEnum {
	
	@EnumValue("0")
	@EnumDesc("正常")
	start(0),
	
	@EnumValue("1")
	@EnumDesc("停用")
	stop(1);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	OpeStatusEnum(Integer value) {
		this.value = value;
	}
}

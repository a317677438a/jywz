package xft.workbench.backstage.base.enumeration.track;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum CloseOption {
	@EnumValue("1")
	@EnumDesc("回购结束")
	buyback(1),
	
	@EnumValue("2")
	@EnumDesc("标记结束")
	mark(2);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	CloseOption(Integer value) {
		this.value = value;
	} 
}

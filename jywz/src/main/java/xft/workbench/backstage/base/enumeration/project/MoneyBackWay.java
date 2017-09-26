package xft.workbench.backstage.base.enumeration.project;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum MoneyBackWay {
	
	@EnumValue("1")
	@EnumDesc("现金收付制")
	money(1),
	
	@EnumValue("2")
	@EnumDesc("权责发生制")
	rights(2);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	MoneyBackWay(Integer value) {
		this.value = value;
	} 

}

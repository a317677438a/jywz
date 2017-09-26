package xft.workbench.backstage.base.enumeration.project;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum EvaluateWay {
	
	@EnumValue("1")
	@EnumDesc("实际余额")
	actualBalance(1),
	
	@EnumValue("2")
	@EnumDesc("未偿余额")
	balanceLeft(2),
	
	@EnumValue("3")
	@EnumDesc("折现值")
	discount(3);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	EvaluateWay(Integer value) {
		this.value = value;
	} 

}

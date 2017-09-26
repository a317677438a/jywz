package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum FeepayFreq {
	
	@EnumValue("1")
	@EnumDesc("一次性收取")
	oncePayAll(1),
	
	@EnumValue("2")
	@EnumDesc("按期收取")
	payByperiod(2);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	FeepayFreq(Integer value) {
		this.value = value;
	} 
}

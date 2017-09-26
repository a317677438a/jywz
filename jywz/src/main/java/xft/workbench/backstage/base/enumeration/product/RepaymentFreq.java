package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

//资产还款频率
public enum RepaymentFreq {

	@EnumValue("1")
	@EnumDesc("MTH-按月")
	monthly(1),
	
	@EnumValue("2")
	@EnumDesc("3MTH-按季")
	quarterly(2),
	
	@EnumValue("3")
	@EnumDesc("6MTH-按半年")
	halfYear(3),
	
	@EnumValue("4")
	@EnumDesc("按年")
	year(4),
	
	@EnumValue("5")
	@EnumDesc("TNR-利随本清")
	disposable(5),
	
	@EnumValue("6")
	@EnumDesc("其他")
	other(6);
	
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	RepaymentFreq(Integer value) {
		this.value = value;
	} 
	
}

package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum BenchRate {
	
	// 此处定义的枚举必须包含于xft.workbench.backstage.base.enumeration.AdjustRateType中
	
	@EnumValue("130")
	@EnumDesc("一年存款")
	oneYearDeposit(130),
	
	@EnumValue("220")
	@EnumDesc("六个月至一年（含一年）贷款")
	sixMonthStooneYearLoan(220),
	
	@EnumValue("230")
	@EnumDesc("一至三年（含三年）贷款")
	oneToThreeYearLoan(230),
	
	@EnumValue("240")
	@EnumDesc("三至五年（含5年贷款）")
	threetofiveyearloan(240),
	
	@EnumValue("250")
	@EnumDesc("五年以上贷款")
	fiveMoreYearLoan(250);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	BenchRate(Integer value) {
		this.value = value;
	} 
}

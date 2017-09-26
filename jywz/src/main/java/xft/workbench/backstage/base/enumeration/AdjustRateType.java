package xft.workbench.backstage.base.enumeration;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum AdjustRateType {

	@EnumValue("100")
	@EnumDesc("存款活期")
	current_deposit(100),
	
	@EnumValue("110")
	@EnumDesc("存款定期-三个月")
	time_deposit_1(110),
	
	@EnumValue("120")
	@EnumDesc("存款定期-半年")
	time_deposit_2(120),
	
	
	@EnumValue("130")
	@EnumDesc("存款定期-一年")
	time_deposit_3(130),
	
	
	@EnumValue("140")
	@EnumDesc("存款定期-二年")
	time_deposit_4(140),
	
	@EnumValue("150")
	@EnumDesc("存款定期-三年")
	time_deposit_5(150),
	
	@EnumValue("160")
	@EnumDesc("存款定期-五年")
	time_deposit_6(160),
	
	
	@EnumValue("210")
	@EnumDesc("商业贷款-六个月")
	business_loan_1(210),
	
	
	@EnumValue("220")
	@EnumDesc("商业贷款-一年")
	business_loan_2(220),
	
	@EnumValue("230")
	@EnumDesc("商业贷款-三年")
	business_loan_3(230),
	
	@EnumValue("240")
	@EnumDesc("商业贷款-五年")
	business_loan_4(240),
	
	@EnumValue("250")
	@EnumDesc("商业贷款-五年以上")
	business_loan_5(250),
	
	@EnumValue("310")
	@EnumDesc("公积金贷款-五年")
	reservefunds_loan_1(310),
	
	@EnumValue("320")
	@EnumDesc("公积金贷款-五年以上")
	reservefunds_loan_2(320);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	AdjustRateType(Integer value) {
		this.value = value;
	}
}

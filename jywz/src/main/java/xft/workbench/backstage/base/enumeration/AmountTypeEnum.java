package xft.workbench.backstage.base.enumeration;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum AmountTypeEnum {

	@EnumValue("1")
	@EnumDesc("正常还款")
	normalRepayment(1),
	
	@EnumValue("2")
	@EnumDesc("提前还款")
	advanceRepayment(2),
	
	@EnumValue("3")
	@EnumDesc("逾期还款")
	overdueRepayment(3),
	
	@EnumValue("4")
	@EnumDesc("逾期未还")
	unOverdueRepayment(4);
	
	
	private Integer value;

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	AmountTypeEnum(Integer value){
		this.value = value;
	}
	
}

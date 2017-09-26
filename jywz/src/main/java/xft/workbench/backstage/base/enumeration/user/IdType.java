package xft.workbench.backstage.base.enumeration.user;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum IdType {

	@EnumValue("0")
	@EnumDesc("身份证")
	idCard(0),
	
	@EnumValue("1")
	@EnumDesc("护照")
	passport(1),
	
	@EnumValue("2")
	@EnumDesc("军官证")
	militaryOfficer(2),
	
	@EnumValue("3")
	@EnumDesc("士兵证")
	soldiersCard(3),
	
	@EnumValue("4")
	@EnumDesc("回乡证")
	returnHomeCard(4),
	
	@EnumValue("5")
	@EnumDesc("户口本")
	account(5),
	
	@EnumValue("6")
	@EnumDesc("外国护照")
	foreignPassport(6),
	
	@EnumValue("7")
	@EnumDesc("其他")
	other(7);
	
/*	@EnumValue("8")
	@EnumDesc("无")
	none(8);*/
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	IdType(Integer value) {
		this.value = value;
	} 
	
}

package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

//资产还款频率
public enum BondRating {

	@EnumValue("1")
	@EnumDesc("AAA")
	aaa(1),
	
	@EnumValue("2")
	@EnumDesc("AA+")
	aap(2),
	
	@EnumValue("3")
	@EnumDesc("AA")
	aa(3),
	
	@EnumValue("4")
	@EnumDesc("AA-")
	aam(4),
	
	@EnumValue("5")
	@EnumDesc("A+")
	ap(5),
	
	@EnumValue("6")
	@EnumDesc("A")
	a(6),
	
	@EnumValue("7")
	@EnumDesc("A-")
	am(7),
	
	@EnumValue("8")
	@EnumDesc("BBB+")
	bbbp(8),
	
	@EnumValue("9")
	@EnumDesc("BBB-")
	bbbm(9),
	
	@EnumValue("10")
	@EnumDesc("-")
	none(10);
	
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	BondRating(Integer value) {
		this.value = value;
	} 
	
}

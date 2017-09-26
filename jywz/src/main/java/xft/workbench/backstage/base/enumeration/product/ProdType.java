package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum ProdType {
	
	@EnumValue("1")
	@EnumDesc("个人消费贷款支持证券（消费贷款ABS）")
	consumeAbs(1),
	
	@EnumValue("2")
	@EnumDesc("个人汽车抵押贷款支持证券（auto-abs）")
	autoAbs(2),
	
	@EnumValue("3")
	@EnumDesc("个人住房抵押贷款支持证券（rmbs）")
	rmbs(3),
	
	@EnumValue("4")
	@EnumDesc("公司信贷资产支持证券（clo）")
	clo(4),
	
	@EnumValue("5")
	@EnumDesc("公司信贷资产支持证券（clo）")
	creditCardAbs(5),
	
	@EnumValue("6")
	@EnumDesc("融资租凭资产支持证券（lbs）")
	lbs(6),
	
	@EnumValue("7")
	@EnumDesc("商业房地产抵押贷款支持证券（cmbs）")
	cmbs(7);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	ProdType(Integer value) {
		this.value = value;
	}

}

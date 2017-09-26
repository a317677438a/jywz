package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum AssetHandleEnum {

	@EnumValue("1")
	@EnumDesc("已处理")
	unhandle(1),
	
	@EnumValue("0")
	@EnumDesc("未处理")
	alreadyHandle(0);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	AssetHandleEnum(Integer value) {
		this.value = value;
	} 
}

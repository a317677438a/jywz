package xft.workbench.backstage.base.enumeration.user;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

//性别枚举
public enum Sex {

	@EnumValue("0")
	@EnumDesc("男")
	male(0),
	
	@EnumValue("1")
	@EnumDesc("女")
	female(1);
	
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	Sex(Integer value) {
		this.value = value;
	} 
	
}

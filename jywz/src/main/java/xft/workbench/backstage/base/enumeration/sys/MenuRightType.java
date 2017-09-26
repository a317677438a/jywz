package xft.workbench.backstage.base.enumeration.sys;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum MenuRightType {
	
	@EnumValue("0")
	@EnumDesc("业务操作权限")
	businessRight(0),
	
	@EnumValue("1")
	@EnumDesc("系统管理权限")
	systemRight(1),
	
	@EnumValue("2")
	@EnumDesc("共用权限")
	commonRight(2);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	MenuRightType(Integer value) {
		this.value = value;
	} 

}

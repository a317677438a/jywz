package xft.workbench.backstage.base.enumeration.user;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 用户类型枚举
 * @author pl
 *
 */
public enum UserType {

	@EnumValue("0")
	@EnumDesc("管理台增加用户")
		addByAdmin(0),
	
	@EnumValue("1")
	@EnumDesc("业务平台增加用户")
		addByUser(1);
	
	private Integer value;
	
	public Integer getValue(){
		return this.value;
	}
	
	UserType(Integer value){
		this.value = value;
	}
}

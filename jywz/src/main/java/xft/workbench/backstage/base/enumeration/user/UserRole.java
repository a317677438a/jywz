package xft.workbench.backstage.base.enumeration.user;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 用户状态
 * @author pl
 *
 */
public enum UserRole {

	@EnumValue("1")
	@EnumDesc("系统管理员")
	admin(1),
	
	@EnumValue("2")
	@EnumDesc("仓管员")
	storehouse(2),
	
	@EnumValue("3")
	@EnumDesc("领导")
	boss(3),
	
	@EnumValue("4")
	@EnumDesc("物资申请人")
	apply(4);
	
	private Integer value;
	
	public Integer getValue(){
		return this.value;
	}
	
	UserRole(Integer value){
		this.value = value;
	}
}

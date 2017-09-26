package xft.workbench.backstage.base.enumeration.user;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 用户状态
 * @author pl
 *
 */
public enum UserStatus {

	@EnumValue("0")
	@EnumDesc("正常")
	run(0),
	
	@EnumValue("1")
	@EnumDesc("停用")
	stop(1);
	
	private Integer value;
	
	public Integer getValue(){
		return this.value;
	}
	
	UserStatus(Integer value){
		this.value = value;
	}
}

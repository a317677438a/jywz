package xft.workbench.backstage.base.enumeration.apply;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 用户状态
 * @author pl
 *
 */
public enum MessageStatus {

	@EnumValue("1")
	@EnumDesc("未读")
	noread(1),
	
	@EnumValue("2")
	@EnumDesc("已读")
	read(2);
	
	private Integer value;
	
	public Integer getValue(){
		return this.value;
	}
	
	MessageStatus(Integer value){
		this.value = value;
	}
}

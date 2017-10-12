package xft.workbench.backstage.base.enumeration.apply;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 用户状态
 * @author pl
 *
 */
public enum UseStatus {

	@EnumValue("1")
	@EnumDesc("已使用")
	use(1);
	
	private Integer value;
	
	public Integer getValue(){
		return this.value;
	}
	
	UseStatus(Integer value){
		this.value = value;
	}
}

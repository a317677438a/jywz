package xft.workbench.backstage.base.enumeration.apply;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 用户状态
 * @author pl
 *
 */
public enum PutinStatus {

	@EnumValue("1")
	@EnumDesc("待确认")
	wait(1),
	
	@EnumValue("2")
	@EnumDesc("确认出库")
	ok(2);
	
	private Integer value;
	
	public Integer getValue(){
		return this.value;
	}
	
	PutinStatus(Integer value){
		this.value = value;
	}
}

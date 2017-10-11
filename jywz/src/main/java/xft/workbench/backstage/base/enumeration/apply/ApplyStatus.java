package xft.workbench.backstage.base.enumeration.apply;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 用户状态
 * @author pl
 *
 */
public enum ApplyStatus {

	@EnumValue("1")
	@EnumDesc("待审核")
	wait(1),
	
	@EnumValue("2")
	@EnumDesc("审批拒绝")
	reject(2),
	
	@EnumValue("3")
	@EnumDesc("待领用")
	agree(3),
	
	@EnumValue("4")
	@EnumDesc("已领用")
	receive(4);
	
	private Integer value;
	
	public Integer getValue(){
		return this.value;
	}
	
	ApplyStatus(Integer value){
		this.value = value;
	}
}

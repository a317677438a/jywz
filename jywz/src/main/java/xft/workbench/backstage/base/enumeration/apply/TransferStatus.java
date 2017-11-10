package xft.workbench.backstage.base.enumeration.apply;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 用户状态
 * @author pl
 *
 */
public enum TransferStatus {

	@EnumValue("1")
	@EnumDesc("待调拨")
	wait(1),
	
	@EnumValue("2")
	@EnumDesc("调拨完成")
	ok(2);
	
	private Integer value;
	
	public Integer getValue(){
		return this.value;
	}
	
	TransferStatus(Integer value){
		this.value = value;
	}
}

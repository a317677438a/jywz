package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum DisposalStatus {
	
	@EnumValue("0")
	@EnumDesc("待审批")
	pendingapprove(0),
	
	@EnumValue("1")
	@EnumDesc("拒绝")
	refused(1),
	
	@EnumValue("2")
	@EnumDesc("待处置")
	pendingdispose(2),
	
	@EnumValue("3")
	@EnumDesc("已处置")
	disposed(3);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	DisposalStatus(Integer value) {
		this.value = value;
	} 
}

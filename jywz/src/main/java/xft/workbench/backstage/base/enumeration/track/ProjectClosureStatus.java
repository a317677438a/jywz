package xft.workbench.backstage.base.enumeration.track;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum ProjectClosureStatus {

	@EnumValue("0")
	@EnumDesc("待审批")
	pendingapprove(0),
	
	@EnumValue("1")
	@EnumDesc("拒绝")
	refused(1),
	
	@EnumValue("2")
	@EnumDesc("待结束")
	pendingclose(2),
	
	@EnumValue("3")
	@EnumDesc("已结束")
	closed(3);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	ProjectClosureStatus(Integer value) {
		this.value = value;
	} 
}

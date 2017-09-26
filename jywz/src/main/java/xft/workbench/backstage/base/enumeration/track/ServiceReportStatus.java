package xft.workbench.backstage.base.enumeration.track;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum ServiceReportStatus {

	@EnumValue("0")
	@EnumDesc("预约")
	booked(0),
	
	@EnumValue("1")
	@EnumDesc("已生成")
	generated(1),
	
	@EnumValue("2")
	@EnumDesc("取消预约")
	canceled(2);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	ServiceReportStatus(Integer value) {
		this.value = value;
	} 
}

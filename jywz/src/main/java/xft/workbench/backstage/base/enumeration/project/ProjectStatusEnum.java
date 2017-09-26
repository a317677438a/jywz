package xft.workbench.backstage.base.enumeration.project;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum ProjectStatusEnum {

	@EnumValue("0")
	@EnumDesc("准备中")
	readying(0),

	@EnumValue("1")
	@EnumDesc("产品发行")
	product_distribution(1),
	
	@EnumValue("2")
	@EnumDesc("项目结束")
	project_close(2);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	ProjectStatusEnum(Integer value){
		this.value = value;
	}
}

package xft.workbench.backstage.base.enumeration.file;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

//项目文件类型
public enum ProjectFileVersion {
	
	@EnumValue("1")
	@EnumDesc("交流版")
	communicationVersion(1),
	
	@EnumValue("2")
	@EnumDesc("正式版")
	formalVersion(2);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	ProjectFileVersion(Integer value) {
		this.value = value;
	} 
	
}

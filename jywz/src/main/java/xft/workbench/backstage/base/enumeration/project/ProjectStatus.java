/**
 * 
 */
package xft.workbench.backstage.base.enumeration.project;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/** 
 *Title: enum of project status 
 *Company:kayak  
 *Makedate:2017-7-18 下午2:57:22 
 * @author hupp
 * 
 */
public enum ProjectStatus {
	
	@EnumValue("0")
	@EnumDesc("准备中")
	Ready("0"),
	
	@EnumValue("1")
	@EnumDesc("产品发行")
	Published("1"),
	
	@EnumValue("2")
	@EnumDesc("项目结束")
	Ended("2");
	
	private String value;	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private ProjectStatus(String value) {
		this.value = value;
	}
	
	
	
	

}

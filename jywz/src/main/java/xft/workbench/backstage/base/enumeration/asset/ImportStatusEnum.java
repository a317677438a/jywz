package xft.workbench.backstage.base.enumeration.asset;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 导入状态
 * @author panl
 *
 */
public enum ImportStatusEnum {

	@EnumValue("0")
	@EnumDesc("导入中")
	importing(0),
	
	@EnumValue("1")
	@EnumDesc("导入完成")
	finish(1);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	ImportStatusEnum(Integer value) {
		this.value = value;
	} 
}

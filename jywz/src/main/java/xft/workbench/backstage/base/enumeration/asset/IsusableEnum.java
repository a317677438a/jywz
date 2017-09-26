package xft.workbench.backstage.base.enumeration.asset;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 是否为世联信贷可用资产
 * @author Administrator
 *
 */
public enum IsusableEnum {
	@EnumValue("0")
	@EnumDesc("不可用")
	no(0),
	
	@EnumValue("1")
	@EnumDesc("可用")
	yes(1);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	IsusableEnum(Integer value) {
		this.value = value;
	} 
}

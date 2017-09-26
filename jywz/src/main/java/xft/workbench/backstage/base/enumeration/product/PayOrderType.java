package xft.workbench.backstage.base.enumeration.product;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

//资产还款方式
public enum PayOrderType {
	
	/**
	 * 1-违约事件发生前
	 */
	@EnumValue("1")
	@EnumDesc("违约事件发生前")
	before(1),
	
	/**
	 * 2-加速清偿
	 */
	@EnumValue("2")
	@EnumDesc("加速清偿")
	accelerate(2),
	
	/**
	 * 3-违约事件发生后
	 */
	@EnumValue("3")
	@EnumDesc("违约事件发生后")
	after(3);
	
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	PayOrderType(Integer value) {
		this.value = value;
	} 
	
	

}

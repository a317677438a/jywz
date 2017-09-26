package xft.workbench.backstage.base.enumeration.product;


//资产还款方式
public enum PayEventType {
	
	/**
	 * 费用
	 */
	fee(1),
	
	/**
	 * 利息
	 */
	interest(2),
	
	/**
	 * 本金
	 */
	investment(3);
	
	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	PayEventType(Integer value) {
		this.value = value;
	} 
	
	

}

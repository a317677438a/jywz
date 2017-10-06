package xft.workbench.backstage.base.enumeration.material;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ApplyStatus {
	
	  @EnumValue("1")
	    @EnumDesc("待审核")
	    packed(1, "待审核"),

	    @EnumValue("2")
	    @EnumDesc("审批拒绝")
	    refuse(2, "审批拒绝"),

	  	@EnumValue("3")
	    @EnumDesc("待领用（审批通过）")
	    pass(3, "待领用（审批通过）"),
	    
	    @EnumValue("4")
	    @EnumDesc("已领用")
	  	receive(4, "已领用");

	    private Integer value;
	    private String desc;

	    @JsonValue
	    public Integer getValue() {
	        return this.value;
	    }

	    public String getDesc() {
	        return desc;
	    }

	    ApplyStatus(Integer value, String desc) {
	        this.value = value;
	        this.desc = desc;
	    }

	    public static ApplyStatus valueOf(int ordinal) {
	        if (ordinal < 0 || ordinal >= values().length) {
	            throw new IndexOutOfBoundsException("Invalid ordinal " + ordinal);
	        }
	        return values()[ordinal];
	    }
}

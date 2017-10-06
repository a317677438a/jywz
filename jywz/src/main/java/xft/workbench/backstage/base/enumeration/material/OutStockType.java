package xft.workbench.backstage.base.enumeration.material;

import com.fasterxml.jackson.annotation.JsonValue;
import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 入库类型
 *
 * @author Huangyao
 */
public enum OutStockType {

    @EnumValue("1")
    @EnumDesc("申领出库")
    packed(1, "申领出库"),

    @EnumValue("2")
    @EnumDesc("移库出库")
    publish(2, "移库出库");

 

    private Integer value;
    private String desc;

    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getDesc() {
        return desc;
    }

    OutStockType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static OutStockType valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal " + ordinal);
        }
        return values()[ordinal];
    }


}

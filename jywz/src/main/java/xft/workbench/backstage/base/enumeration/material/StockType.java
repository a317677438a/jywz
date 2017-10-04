package xft.workbench.backstage.base.enumeration.material;

import com.fasterxml.jackson.annotation.JsonValue;
import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 入库类型
 *
 * @author Huangyao
 */
public enum StockType {

    @EnumValue("1")
    @EnumDesc("采购入库")
    packed(1, "采购入库"),

    @EnumValue("2")
    @EnumDesc("退库入库")
    publish(2, "退库入库"),

    @EnumValue("3")
    @EnumDesc("移库入库")
    disposal(3, "移库入库");

    private Integer value;
    private String desc;

    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getDesc() {
        return desc;
    }

    StockType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static StockType valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal " + ordinal);
        }
        return values()[ordinal];
    }


}

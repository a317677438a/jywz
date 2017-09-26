package xft.workbench.backstage.base.enumeration.asset;

import com.fasterxml.jackson.annotation.JsonValue;
import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 债权状态
 *
 * @author panl
 */
public enum LoanStatusEnum {

    @EnumValue("0")
    @EnumDesc("未标记")
    intoPool(0, "未标记"),

    @EnumValue("1")
    @EnumDesc("预标记")
    packed(1, "预标记"),

    @EnumValue("2")
    @EnumDesc("正式标记")
    publish(2, "正式标记"),

    @EnumValue("3")
    @EnumDesc("处置")
    disposal(3, "处置");

    private Integer value;
    private String desc;

    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getDesc() {
        return desc;
    }

    LoanStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static LoanStatusEnum valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal " + ordinal);
        }
        return values()[ordinal];
    }


}

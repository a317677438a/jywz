package xft.workbench.backstage.base.enumeration.apply;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 导入文件类型
 *
 * @author panl
 */
public enum UseTypeEnum {

    @EnumValue("1")
    @EnumDesc("安装使用")
    usetype_1(1),

    @EnumValue("2")
    @EnumDesc("培训使用")
    usetype_2(2);

    private Integer value;

    public Integer getValue() {
        return this.value;
    }

    UseTypeEnum(Integer value) {
        this.value = value;
    }

}

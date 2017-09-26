package xft.workbench.backstage.base.enumeration.asset;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 导入文件类型
 *
 * @author panl
 */
public enum FileTypeEnum {

    @EnumValue("1")
    @EnumDesc("基础资产信息")
    asset(1),

    @EnumValue("2")
    @EnumDesc("还款计划表信息")
    repayment(2);

    private Integer value;

    public Integer getValue() {
        return this.value;
    }

    FileTypeEnum(Integer value) {
        this.value = value;
    }

}

package xft.workbench.backstage.base.enumeration.asset;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum  FileSyncEnum {
    @EnumValue("0")
    @EnumDesc("手动同步")
    MANUAL(0),

    @EnumValue("1")
    @EnumDesc("第一次自动同步")
    FIRST(1),

    @EnumValue("2")
    @EnumDesc("第二次自动同步")
    SECOND(2);

    private Integer value;

    public Integer getValue(){
        return this.value;
    }

    FileSyncEnum(Integer value){
        this.value = value;
    }
}

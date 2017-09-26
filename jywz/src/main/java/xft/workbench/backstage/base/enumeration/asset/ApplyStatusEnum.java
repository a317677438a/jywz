package xft.workbench.backstage.base.enumeration.asset;

import com.fasterxml.jackson.annotation.JsonValue;
import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

/**
 * 资产标识申请状态
 * @author Administrator
 *
 */
public enum ApplyStatusEnum {

	@EnumValue("0")
	@EnumDesc("准备")
	preparation(0),

	@EnumValue("1")
	@EnumDesc("成功")
	successful(1),

	@EnumValue("2")
	@EnumDesc("失败")
	failure(2);

	private Integer value;

    @JsonValue
	public Integer getValue(){
		return this.value;
	}

	ApplyStatusEnum(Integer value){
		this.value = value;
	}

	public static ApplyStatusEnum valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal "+ordinal);
		}
		return values()[ordinal];
	}
}

package xft.workbench.backstage.base.enumeration;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;


/**
 * 机构类型
 * @author chenzh
 *
 */
public enum OrgTypeEnum {
	    @EnumValue("1")
		@EnumDesc("发起机构")
		organiser(1),
		
		@EnumValue("2")
		@EnumDesc("受托机构")
		issuer(2),
		
		@EnumValue("3")
		@EnumDesc("项目评级机构")
	    ratingAgencies(3),
		
		@EnumValue("4")
		@EnumDesc("项目增级机构")
		isenhancement(4),
		
		@EnumValue("5")
		@EnumDesc("财务顾问")
	    financeConsultant(5),
		
		@EnumValue("6")
		@EnumDesc("会计事务所")
		accountOffice(6),
		
		@EnumValue("7")
		@EnumDesc("律师事务所")
	    lawOffice(7),
		
		@EnumValue("8")
		@EnumDesc("管理机构")
		other(8),
		
		@EnumValue("9")
		@EnumDesc("投资人")
	    investor(9);
		
		private Integer value;

		public Integer getValue() {
			return value;
		}
		
		OrgTypeEnum(Integer value){
			this.value = value;
		}
}

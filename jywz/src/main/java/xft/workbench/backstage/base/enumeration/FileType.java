package xft.workbench.backstage.base.enumeration;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;

public enum FileType {
	
	@EnumValue("1")
	@EnumDesc("公司基本情况")
	companyInfo(1),
	
	@EnumValue("2")
	@EnumDesc("主营业务情况")
	business(2),
	
	@EnumValue("3")
	@EnumDesc("财务情况")
	financial(3),
	
	@EnumValue("4")
	@EnumDesc("机构其它文档")
	institutionOther(4),
	
	@EnumValue("101")
	@EnumDesc("初步信息汇总表")
	preliminaryInfo(101),
	
	@EnumValue("102")
	@EnumDesc("资产池信息")
	assetPoolInfo(102),
	
	@EnumValue("103")
	@EnumDesc("其他预评估期文档")
	otherPreEvaluation(103),
	
	@EnumValue("201")
	@EnumDesc("资料清单")
	dataList(201),
	
	@EnumValue("202")
	@EnumDesc("审核意见")
	auditOpinion(202),
	
	@EnumValue("203")
	@EnumDesc("尽调意见")
	dueDiligence(203),
	
	@EnumValue("204")
	@EnumDesc("其他产品设计期文档")
	otherProdDesign(204),
	
	@EnumValue("301")
	@EnumDesc("计划说明书")
	planDesc(301),
	
	@EnumValue("302")
	@EnumDesc("法律意见书")
	lawOpinion(302),
	
	@EnumValue("303")
	@EnumDesc("信用评估报告")
	reditRating(303),
	
	@EnumValue("304")
	@EnumDesc("会计处理意见")
	accountOpinion(304),
	
	@EnumValue("305")
	@EnumDesc("资产转让评估/现金流分析报告")
	assetSituation(305),
	
	@EnumValue("306")
	@EnumDesc("尽职调查报告")
	dueDiligenceReport(306),
	
	@EnumValue("307")
	@EnumDesc("交易合同文本")
	tradeContract(307),
	
	@EnumValue("308")
	@EnumDesc("其他发行期文档")
	otherRelease(308),
	
	@EnumValue("401")
	@EnumDesc("季度、年度托管报告")
	trusteeshipReport(401),
	
	@EnumValue("402")
	@EnumDesc("季度、年度资产管理报告")
	assetManagementReport(402),
	
	@EnumValue("403")
	@EnumDesc("年度审计报告")
	annualAuditReport(403),
	
	@EnumValue("404")
	@EnumDesc("历次分配报告")
	distributionReport(404),
	
	@EnumValue("405")
	@EnumDesc("后续评级报告")
	followupRatingReport(405),
	
	@EnumValue("406")
	@EnumDesc("其他存续期文档")
	otherDuration(406);

	private Integer value;
	
	public Integer getValue() {
		return this.value;
	}
	
	FileType(Integer value){
		this.value = value;
	}
}
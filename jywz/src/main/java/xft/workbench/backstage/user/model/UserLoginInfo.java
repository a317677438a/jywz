package xft.workbench.backstage.user.model;

public class UserLoginInfo {

	private Integer id; // 用户id
	private String loginname; // 登录名称
	private String passwd; // 登录密码，md加密
	private String username; // 操作用户名称
	private String orgid; // 机构代码
	private String userstatus; // 用户状态：0:正常(默认)，1：停用
	private String idtype; // 证件类型
	private String idno; // 证件号码
	private String sex; // 性别
	private String mobileno; // 移动电话
	private String officeno; // 办公电话
	private String homeno; // 住宅电话
	private String faxno; // 传真
	private String email; // 电子邮箱
	private String postcode; // 邮编
	private String address; // 地址
	private Integer sys_org_id;//机构id
	private String orgname;//机构名称
	private String orgtype;//orgtype
    
	
	private String sessionid;//会话id
	private String loginip;//登录IP
	
	private Integer pwderrtimes; //密码错误次数
	
	// char createdate; //发布日期
	// char modifydate; //最后修改日期
	// Integer pwderrtimes; //密码错误次数
	// String pwderrlockdt; //密码输错锁定时间（格式：yyyymmdd hhmmss）
	// char pwdsetdate; //密码重设日期
	// String lastlogintime; //最后登录时间
	// String lastloginstation; //上次登录站点
	private String usertype; // 用户类型：0-管理台增加用户，1-业务平台增加用户
	private String certificate; // 证书编号

	
	
	public UserLoginInfo() {
		
	}
	
	
	public UserLoginInfo(Integer id, String loginname, String passwd,
			String username, String orgid, String userstatus, String idtype,
			String idno, String sex, String mobileno, String officeno,
			String homeno, String faxno, String email, String postcode,
			String address, Integer sys_org_id, String orgname, String orgtype,
			String sessionid, String loginip, Integer pwderrtimes,
			String usertype, String certificate) {
		super();
		this.id = id;
		this.loginname = loginname;
		this.passwd = passwd;
		this.username = username;
		this.orgid = orgid;
		this.userstatus = userstatus;
		this.idtype = idtype;
		this.idno = idno;
		this.sex = sex;
		this.mobileno = mobileno;
		this.officeno = officeno;
		this.homeno = homeno;
		this.faxno = faxno;
		this.email = email;
		this.postcode = postcode;
		this.address = address;
		this.sys_org_id = sys_org_id;
		this.orgname = orgname;
		this.orgtype = orgtype;
		this.sessionid = sessionid;
		this.loginip = loginip;
		this.pwderrtimes = pwderrtimes;
		this.usertype = usertype;
		this.certificate = certificate;
	}



	public Integer getPwderrtimes() {
		return pwderrtimes;
	}

	public void setPwderrtimes(Integer pwderrtimes) {
		this.pwderrtimes = pwderrtimes;
	}

	public Integer getSys_org_id() {
		return sys_org_id;
	}

	public void setSys_org_id(Integer sys_org_id) {
		this.sys_org_id = sys_org_id;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getOfficeno() {
		return officeno;
	}

	public void setOfficeno(String officeno) {
		this.officeno = officeno;
	}

	public String getHomeno() {
		return homeno;
	}

	public void setHomeno(String homeno) {
		this.homeno = homeno;
	}

	public String getFaxno() {
		return faxno;
	}

	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

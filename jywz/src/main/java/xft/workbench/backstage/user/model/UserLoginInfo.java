package xft.workbench.backstage.user.model;

public class UserLoginInfo {

	private Integer id; // 用户id
	private String loginname; // 登录名称
	private String passwd; // 登录密码，md加密
	private Integer role;//角色
	private String organize_code; // 组织
	private String code; // 人员编号
	private String name; // 人员名称
	private String ophone; // 办公电话
	private String mphone; // 手机
	private String status; // 状态：1：正常，2：停用
    
	

	private String sessionid;//会话id
	private String loginip;//登录IP
	
	private Integer pwderrtimes; //密码错误次数
	
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


	public Integer getRole() {
		return role;
	}


	public void setRole(Integer role) {
		this.role = role;
	}


	public String getOrganize_code() {
		return organize_code;
	}


	public void setOrganize_code(String organize_code) {
		this.organize_code = organize_code;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getOphone() {
		return ophone;
	}


	public void setOphone(String ophone) {
		this.ophone = ophone;
	}


	public String getMphone() {
		return mphone;
	}


	public void setMphone(String mphone) {
		this.mphone = mphone;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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


	public Integer getPwderrtimes() {
		return pwderrtimes;
	}


	public void setPwderrtimes(Integer pwderrtimes) {
		this.pwderrtimes = pwderrtimes;
	}


	
	
	public UserLoginInfo() {
		
	}
	
	
}

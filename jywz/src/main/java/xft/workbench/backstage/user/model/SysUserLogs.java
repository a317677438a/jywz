package xft.workbench.backstage.user.model;

public class SysUserLogs {

	private Integer id; // 用户id
	private String sessionid; // 会话id
	private String sys_user_id; // 用户id
	private String loginname; // 用户登录名
	private String requestmapping; // 映射地址
	private String requestParams; // 请求参数
	private String opip; // 操作ip
	private String opdate; // 操作日期
	
	private String optime; // 操作时间
	public SysUserLogs(String sessionid, String sys_user_id,
			String loginname, String requestmapping, String requestParams,
			String opip, String opdate, String optime) {
		super();
		this.sessionid = sessionid;
		this.sys_user_id = sys_user_id;
		this.loginname = loginname;
		this.requestmapping = requestmapping;
		this.requestParams = requestParams;
		this.opip = opip;
		this.opdate = opdate;
		this.optime = optime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getSys_user_id() {
		return sys_user_id;
	}
	public void setSys_user_id(String sys_user_id) {
		this.sys_user_id = sys_user_id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getRequestmapping() {
		return requestmapping;
	}
	public void setRequestmapping(String requestmapping) {
		this.requestmapping = requestmapping;
	}
	public String getRequestParams() {
		return requestParams;
	}
	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}
	public String getOpip() {
		return opip;
	}
	public void setOpip(String opip) {
		this.opip = opip;
	}
	public String getOpdate() {
		return opdate;
	}
	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}
	public String getOptime() {
		return optime;
	}
	public void setOptime(String optime) {
		this.optime = optime;
	}
	
	@Override
	public String toString() {
		return "SysUserLogs [id=" + id + ", sessionid=" + sessionid
				+ ", sys_user_id=" + sys_user_id + ", loginname=" + loginname
				+ ", requestmapping=" + requestmapping + ", requestParams="
				+ requestParams + ", opip=" + opip + ", opdate=" + opdate
				+ ", optime=" + optime + "]";
	}
	
}

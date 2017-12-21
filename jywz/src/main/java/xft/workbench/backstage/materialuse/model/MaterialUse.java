package xft.workbench.backstage.materialuse.model;


public class MaterialUse {
	private  Integer id;
	private  Integer jy_material_id;
	private  Integer use_type;
	private  String use_workno;
	private  Integer use_user;
	private  String use_date;
	private  Integer use_number;
	private  Integer status;
	private  String remark;
	private  Integer inputuser;
	private  String crt_date;
	private  String crt_time;
	
	public Integer getUse_type() {
		return use_type;
	}
	public void setUse_type(Integer use_type) {
		this.use_type = use_type;
	}
	public String getUse_workno() {
		return use_workno;
	}
	public void setUse_workno(String use_workno) {
		this.use_workno = use_workno;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getJy_material_id() {
		return jy_material_id;
	}
	public void setJy_material_id(Integer jy_material_id) {
		this.jy_material_id = jy_material_id;
	}
	public Integer getUse_user() {
		return use_user;
	}
	public void setUse_user(Integer use_user) {
		this.use_user = use_user;
	}
	public String getUse_date() {
		return use_date;
	}
	public void setUse_date(String use_date) {
		this.use_date = use_date;
	}
	public Integer getUse_number() {
		return use_number;
	}
	public void setUse_number(Integer use_number) {
		this.use_number = use_number;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getInputuser() {
		return inputuser;
	}
	public void setInputuser(Integer inputuser) {
		this.inputuser = inputuser;
	}
	public String getCrt_date() {
		return crt_date;
	}
	public void setCrt_date(String crt_date) {
		this.crt_date = crt_date;
	}
	public String getCrt_time() {
		return crt_time;
	}
	public void setCrt_time(String crt_time) {
		this.crt_time = crt_time;
	}
}

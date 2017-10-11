package xft.workbench.backstage.storehouseout.model;

public class Storehoseout {
	private  Integer id;
	private  String putout_code;
	private  Integer putout_type;
	private  Integer putout_user;
	private  String putout_date;
	private  String putout_storehouse_code;
	private  Integer apply_user;
	private  String putin_storehouse_code;
	private  Integer status;
	private  String remark;
	private  Integer inputuser;
	private  String crt_date;
	private  String crt_time;
	
	public Storehoseout(){
	}
	
	public Storehoseout(Integer id, String putout_code, Integer putout_type,
			Integer putout_user, String putout_date,
			String putout_storehouse_code, Integer apply_user,
			String putin_storehouse_code, Integer status, String remark,
			Integer inputuser, String crt_date, String crt_time) {
		super();
		this.id = id;
		this.putout_code = putout_code;
		this.putout_type = putout_type;
		this.putout_user = putout_user;
		this.putout_date = putout_date;
		this.putout_storehouse_code = putout_storehouse_code;
		this.apply_user = apply_user;
		this.putin_storehouse_code = putin_storehouse_code;
		this.status = status;
		this.remark = remark;
		this.inputuser = inputuser;
		this.crt_date = crt_date;
		this.crt_time = crt_time;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPutout_code() {
		return putout_code;
	}
	public void setPutout_code(String putout_code) {
		this.putout_code = putout_code;
	}
	public Integer getPutout_type() {
		return putout_type;
	}
	public void setPutout_type(Integer putout_type) {
		this.putout_type = putout_type;
	}
	public Integer getPutout_user() {
		return putout_user;
	}
	public void setPutout_user(Integer putout_user) {
		this.putout_user = putout_user;
	}
	public String getPutout_date() {
		return putout_date;
	}
	public void setPutout_date(String putout_date) {
		this.putout_date = putout_date;
	}
	public String getPutout_storehouse_code() {
		return putout_storehouse_code;
	}
	public void setPutout_storehouse_code(String putout_storehouse_code) {
		this.putout_storehouse_code = putout_storehouse_code;
	}
	public Integer getApply_user() {
		return apply_user;
	}
	public void setApply_user(Integer apply_user) {
		this.apply_user = apply_user;
	}
	public String getPutin_storehouse_code() {
		return putin_storehouse_code;
	}
	public void setPutin_storehouse_code(String putin_storehouse_code) {
		this.putin_storehouse_code = putin_storehouse_code;
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

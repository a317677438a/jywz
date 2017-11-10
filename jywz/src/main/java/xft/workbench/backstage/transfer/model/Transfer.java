package xft.workbench.backstage.transfer.model;

/**
 * 入库主体信息
 * 
 * @author huangyao
 *
 */
public class Transfer {
	
	//id
	private Integer id;
	
	//调拨单号
	private String transfer_code;
	
	//调拨类型
	private Integer transfer_type;
	
	//调拨日期
	private String transfer_date;
	
	//入库人
	private Integer putin_user;
	
	//入库仓库编码
	private String putin_storehouse_code;
	
	//出库人
	private Integer putout_user;
	
	//出库仓库编码
	private String putout_storehouse_code;
	
	//状态
	private Integer status;
	
	//备注
	private String remark;
	
	//操作人
	private String inputuser;
	
	//创建日期
	private String crt_date;
	
	//创建时间
	private String crt_time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTransfer_code() {
		return transfer_code;
	}

	public void setTransfer_code(String transfer_code) {
		this.transfer_code = transfer_code;
	}

	public Integer getTransfer_type() {
		return transfer_type;
	}

	public void setTransfer_type(Integer transfer_type) {
		this.transfer_type = transfer_type;
	}

	public String getTransfer_date() {
		return transfer_date;
	}

	public void setTransfer_date(String transfer_date) {
		this.transfer_date = transfer_date;
	}

	public Integer getPutin_user() {
		return putin_user;
	}

	public void setPutin_user(Integer putin_user) {
		this.putin_user = putin_user;
	}

	public String getPutin_storehouse_code() {
		return putin_storehouse_code;
	}

	public void setPutin_storehouse_code(String putin_storehouse_code) {
		this.putin_storehouse_code = putin_storehouse_code;
	}

	public Integer getPutout_user() {
		return putout_user;
	}

	public void setPutout_user(Integer putout_user) {
		this.putout_user = putout_user;
	}

	public String getPutout_storehouse_code() {
		return putout_storehouse_code;
	}

	public void setPutout_storehouse_code(String putout_storehouse_code) {
		this.putout_storehouse_code = putout_storehouse_code;
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

	public String getInputuser() {
		return inputuser;
	}

	public void setInputuser(String inputuser) {
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

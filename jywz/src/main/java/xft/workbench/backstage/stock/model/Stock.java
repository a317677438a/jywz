package xft.workbench.backstage.stock.model;

/**
 * 入库主体信息
 * 
 * @author huangyao
 *
 */
public class Stock {
	
	//id
	private Integer id;
	
	//入库类型
	private Integer putin_type;
	
	//入库人
	private Integer putin_user;
	
	//入库日期
	private String putin_date;
	
	//入库仓库编码
	private String putin_storehouse_code;
	
	//退库人
	private Integer cancel_user;
	
	//合同编号
	private String contract_no;
	
	//出库仓库编码
	private String putout_storehouse_code;
	
	//状态
	private String status;
	
	//备注
	private String remark;
	
	//操作人
	private String inputuser;
	
	//创建日期
	private String crt_date;
	
	//创建时间
	private String crt_time;
	
	//入库单号
	private String putin_code;

	

	public String getPutin_code() {
		return putin_code;
	}

	public void setPutin_code(String putin_code) {
		this.putin_code = putin_code;
	}

	public Stock(Integer id, String putin_code,Integer putin_type, Integer putin_user,
			String putin_date, String putin_storehouse_code,
			Integer cancel_user, String contract_no,
			String putout_storehouse_code, String status, String remark,
			String inputuser, String crt_date, String crt_time) {
		super();
		this.id = id;
		this.putin_code = putin_code;
		this.putin_type = putin_type;
		this.putin_user = putin_user;
		this.putin_date = putin_date;
		this.putin_storehouse_code = putin_storehouse_code;
		this.cancel_user = cancel_user;
		this.contract_no = contract_no;
		this.putout_storehouse_code = putout_storehouse_code;
		this.status = status;
		this.remark = remark;
		this.inputuser = inputuser;
		this.crt_date = crt_date;
		this.crt_time = crt_time;
	}

	public Stock() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPutin_type() {
		return putin_type;
	}

	public void setPutin_type(Integer putin_type) {
		this.putin_type = putin_type;
	}

	public Integer getPutin_user() {
		return putin_user;
	}

	public void setPutin_user(Integer putin_user) {
		this.putin_user = putin_user;
	}

	public String getPutin_date() {
		return putin_date;
	}

	public void setPutin_date(String putin_date) {
		this.putin_date = putin_date;
	}

	public String getPutin_storehouse_code() {
		return putin_storehouse_code;
	}

	public void setPutin_storehouse_code(String putin_storehouse_code) {
		this.putin_storehouse_code = putin_storehouse_code;
	}

	public Integer getCancel_user() {
		return cancel_user;
	}

	public void setCancel_user(Integer cancel_user) {
		this.cancel_user = cancel_user;
	}

	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}

	public String getPutout_storehouse_code() {
		return putout_storehouse_code;
	}

	public void setPutout_storehouse_code(String putout_storehouse_code) {
		this.putout_storehouse_code = putout_storehouse_code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

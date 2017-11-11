package xft.workbench.backstage.warn.model;


public class Message {
	private  Integer id;
	private  Integer message_user;
	private  String message_title;
	private  String message_content;
	private  Integer status;
	private  Integer inputuser;
	private  String crt_date;
	private  String crt_time;
	
	public Message(){
		
	}
	
	public Message(Integer id,Integer message_user, String message_title, String message_content,
			Integer status, Integer inputuser, String crt_date, String crt_time) {
		super();
		this.id = id;
		this.message_user = message_user;
		this.message_title = message_title;
		this.message_content = message_content;
		this.status = status;
		this.inputuser = inputuser;
		this.crt_date = crt_date;
		this.crt_time = crt_time;
	}
	
	public Integer getMessage_user() {
		return message_user;
	}

	public void setMessage_user(Integer message_user) {
		this.message_user = message_user;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage_title() {
		return message_title;
	}
	public void setMessage_title(String message_title) {
		this.message_title = message_title;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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

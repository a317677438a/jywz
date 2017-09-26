package xft.workbench.backstage.user.model;

public class MenuDao {
	private Integer id;//菜单id
	private String menuname;//菜单名称
	private String actionname;//功能名称
	private String sys_menu_detail_id;//功能id
	private String requestmapping;//功能映射url
	private String upperid;//菜单上一级id
	
	public String getUpperid() {
		return upperid;
	}
	public void setUpperid(String upperid) {
		this.upperid = upperid;
	}
	public String getSys_menu_detail_id() {
		return sys_menu_detail_id;
	}
	public void setSys_menu_detail_id(String sys_menu_detail_id) {
		this.sys_menu_detail_id = sys_menu_detail_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getActionname() {
		return actionname;
	}
	public void setActionname(String actionname) {
		this.actionname = actionname;
	}
	public String getRequestmapping() {
		return requestmapping;
	}
	public void setRequestmapping(String requestmapping) {
		this.requestmapping = requestmapping;
	}
}

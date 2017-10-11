package xft.workbench.backstage.storehouseout.model;

public class StorehoseoutDetail {
	private Integer jy_storehouse_out_id;
	private Integer jy_material_id;
	private Integer putout_number;
	
	public StorehoseoutDetail(){}
	
	public StorehoseoutDetail(Integer jy_storehouse_out_id,
			Integer jy_material_id, Integer putout_number) {
		super();
		this.jy_storehouse_out_id = jy_storehouse_out_id;
		this.jy_material_id = jy_material_id;
		this.putout_number = putout_number;
	}
	public Integer getJy_storehouse_out_id() {
		return jy_storehouse_out_id;
	}
	public void setJy_storehouse_out_id(Integer jy_storehouse_out_id) {
		this.jy_storehouse_out_id = jy_storehouse_out_id;
	}
	public Integer getJy_material_id() {
		return jy_material_id;
	}
	public void setJy_material_id(Integer jy_material_id) {
		this.jy_material_id = jy_material_id;
	}
	public Integer getPutout_number() {
		return putout_number;
	}
	public void setPutout_number(Integer putout_number) {
		this.putout_number = putout_number;
	}
}

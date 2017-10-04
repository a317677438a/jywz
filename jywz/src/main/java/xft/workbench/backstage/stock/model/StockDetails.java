package xft.workbench.backstage.stock.model;

/**
 * 入库单明细
 * 
 * @author huangyao
 *
 */
public class StockDetails {
	
	//入库单主表系统id
	private Integer jy_storehouse_in_id;
	
	//物资表表系统id
	private Integer jy_material_id;
	
	//入库数量
	private Integer putin_number;

	public Integer getJy_storehouse_in_id() {
		return jy_storehouse_in_id;
	}

	public void setJy_storehouse_in_id(Integer jy_storehouse_in_id) {
		this.jy_storehouse_in_id = jy_storehouse_in_id;
	}

	public Integer getJy_material_id() {
		return jy_material_id;
	}

	public void setJy_material_id(Integer jy_material_id) {
		this.jy_material_id = jy_material_id;
	}

	public Integer getPutin_number() {
		return putin_number;
	}

	public void setPutin_number(Integer putin_number) {
		this.putin_number = putin_number;
	}

	public StockDetails(Integer jy_storehouse_in_id, Integer jy_material_id,
			Integer putin_number) {
		super();
		this.jy_storehouse_in_id = jy_storehouse_in_id;
		this.jy_material_id = jy_material_id;
		this.putin_number = putin_number;
	}
	
	public StockDetails() {
		// TODO Auto-generated constructor stub
	}
	
}

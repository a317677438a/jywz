package xft.workbench.backstage.transfer.model;

/**
 * 入库单明细
 * 
 * @author huangyao
 *
 */
public class TransferDetail {
	
	//入库单主表系统id
	private Integer jy_transfer_id;
	
	//物资表表系统id
	private Integer jy_material_id;
	
	//入库数量
	private Integer transfer_number;

	public Integer getJy_transfer_id() {
		return jy_transfer_id;
	}

	public void setJy_transfer_id(Integer jy_transfer_id) {
		this.jy_transfer_id = jy_transfer_id;
	}

	public Integer getJy_material_id() {
		return jy_material_id;
	}

	public void setJy_material_id(Integer jy_material_id) {
		this.jy_material_id = jy_material_id;
	}

	public Integer getTransfer_number() {
		return transfer_number;
	}

	public void setTransfer_number(Integer transfer_number) {
		this.transfer_number = transfer_number;
	}
	
}

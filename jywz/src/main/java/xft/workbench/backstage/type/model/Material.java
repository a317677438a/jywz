package xft.workbench.backstage.type.model;

/**
 * 物资  实体
 * 
 * @author huangyao
 *
 */
public class Material {
	
	//id
	private Integer id;
	
	//物资类型id
	private Integer jy_material_type_id;
	
	//物资编码
	private String code;
	
	//物资名称
	private String name;
	
	//规格型号
	private String model;
	
	//供应商（厂家）
	private String supplier;

	public Material(Integer id, Integer jy_material_type_id, String code,
			String name, String model, String supplier) {
		super();
		this.id = id;
		this.jy_material_type_id = jy_material_type_id;
		this.code = code;
		this.name = name;
		this.model = model;
		this.supplier = supplier;
	}

	public Material() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getJy_material_type_id() {
		return jy_material_type_id;
	}

	public void setJy_material_type_id(Integer jy_material_type_id) {
		this.jy_material_type_id = jy_material_type_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	
	
}

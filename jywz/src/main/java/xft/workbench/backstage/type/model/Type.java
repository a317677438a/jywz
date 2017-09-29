package xft.workbench.backstage.type.model;

/**
 * 物资类型实体类
 * @author huangyao
 *
 */
public class Type {
	
	//id
	private Integer id;
	
	//物资类型
	private String code;
	
	//物资名称
	private String name;

	public Type() {
		
	}
	
	public Type(Integer id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	
}

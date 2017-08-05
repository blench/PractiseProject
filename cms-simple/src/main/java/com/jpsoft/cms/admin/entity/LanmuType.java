package com.jpsoft.cms.admin.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="lanmu_type1")
public class LanmuType implements Serializable {
	
	private String id;
	private String name;
	private Integer shuju;
//	private Set<Lanmu> lanmus;
	@Id
	@Column(name = "id_")
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	/**
	 * 获取栏目唯一的编号
	 * @return
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="lanmu_type_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="shuju")
	public Integer getShuju() {
		return shuju;
	}

	public void setShuju(Integer shuju) {
		this.shuju = shuju;
	}
	
//	@OneToMany(cascade =  { CascadeType.PERSIST, CascadeType.REFRESH,
//            CascadeType.MERGE, CascadeType.REMOVE }, mappedBy = "lanmuTypeId", fetch = FetchType.EAGER)
//	public Set<Lanmu> getLanmus() {
//		return lanmus;
//	}
//
//	public void setLanmus(Set<Lanmu> lanmus) {
//		this.lanmus = lanmus;
//	}
	
}

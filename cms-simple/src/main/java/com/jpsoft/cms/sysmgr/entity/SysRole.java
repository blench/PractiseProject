package com.jpsoft.cms.sysmgr.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.hibernate.annotations.GenericGenerator;

/**
  作者:zq
  时间:2014-07-14 15:49:25
  描述:sys_role的实体类
 */
@Entity
@Table(name = "sys_role")
public class SysRole implements Serializable{
	private String id;
	private String name;
	private String description;
	
	@Id
	@Column(name="id_")
	@GeneratedValue(generator = "hibernate-uuid") 
	@GenericGenerator(name="hibernate-uuid",strategy = "uuid")
	/**
	 *获取角色编号
	 */
	public String getId(){
		return id;
	}
	
		
	/**
	 *设置角色编号
	 */
	public void setId(String id){
		this.id = id;
	}
	@Column(name="name_")
	/**
	 *获取角色名称
	 */
	public String getName(){
		return name;
	}
	
		
	/**
	 *设置角色名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 角色描述
	 * @return
	 */
	@Column(name="description_")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
package com.jpsoft.cms.office.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 创建者表
 * @author chenxiyu
 *
 */
@Entity
@Table(name="user")
public class User implements Serializable{
	
	private String id;
	private String username;
	private String password;
	

	@Id
	@Column(name="id_")
	@GenericGenerator(name="hibernate-uuid",strategy="uuid")
	@GeneratedValue(generator="hibernate-uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="username_")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name="password_")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

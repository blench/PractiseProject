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
  时间:2014-07-14 15:49:26
  描述:sys_user_role的实体类
 */
@Entity
@Table(name = "sys_user_role")
public class SysUserRole implements Serializable{
	private String id;
	private String userId;
	private String roleId;
	
	@Id
	@Column(name="id_")
	@GeneratedValue(generator = "hibernate-uuid") 
	@GenericGenerator(name="hibernate-uuid",strategy = "uuid")
	/**
	 *获取
	 */
	public String getId(){
		return id;
	}
	
		
	/**
	 *设置
	 */
	public void setId(String id){
		this.id = id;
	}
	@Column(name="user_id")
	/**
	 *获取用户编号
	 */
	public String getUserId(){
		return userId;
	}
	
		
	/**
	 *设置用户编号
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	@Column(name="role_id")
	/**
	 *获取角色编号
	 */
	public String getRoleId(){
		return roleId;
	}
	
		
	/**
	 *设置角色编号
	 */
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}
}
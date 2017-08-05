package com.jpsoft.cms.sysmgr.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 作者:zq 时间:2014-07-14 15:49:26 描述:sys_user的实体类
 */
@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable {
	private String id;
	private String name;
	private String realName;
	private String password;
	private String plainPassword;
	private Date createDate;
	private Date lastVisitDate;
	private String remoteAddr;
	private Integer userType;

	@Id
	@Column(name = "id_")
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	/**
	 *获取用户编号
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置用户编号
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name_")
	/**
	 *获取用户名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置用户名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password_")
	/**
	 *获取用户密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置用户密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "real_name")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	// 不持久化到数据库，也不显示在Restful接口的属性.
	@Transient
	@JsonIgnore
	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	@Column(name = "create_date")
	@JsonIgnore
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Transient
	public String getCreateDateF(){
		if (this.createDate!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
			
			return sdf.format(this.createDate);
		}
		else{
			return null;
		}
	}

	@Column(name = "last_visit_date")
	public Date getLastVisitDate() {
		return lastVisitDate;
	}

	public void setLastVisitDate(Date lastVisitDate) {
		this.lastVisitDate = lastVisitDate;
	}
	
	@Transient
	public String getLastVisitDateF(){
		if (this.lastVisitDate!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
			
			return sdf.format(this.lastVisitDate);
		}
		else{
			return null;
		}
	}

	@Column(name = "remote_addr")
	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	/**
	 * 用户类型1-税务人员，2-企业人员
	 * @return
	 */
	@Column(name = "user_type")
	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
}
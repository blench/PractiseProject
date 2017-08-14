package com.jpsoft.cms.office.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 办公用品申请表
 * @author chenxiyu
 *
 */
@Entity
@Table(name="office_application")
public class OfficeApplication implements Serializable{
	
	private String id;
	private String subject;
	private String applicant;
	private String sortNo;
	private Date createTime;
	private String creator;
	private String isverify;
	
	/**
	 * 一对多的设置，一个人可以申请多个办公用品，
	 * 
	 */
	private Set<OfficeSupplies>  offices;
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
	
	@Column(name="subject_")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(name="applicant_")
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	
	@Column(name="sort_no")
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}
	
	@Transient
	public String getCreateTimeF()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		
		String formatTime = "";
		if(createTime!=null)
		{
			formatTime = sdf.format(createTime);
		}
		return formatTime;
	}
	
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="creator_")
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name="isverify_")
	public String getIsverify() {
		return isverify;
	}
	public void setIsverify(String isverify) {
		this.isverify = isverify;
	}
	
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="applices_application_id")
	public Set<OfficeSupplies> getOffices() {
		return offices;
	}
	public void setOffices(Set<OfficeSupplies> offices) {
		this.offices = offices;
	}
	
	
}

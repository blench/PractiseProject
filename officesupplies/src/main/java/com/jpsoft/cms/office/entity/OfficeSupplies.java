package com.jpsoft.cms.office.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * 办公用品表
 */
@Entity
@Table(name="office_supplies")
public class OfficeSupplies implements Serializable{
	
	private String id;
	private String name;
	private String spec;
	private String unit;
	private String brand;
	private String provider;
	private String type;
	private Date createTime;
	private String creator;
	private String pic;
	private String sortNo;
//	private OfficeApplication officeApplication;
	
	@Id
	@Column(name="oid_")
	@GenericGenerator(name="hibernate-uuid",strategy="uuid")
	@GeneratedValue(generator="hibernate-uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="name_")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="spec_")
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	@Column(name="unit_")
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
	@Column(name="brand_")
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Column(name="provider_")
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	@Column(name="type_")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	@Column(name="pic_")
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	@Column(name="sort_no")
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	
	
	
	
	
}

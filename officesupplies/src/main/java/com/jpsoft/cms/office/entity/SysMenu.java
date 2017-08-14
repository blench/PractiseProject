package com.jpsoft.cms.office.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.text.SimpleDateFormat;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
  作者:zq
  时间:2014-07-14 15:49:23
  描述:sys_menu的实体类
 */
@Entity
@Table(name = "sys_menu")
public class SysMenu implements Serializable{
	private String id;
	private String name;
	private String pageUrl;
	private String displayType;
	private Integer sortNo;
	private String code;
	private SysMenu parent;
	private Set<SysMenu> children;


	@Id
	@Column(name="id_")
	@GeneratedValue(generator = "hibernate-uuid") 
	@GenericGenerator(name="hibernate-uuid",strategy = "uuid")
	/**
	 *获取菜单编号
	 */
	public String getId(){
		return id;
	}
	
		
	/**
	 *设置菜单编号
	 */
	public void setId(String id){
		this.id = id;
	}
	@Column(name="name_")
	/**
	 *获取菜单名称
	 */
	public String getName(){
		return name;
	}
	
		
	/**
	 *设置菜单名称
	 */
	public void setName(String name){
		this.name = name;
	}
	@Column(name="page_url")
	/**
	 *获取页面地址
	 */
	public String getPageUrl(){
		return pageUrl;
	}
	
		
	/**
	 *设置页面地址
	 */
	public void setPageUrl(String pageUrl){
		this.pageUrl = pageUrl;
	}
	@Column(name="display_type")
	/**
	 *获取显示方式(左侧菜单menu;按钮button)
	 */
	public String getDisplayType(){
		return displayType;
	}
	
		
	/**
	 *设置显示方式(左侧菜单menu;按钮button)
	 */
	public void setDisplayType(String displayType){
		this.displayType = displayType;
	}
	
	
	@Column(name="sort_no")
	/**
	 *获取排序号
	 */
	public Integer getSortNo(){
		return sortNo;
	}
	
		
	/**
	 *设置排序号
	 */
	public void setSortNo(Integer sortNo){
		this.sortNo = sortNo;
	}
	@Column(name="code_")
	/**
	 *获取菜单编码
	 */
	public String getCode(){
		return code;
	}
	
		
	/**
	 *设置菜单编码
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * 上级菜单
	 * @return
	 */
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "PARENT_ID")
	@JsonIgnore
	public SysMenu getParent() {
		return parent;
	}

	public void setParent(SysMenu parent) {
		this.parent = parent;
	}
	
	/**
	 * 子菜单
	 * @return
	 */
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("code asc")
	@JsonIgnore
	public Set<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(Set<SysMenu> children) {
		this.children = children;
	}

	 
	@Transient
	public String getParentName(){
		if(parent!=null){
			return parent.getName();
		}
		else{
			return null;
		}
	}
	
	public void setParentName(String name){
		if(parent!=null){
			parent.setName(name);
		}
	}

	
	
}
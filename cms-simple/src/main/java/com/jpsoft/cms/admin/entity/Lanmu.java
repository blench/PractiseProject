package com.jpsoft.cms.admin.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpsoft.cms.sysmgr.entity.SysMenu;

/*
 * 栏目表
 */
@Entity
@Table(name = "cms_lanmu")
public class Lanmu implements Serializable {
	private String id;
	private String name;
	private String bianma;
	private Integer sortNo;
	private Date createTime;
	private String creator;
	private String cover;

	private String pageUrl;
	private LanmuType lanmuTypeId;
	private String currentLanmu;
	private Set<Lanmu> children;
	private Lanmu parent;
	private String displayType;
	@Id
	@Column(name = "id_")
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	/**
	 *获取菜单编号
	 */
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "sort_no")
	public Integer getSortNo() {
		return sortNo;
	}
	


	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}


	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 获取创建时间格式化
	 */
	@Transient
	public String getCreateTimeF() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		String formatTime = "";

		if (createTime != null) {
			formatTime = sdf.format(createTime);
		}

		return formatTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	
	@Column(name="lanmu_ming_")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="lanmu_bianma")
	public String getBianma() {
		return bianma;
	}

	public void setBianma(String bianma) {
		this.bianma = bianma;
	}
	
	@Column(name="creator")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name="lanmu_cover")
	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
	
	
	
	
	@Column(name="page_url")
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	
	
	/**
	 * 上级菜单
	 * @return
	 */
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	@JsonIgnore
	public Lanmu getParent() {
		return parent;
	}

	public void setParent(Lanmu parent) {
		this.parent = parent;
	}

	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "LANMU_TYPE_ID")
	@JsonIgnore
	public LanmuType getLanmuTypeId() {
		return lanmuTypeId;
	}

	public void setLanmuTypeId(LanmuType lanmuTypeId) {
		this.lanmuTypeId = lanmuTypeId;
	}
	
	
	@Column(name="current_lanmu")
	public String getCurrentLanmu() {
		return currentLanmu;
	}

	public void setCurrentLanmu(String currentLanmu) {
		this.currentLanmu = currentLanmu;
	}
	
	
	/**
	 * 得到菜单名
	 * @return
	 */
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
	
//	/**
//	 * 子菜单
//	 * @return
//	 */
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("sortNo asc")
	@JsonIgnore
	public Set<Lanmu> getChildren() {
		return children;
	}

	public void setChildren(Set<Lanmu> children) {
		this.children = children;
	}
//	
	/**
	 * 菜单选项如何
	 */
	
	
	@Column(name="display_type")
	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	

	
	

//	/**
//	 * 所属栏目
//	 * @return
//	 */
//	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
//	@JoinColumn(name = "MENU_ID")
//	public SysMenu getMenu() {
//		return menu;
//	}
//
//	public void setMenu(SysMenu menu) {
//		this.menu = menu;
//	}
	
}
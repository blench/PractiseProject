package com.jpsoft.cms.admin.entity;

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
import com.jpsoft.cms.sysmgr.entity.SysMenu;

@Entity
@Table(name = "cms_news")
public class News implements Serializable {
	private String id;
	private String title;
	private String content;
	private Date createTime;
	private String creator;
	private String author;
	private Integer sortNo;
	private String cover;
//	private SysMenu menu;
	private Lanmu lanmuId;
	public void setId(String id) {
		this.id = id;
	}

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

	@Column(name = "sort_no")
	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	@Column(name = "title_")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@Column(name = "author_")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "content_")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="creator_")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name="cover")
	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
	
	
	/**
	 * 所属栏目
	 * @return
	 */
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "LANMU_ID")
	public Lanmu getLanmuId() {
		return lanmuId;
	}

	public void setLanmuId(Lanmu lanmuId) {
		this.lanmuId = lanmuId;
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
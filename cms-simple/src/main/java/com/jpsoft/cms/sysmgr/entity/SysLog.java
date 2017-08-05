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

@Entity
@Table(name = "sys_log")
public class SysLog implements Serializable{
	private String logId;
	private String userName;
	private String content;
	private Date createTime;
	
	@Column(name="log_id")
	@Id
	@GeneratedValue(generator = "hibernate-uuid") 
	@GenericGenerator(name="hibernate-uuid",strategy = "uuid")
	/**
	 *获取
	 */
	public String getLogId(){
		return logId;
	}
	/**
	 *设置
	 */
	public void setLogId(String logId){
		this.logId = logId;
	}
	@Column(name="user_name")
	/**
	 *获取
	 */
	public String getUserName(){
		return userName;
	}
	/**
	 *设置
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}
	@Column(name="content_",length=1000)
	/**
	 *获取
	 */
	public String getContent(){
		return content;
	}
	
		
	/**
	 *设置
	 */
	public void setContent(String content){
		this.content = content;
	}
	@Column(name="create_time")
	/**
	 *获取
	 */
	public Date getCreateTime(){
		return createTime;
	}
	
		/**
	 *获取格式化
	 */
	@Transient
	public String getCreateTimeF(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		String formatTime = "";
		
		if (createTime!=null) {
			formatTime =  sdf.format(createTime);
		}
		
		return formatTime;
	}	
		
	/**
	 *设置
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
}
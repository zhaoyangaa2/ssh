package com.tedu.entity;

import java.io.Serializable;

public class U_family implements Serializable {

	private int u_familyId;
	private int userId;
	private int familyId;
	private int fcreaterId;
	private int isRead;
	private int isRefuse;
	
	private String username;
	private String familyname;
	
	
	public int getU_familyId() {
		return u_familyId;
	}
	public void setU_familyId(int u_familyId) {
		this.u_familyId = u_familyId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFamilyId() {
		return familyId;
	}
	public void setFamilyId(int familyId) {
		this.familyId = familyId;
	}
	public int getFcreaterId() {
		return fcreaterId;
	}
	public void setFcreaterId(int fcreaterId) {
		this.fcreaterId = fcreaterId;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public int getIsRefuse() {
		return isRefuse;
	}
	public void setIsRefuse(int isRefuse) {
		this.isRefuse = isRefuse;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFamilyname() {
		return familyname;
	}
	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

}

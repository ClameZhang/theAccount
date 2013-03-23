package com.example.theaccount.db.table;

import android.graphics.Bitmap;

public class UserInfo {
	private int id;
	private Bitmap UserBitmap;
	private String name;
	private String nickName;
	private String telNum;

	public Bitmap getUserBitmap() {
		return UserBitmap;
	}
	
	public void setUserBitmap(Bitmap userBitmap) {
		UserBitmap = userBitmap;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getTelNum() {
		return telNum;
	}
	
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}	
}

package com.ovi_rahim.online;

public class details {
	
	private int id;
	private String name;
	
	public details(){}
	
	public details(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public void setProduct_Price(int id){
		this.id = id;
	}
	
	public void setProduct_Details(String name){
		this.name = name;
	}
	
	public int getProduct_Price(){
		return this.id;
	}
	
	public String getProduct_Details(){
		return this.name;
	}

}

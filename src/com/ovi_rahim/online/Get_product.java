package com.ovi_rahim.online;

public class Get_product {
	
	private int id;
	private String name;
	
	public Get_product(){}
	
	public Get_product(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public void setProduct_id(int id){
		this.id = id;
	}
	
	public void setProduct_name(String name){
		this.name = name;
	}
	
	public int getProduct_Id(){
		return this.id;
	}
	
	public String getProduct_name(){
		return this.name;
	}

}

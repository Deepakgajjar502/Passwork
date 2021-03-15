package com.b2b.passwork.Model.Category;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoryResponse{

	@SerializedName("data")
	private ArrayList<CategoryModel> category;

	@SerializedName("status")
	private int status;

	public void setCategory(ArrayList<CategoryModel> data){
		this.category = data;
	}

	public ArrayList<CategoryModel> getCategory(){
		return category;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}
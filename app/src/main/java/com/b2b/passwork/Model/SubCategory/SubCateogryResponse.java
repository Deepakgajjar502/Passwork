package com.b2b.passwork.Model.SubCategory;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubCateogryResponse{

	@SerializedName("data")
	private ArrayList<SubCategoryModel> data;

	@SerializedName("status")
	private int status;

	public void setData(ArrayList<SubCategoryModel> data){
		this.data = data;
	}

	public ArrayList<SubCategoryModel> getData(){
		return data;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}
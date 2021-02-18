package com.b2b.passwork.Model.FloorList;

import com.google.gson.annotations.SerializedName;

public class FloorsItem{

	@SerializedName("floor_name")
	private String floorName;

	@SerializedName("workspace_id")
	private int workspaceId;

	@SerializedName("floor_description")
	private String floorDescription;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("floor_id")
	private int floorId;

	public void setFloorName(String floorName){
		this.floorName = floorName;
	}

	public String getFloorName(){
		return floorName;
	}

	public void setWorkspaceId(int workspaceId){
		this.workspaceId = workspaceId;
	}

	public int getWorkspaceId(){
		return workspaceId;
	}

	public void setFloorDescription(String floorDescription){
		this.floorDescription = floorDescription;
	}

	public String getFloorDescription(){
		return floorDescription;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setFloorId(int floorId){
		this.floorId = floorId;
	}

	public int getFloorId(){
		return floorId;
	}
}
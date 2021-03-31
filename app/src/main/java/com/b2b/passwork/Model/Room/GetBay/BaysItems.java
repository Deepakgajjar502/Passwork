package com.b2b.passwork.Model.Room.GetBay;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BaysItems {

	@SerializedName("bay_parent_name")
	private String bayParentName;

	@SerializedName("bay_parent_floor_id")
	private int bayParentFloorId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("inventory")
	private ArrayList<InventoryItem> inventory;

	@SerializedName("bays_parent_id")
	private int baysParentId;

	@SerializedName("floor_info")
	private List<FloorInfoItem> floorInfo;

	@SerializedName("bay_parent_description")
	private String bayParentDescription;

	public void setBayParentName(String bayParentName){
		this.bayParentName = bayParentName;
	}

	public String getBayParentName(){
		return bayParentName;
	}

	public void setBayParentFloorId(int bayParentFloorId){
		this.bayParentFloorId = bayParentFloorId;
	}

	public int getBayParentFloorId(){
		return bayParentFloorId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setInventory(ArrayList<InventoryItem> inventory){
		this.inventory = inventory;
	}

	public ArrayList<InventoryItem> getInventory(){
		return inventory;
	}

	public void setBaysParentId(int baysParentId){
		this.baysParentId = baysParentId;
	}

	public int getBaysParentId(){
		return baysParentId;
	}

	public void setFloorInfo(List<FloorInfoItem> floorInfo){
		this.floorInfo = floorInfo;
	}

	public List<FloorInfoItem> getFloorInfo(){
		return floorInfo;
	}

	public void setBayParentDescription(String bayParentDescription){
		this.bayParentDescription = bayParentDescription;
	}

	public String getBayParentDescription(){
		return bayParentDescription;
	}
}
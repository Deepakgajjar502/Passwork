package com.b2b.passwork.Model.Room.GetBay.NewGetBay;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NewInventoryItem implements Serializable {

	@SerializedName("seats_count")
	private int seatsCount;

	@SerializedName("bay_type")
	private String bayType;

	@SerializedName("bay_name")
	private String bayName;



	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("bay_id")
	private int bayId;

	@SerializedName("floor_id")
	private int floorId;

	@SerializedName("bays_parent_id")
	private int baysParentId;

	@SerializedName("bay_description")
	private String bayDescription;

	public void setSeatsCount(int seatsCount){
		this.seatsCount = seatsCount;
	}

	public int getSeatsCount(){
		return seatsCount;
	}

	public void setBayType(String bayType){
		this.bayType = bayType;
	}

	public String getBayType(){
		return bayType;
	}

	public void setBayName(String bayName){
		this.bayName = bayName;
	}

	public String getBayName(){
		return bayName;
	}



	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setBayId(int bayId){
		this.bayId = bayId;
	}

	public int getBayId(){
		return bayId;
	}

	public void setFloorId(int floorId){
		this.floorId = floorId;
	}

	public int getFloorId(){
		return floorId;
	}

	public void setBaysParentId(int baysParentId){
		this.baysParentId = baysParentId;
	}

	public int getBaysParentId(){
		return baysParentId;
	}

	public void setBayDescription(String bayDescription){
		this.bayDescription = bayDescription;
	}

	public String getBayDescription(){
		return bayDescription;
	}
}
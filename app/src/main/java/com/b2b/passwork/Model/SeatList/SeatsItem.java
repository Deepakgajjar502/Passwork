package com.b2b.passwork.Model.SeatList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeatsItem{

	@SerializedName("workspace_id")
	private int workspaceId;

	@SerializedName("seat_id")
	private int seatId;

	@SerializedName("available")
	private boolean available;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("floor_id")
	private int floorId;

	@SerializedName("bay_id")
	private int bayId;

	@SerializedName("bays_parent_id")
	private int baysParentId;

	@SerializedName("availablity")
	private List<AvailablityItem> availablity;

	public void setWorkspaceId(int workspaceId){
		this.workspaceId = workspaceId;
	}

	public int getWorkspaceId(){
		return workspaceId;
	}

	public void setSeatId(int seatId){
		this.seatId = seatId;
	}

	public int getSeatId(){
		return seatId;
	}

	public void setAvailable(boolean available){
		this.available = available;
	}

	public boolean isAvailable(){
		return available;
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

	public void setBayId(int bayId){
		this.bayId = bayId;
	}

	public int getBayId(){
		return bayId;
	}

	public void setBaysParentId(int baysParentId){
		this.baysParentId = baysParentId;
	}

	public int getBaysParentId(){
		return baysParentId;
	}

	public void setAvailablity(List<AvailablityItem> availablity){
		this.availablity = availablity;
	}

	public List<AvailablityItem> getAvailablity(){
		return availablity;
	}
}
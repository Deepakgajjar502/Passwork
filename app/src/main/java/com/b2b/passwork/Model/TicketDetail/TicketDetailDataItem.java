package com.b2b.passwork.Model.TicketDetail;

import com.google.gson.annotations.SerializedName;

public class TicketDetailDataItem {

	@SerializedName("image")
	private String image;

	@SerializedName("category_name")
	private String categoryName;

	@SerializedName("seat_id")
	private int seatId;

	@SerializedName("assign_to")
	private int assignTo;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("floor_description")
	private String floorDescription;

	@SerializedName("raised_by")
	private int raisedBy;

	@SerializedName("priority")
	private String priority;

	@SerializedName("workspace_id")
	private int workspaceId;

	@SerializedName("floor_name")
	private String floorName;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("sub_category_id")
	private int subCategoryId;

	@SerializedName("corporate_id")
	private int corporateId;

	@SerializedName("sub_category_name")
	private String subCategoryName;

	@SerializedName("floor_id")
	private int floorId;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setSeatId(int seatId){
		this.seatId = seatId;
	}

	public int getSeatId(){
		return seatId;
	}

	public void setAssignTo(int assignTo){
		this.assignTo = assignTo;
	}

	public int getAssignTo(){
		return assignTo;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setFloorDescription(String floorDescription){
		this.floorDescription = floorDescription;
	}

	public String getFloorDescription(){
		return floorDescription;
	}

	public void setRaisedBy(int raisedBy){
		this.raisedBy = raisedBy;
	}

	public int getRaisedBy(){
		return raisedBy;
	}

	public void setPriority(String priority){
		this.priority = priority;
	}

	public String getPriority(){
		return priority;
	}

	public void setWorkspaceId(int workspaceId){
		this.workspaceId = workspaceId;
	}

	public int getWorkspaceId(){
		return workspaceId;
	}

	public void setFloorName(String floorName){
		this.floorName = floorName;
	}

	public String getFloorName(){
		return floorName;
	}

	public void setCategoryId(int categoryId){
		this.categoryId = categoryId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setSubCategoryId(int subCategoryId){
		this.subCategoryId = subCategoryId;
	}

	public int getSubCategoryId(){
		return subCategoryId;
	}

	public void setCorporateId(int corporateId){
		this.corporateId = corporateId;
	}

	public int getCorporateId(){
		return corporateId;
	}

	public void setSubCategoryName(String subCategoryName){
		this.subCategoryName = subCategoryName;
	}

	public String getSubCategoryName(){
		return subCategoryName;
	}

	public void setFloorId(int floorId){
		this.floorId = floorId;
	}

	public int getFloorId(){
		return floorId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}
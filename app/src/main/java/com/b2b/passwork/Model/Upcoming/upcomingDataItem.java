package com.b2b.passwork.Model.Upcoming;

import com.google.gson.annotations.SerializedName;

public class upcomingDataItem {

	@SerializedName("corporate_customer_id")
	private int corporateCustomerId;

	@SerializedName("guest_info")
	private String guestInfo;

	@SerializedName("meeting_topic")
	private String meetingTopic;

	@SerializedName("start_datetime")
	private String startDatetime;

	@SerializedName("end_datetime")
	private String endDatetime;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("floor_description")
	private String floorDescription;

	@SerializedName("type")
	private String type;

	@SerializedName("seats")
	private String seats;

	@SerializedName("workspace_id")
	private int workspaceId;

	@SerializedName("floor_name")
	private String floorName;

	@SerializedName("booking_number")
	private String bookingNumber;

	@SerializedName("workspace_display_name")
	private String workspaceDisplayName;

	@SerializedName("is_checkedin")
	private boolean isCheckedin;

	@SerializedName("facility_id")
	private int facilityId;

	@SerializedName("guest_count")
	private String guestCount;

	@SerializedName("id")
	private String id;

	@SerializedName("workspace_name")
	private String workspaceName;

	@SerializedName("workspace_display_location")
	private String workspaceDisplayLocation;

	@SerializedName("status")
	private String status;

	public void setCorporateCustomerId(int corporateCustomerId){
		this.corporateCustomerId = corporateCustomerId;
	}

	public int getCorporateCustomerId(){
		return corporateCustomerId;
	}

	public void setGuestInfo(String guestInfo){
		this.guestInfo = guestInfo;
	}

	public String getGuestInfo(){
		return guestInfo;
	}

	public void setMeetingTopic(String meetingTopic){
		this.meetingTopic = meetingTopic;
	}

	public String getMeetingTopic(){
		return meetingTopic;
	}

	public void setStartDatetime(String startDatetime){
		this.startDatetime = startDatetime;
	}

	public String getStartDatetime(){
		return startDatetime;
	}

	public void setEndDatetime(String endDatetime){
		this.endDatetime = endDatetime;
	}

	public String getEndDatetime(){
		return endDatetime;
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

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setSeats(String seats){
		this.seats = seats;
	}

	public String getSeats(){
		return seats;
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

	public void setBookingNumber(String bookingNumber){
		this.bookingNumber = bookingNumber;
	}

	public String getBookingNumber(){
		return bookingNumber;
	}

	public void setWorkspaceDisplayName(String workspaceDisplayName){
		this.workspaceDisplayName = workspaceDisplayName;
	}

	public String getWorkspaceDisplayName(){
		return workspaceDisplayName;
	}

	public void setIsCheckedin(boolean isCheckedin){
		this.isCheckedin = isCheckedin;
	}

	public boolean isIsCheckedin(){
		return isCheckedin;
	}

	public void setFacilityId(int facilityId){
		this.facilityId = facilityId;
	}

	public int getFacilityId(){
		return facilityId;
	}

	public void setGuestCount(String guestCount){
		this.guestCount = guestCount;
	}

	public String getGuestCount(){
		return guestCount;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setWorkspaceName(String workspaceName){
		this.workspaceName = workspaceName;
	}

	public String getWorkspaceName(){
		return workspaceName;
	}

	public void setWorkspaceDisplayLocation(String workspaceDisplayLocation){
		this.workspaceDisplayLocation = workspaceDisplayLocation;
	}

	public String getWorkspaceDisplayLocation(){
		return workspaceDisplayLocation;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}
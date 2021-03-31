package com.b2b.passwork.Model.SeatList;

import com.google.gson.annotations.SerializedName;

public class ProfileItem{

	@SerializedName("corporate_customer_id")
	private int corporateCustomerId;

	@SerializedName("guest_info")
	private Object guestInfo;

	@SerializedName("role")
	private String role;

	@SerializedName("code")
	private String code;

	@SerializedName("start_datetime")
	private String startDatetime;

	@SerializedName("end_datetime")
	private String endDatetime;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("type")
	private String type;

	@SerializedName("seats")
	private String seats;

	@SerializedName("workspace_id")
	private int workspaceId;

	@SerializedName("booking_number")
	private String bookingNumber;

	@SerializedName("password")
	private String password;

	@SerializedName("is_checkedin")
	private boolean isCheckedin;

	@SerializedName("facility_id")
	private int facilityId;

	@SerializedName("id")
	private int id;

	@SerializedName("department")
	private Object department;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("meeting_topic")
	private String meetingTopic;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("corporate_id")
	private int corporateId;

	@SerializedName("guest_count")
	private String guestCount;

	@SerializedName("designation")
	private Object designation;

	@SerializedName("admin_login_allowed")
	private String adminLoginAllowed;

	@SerializedName("status")
	private String status;

	public void setCorporateCustomerId(int corporateCustomerId){
		this.corporateCustomerId = corporateCustomerId;
	}

	public int getCorporateCustomerId(){
		return corporateCustomerId;
	}

	public void setGuestInfo(Object guestInfo){
		this.guestInfo = guestInfo;
	}

	public Object getGuestInfo(){
		return guestInfo;
	}

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
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

	public void setBookingNumber(String bookingNumber){
		this.bookingNumber = bookingNumber;
	}

	public String getBookingNumber(){
		return bookingNumber;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDepartment(Object department){
		this.department = department;
	}

	public Object getDepartment(){
		return department;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setMeetingTopic(String meetingTopic){
		this.meetingTopic = meetingTopic;
	}

	public String getMeetingTopic(){
		return meetingTopic;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setCorporateId(int corporateId){
		this.corporateId = corporateId;
	}

	public int getCorporateId(){
		return corporateId;
	}

	public void setGuestCount(String guestCount){
		this.guestCount = guestCount;
	}

	public String getGuestCount(){
		return guestCount;
	}

	public void setDesignation(Object designation){
		this.designation = designation;
	}

	public Object getDesignation(){
		return designation;
	}

	public void setAdminLoginAllowed(String adminLoginAllowed){
		this.adminLoginAllowed = adminLoginAllowed;
	}

	public String getAdminLoginAllowed(){
		return adminLoginAllowed;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}
package com.b2b.passwork.Model.WorkspaceList;

import com.google.gson.annotations.SerializedName;

public class WorkspacesItem{

	@SerializedName("bd_address_line")
	private Object bdAddressLine;

	@SerializedName("metadata")
	private Metadata metadata;

	@SerializedName("total_area")
	private int totalArea;

	@SerializedName("distance")
	private Object distance;

	@SerializedName("workspace_account_id")
	private String workspaceAccountId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("description")
	private Object description;

	@SerializedName("toll_free_number")
	private Object tollFreeNumber;

	@SerializedName("contact_person_number")
	private Object contactPersonNumber;

	@SerializedName("min_balance")
	private int minBalance;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("about_us")
	private Object aboutUs;

	@SerializedName("qr_code")
	private String qrCode;

	@SerializedName("legacy_id")
	private int legacyId;

	@SerializedName("commission")
	private Object commission;

	@SerializedName("is_partner")
	private Object isPartner;

	@SerializedName("id")
	private String id;

	@SerializedName("contact_person_name")
	private Object contactPersonName;

	@SerializedName("total_seats")
	private Object totalSeats;

	@SerializedName("lat")
	private String lat;

	@SerializedName("place_id")
	private Object placeId;

	@SerializedName("display_location")
	private String displayLocation;

	@SerializedName("phone_2")
	private Object phone2;

	@SerializedName("phone_1")
	private Object phone1;

	@SerializedName("is_active")
	private boolean isActive;

	@SerializedName("lng")
	private String lng;

	@SerializedName("invoice_prefix")
	private String invoicePrefix;

	@SerializedName("display_name")
	private String displayName;

	@SerializedName("name")
	private String name;

	@SerializedName("comment")
	private Object comment;

	@SerializedName("registered_date")
	private String registeredDate;

	@SerializedName("status")
	private int status;

	public void setBdAddressLine(Object bdAddressLine){
		this.bdAddressLine = bdAddressLine;
	}

	public Object getBdAddressLine(){
		return bdAddressLine;
	}

	public void setMetadata(Metadata metadata){
		this.metadata = metadata;
	}

	public Metadata getMetadata(){
		return metadata;
	}

	public void setTotalArea(int totalArea){
		this.totalArea = totalArea;
	}

	public int getTotalArea(){
		return totalArea;
	}

	public void setDistance(Object distance){
		this.distance = distance;
	}

	public Object getDistance(){
		return distance;
	}

	public void setWorkspaceAccountId(String workspaceAccountId){
		this.workspaceAccountId = workspaceAccountId;
	}

	public String getWorkspaceAccountId(){
		return workspaceAccountId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setDescription(Object description){
		this.description = description;
	}

	public Object getDescription(){
		return description;
	}

	public void setTollFreeNumber(Object tollFreeNumber){
		this.tollFreeNumber = tollFreeNumber;
	}

	public Object getTollFreeNumber(){
		return tollFreeNumber;
	}

	public void setContactPersonNumber(Object contactPersonNumber){
		this.contactPersonNumber = contactPersonNumber;
	}

	public Object getContactPersonNumber(){
		return contactPersonNumber;
	}

	public void setMinBalance(int minBalance){
		this.minBalance = minBalance;
	}

	public int getMinBalance(){
		return minBalance;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setAboutUs(Object aboutUs){
		this.aboutUs = aboutUs;
	}

	public Object getAboutUs(){
		return aboutUs;
	}

	public void setQrCode(String qrCode){
		this.qrCode = qrCode;
	}

	public String getQrCode(){
		return qrCode;
	}

	public void setLegacyId(int legacyId){
		this.legacyId = legacyId;
	}

	public int getLegacyId(){
		return legacyId;
	}

	public void setCommission(Object commission){
		this.commission = commission;
	}

	public Object getCommission(){
		return commission;
	}

	public void setIsPartner(Object isPartner){
		this.isPartner = isPartner;
	}

	public Object getIsPartner(){
		return isPartner;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setContactPersonName(Object contactPersonName){
		this.contactPersonName = contactPersonName;
	}

	public Object getContactPersonName(){
		return contactPersonName;
	}

	public void setTotalSeats(Object totalSeats){
		this.totalSeats = totalSeats;
	}

	public Object getTotalSeats(){
		return totalSeats;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setPlaceId(Object placeId){
		this.placeId = placeId;
	}

	public Object getPlaceId(){
		return placeId;
	}

	public void setDisplayLocation(String displayLocation){
		this.displayLocation = displayLocation;
	}

	public String getDisplayLocation(){
		return displayLocation;
	}

	public void setPhone2(Object phone2){
		this.phone2 = phone2;
	}

	public Object getPhone2(){
		return phone2;
	}

	public void setPhone1(Object phone1){
		this.phone1 = phone1;
	}

	public Object getPhone1(){
		return phone1;
	}

	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}

	public boolean isIsActive(){
		return isActive;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setInvoicePrefix(String invoicePrefix){
		this.invoicePrefix = invoicePrefix;
	}

	public String getInvoicePrefix(){
		return invoicePrefix;
	}

	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName(){
		return displayName;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setComment(Object comment){
		this.comment = comment;
	}

	public Object getComment(){
		return comment;
	}

	public void setRegisteredDate(String registeredDate){
		this.registeredDate = registeredDate;
	}

	public String getRegisteredDate(){
		return registeredDate;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}
package com.b2b.passwork.Model.Category;

import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    @SerializedName("corporate_id")
    private String corporateId;

    @SerializedName("name")
    private String name;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private String id;

    public void setCorporateId(String corporateId){
        this.corporateId = corporateId;
    }

    public String getCorporateId(){
        return corporateId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}

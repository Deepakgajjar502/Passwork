package com.b2b.passwork.Model;

public class CategoryModel {

    String categoryId;
    String CategoryTitle;
    boolean isChecked = false;


    public CategoryModel(String categoryId, String categoryTitle) {
        this.categoryId = categoryId;
        CategoryTitle = categoryTitle;

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }
}

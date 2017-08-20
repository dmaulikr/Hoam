package com.solvo.hoam.data.db.model;

public class AdModel {

    private String id;
    private String title;
    private String text;
    private long price;
    private String phone;
    private int views;
    private String authorId;
    private String authorName;
    private String categoryId;
    private boolean isPremium;
    private String cityId;
    private String createdAt;
    private String updatedAt;
    private boolean isFree;

    public AdModel() {
    }

    public AdModel(String id, String title, String text, long price, String phone, int views,
                   String authorId, String authorName, String categoryId, boolean isPremium,
                   String cityId, String createdAt, String updatedAt, boolean isFree) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.price = price;
        this.phone = phone;
        this.views = views;
        this.authorId = authorId;
        this.authorName = authorName;
        this.categoryId = categoryId;
        this.isPremium = isPremium;
        this.cityId = cityId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isFree = isFree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}

package com.solvo.hoam.domain.model;

import com.solvo.hoam.data.network.response.Image;

import java.util.List;

public class AdEntity {

    private String id;
    private String title;
    private String text;
    private long price;
    private String phone;
    private int views;
    private String authorId;
    private String authorName;
    private String categoryId;
    private String categoryName;
    private boolean isPremium;
    private String locationId;
    private String locationName;
    private String createdAt;
    private String updatedAt;
    private List<Image> imageList;
    private boolean isFree;
    private boolean isFavorite;

    public AdEntity(String id,
                    String title,
                    String text,
                    long price,
                    String phone,
                    int views,
                    String authorId,
                    String authorName,
                    String categoryId,
                    String categoryName,
                    boolean isPremium,
                    String locationId,
                    String locationName,
                    String createdAt,
                    String updatedAt,
                    List<Image> imageList,
                    boolean isFree,
                    boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.price = price;
        this.phone = phone;
        this.views = views;
        this.authorId = authorId;
        this.authorName = authorName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.locationId = locationId;
        this.locationName = locationName;
        this.isPremium = isPremium;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageList = imageList;
        this.isFree = isFree;
        this.isFavorite = isFavorite;
    }

    public AdEntity(String id,
                    String title,
                    String text,
                    long price,
                    String phone,
                    int views,
                    String authorId,
                    String authorName,
                    String categoryId,
                    String locationId,
                    boolean isPremium,
                    String createdAt,
                    String updatedAt,
                    List<Image> imageList,
                    boolean isFree,
                    boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.price = price;
        this.phone = phone;
        this.views = views;
        this.authorId = authorId;
        this.authorName = authorName;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.isPremium = isPremium;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageList = imageList;
        this.isFree = isFree;
        this.isFavorite = isFavorite;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}

package com.nikhilchakravartula.stocksearch.models;


import com.google.gson.annotations.SerializedName;

public class NewsModel {

    @SerializedName("category")
    private String category;
    @SerializedName("datetime")
    private Integer datetime;
    @SerializedName("headline")
    private String headline;
    @SerializedName("id")
    private Integer id;
    @SerializedName("image")
    private String image;
    @SerializedName("related")
    private String related;
    @SerializedName("source")
    private String source;
    @SerializedName("summary")
    private String summary;
    @SerializedName("url")
    private String url;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("content")
    private String content;
    @SerializedName("publishedAt")
    private String publishedAt;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDatetime() {
        return datetime;
    }

    public void setDatetime(Integer datetime) {
        this.datetime = datetime;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

}
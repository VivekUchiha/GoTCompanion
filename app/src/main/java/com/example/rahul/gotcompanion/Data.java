package com.example.rahul.gotcompanion;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("dateOfBirth")
    @Expose
    private Integer dateOfBirth=0;
    @SerializedName("imageLink")
    @Expose
    private String imageLink;
    @SerializedName("male")
    @Expose
    private Boolean male;
    @SerializedName("spouse")
    @Expose
    private String spouse;
    @SerializedName("house")
    @Expose
    private String house;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("books")
    @Expose
    private List<String> books = null;
    @SerializedName("titles")
    @Expose
    private List<String> titles = null;
    @SerializedName("pageRank")
    @Expose
    private Double pageRank = 0.0;
    @SerializedName("culture")
    @Expose
    private String culture;

    public Data(String id, Integer dateOfBirth, String imageLink, Boolean male, String spouse, String house, String slug, String name, List<String> books, List<String> titles, Double pageRank, String culture) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.imageLink = imageLink;
        this.male = male;
        this.spouse = spouse;
        this.house = house;
        this.slug = slug;
        this.name = name;
        this.books = books;
        this.titles = titles;
        this.pageRank = pageRank;
        this.culture = culture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Integer dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public Double getPageRank() {
        return pageRank;
    }

    public void setPageRank(Double pageRank) {
        this.pageRank = pageRank;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }
}
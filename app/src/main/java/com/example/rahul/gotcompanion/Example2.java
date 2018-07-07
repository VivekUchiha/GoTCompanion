package com.example.rahul.gotcompanion;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example2 {

    @SerializedName("name")
    @Expose
    private String name;

    public Example2(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
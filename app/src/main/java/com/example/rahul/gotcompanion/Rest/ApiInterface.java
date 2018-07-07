package com.example.rahul.gotcompanion.Rest;

import com.example.rahul.gotcompanion.Data;
import com.example.rahul.gotcompanion.Example;
import com.example.rahul.gotcompanion.Example2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //To get popular tracks
    @GET("characters/{id}")
    Call<Example> getCharacter(@Path("id") String id);

    @GET("characters/")
    Call<List<Data>> getAll();

}

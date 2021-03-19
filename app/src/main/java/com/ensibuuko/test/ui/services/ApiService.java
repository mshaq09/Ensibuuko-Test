package com.ensibuuko.test.ui.services;

import com.ensibuuko.test.ui.models.Album;
import com.ensibuuko.test.ui.models.Posts;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {


    @GET("posts")
    Call<List<Posts>> getPosts();

    @GET("albums")
    Call<List<Album>> getAlbums();


}

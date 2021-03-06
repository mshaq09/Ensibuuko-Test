package com.ensibuuko.test.ui.services;

import com.ensibuuko.test.ui.models.Album;
import com.ensibuuko.test.ui.models.Comments;
import com.ensibuuko.test.ui.models.Photos;
import com.ensibuuko.test.ui.models.Posts;
import com.ensibuuko.test.ui.models.User;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    @GET("posts")
    Call<List<Posts>> getPosts();

    @POST("posts")
    Call<Posts> addPost(@Body Posts post);

    @GET("albums")
    Call<List<Album>> getAlbums();

    @GET("photos")
    Call<List<Photos>> getPhotos();

    @GET("users")
    Call<List<User>> getUsers();

    @GET("comments")
    Call<List<Comments>> getComments();



}

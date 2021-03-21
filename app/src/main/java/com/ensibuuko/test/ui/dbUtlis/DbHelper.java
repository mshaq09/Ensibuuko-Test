package com.ensibuuko.test.ui.dbUtlis;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ensibuuko.test.ui.models.Album;
import com.ensibuuko.test.ui.models.Comments;
import com.ensibuuko.test.ui.models.Photos;
import com.ensibuuko.test.ui.models.Posts;
import com.ensibuuko.test.ui.models.User;
import com.ensibuuko.test.ui.services.ApiClient;
import com.ensibuuko.test.ui.services.ApiService;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DbHelper {

    Realm realm;
    ApiService apiService;

    public  DbHelper(boolean start){
        realm = Realm.getDefaultInstance();

        if(start){
            apiService = ApiClient.buildAPIService();
            getPosts();
            getAlbums();
            getPhotos();
            getAllUsers();
            getComments();
        }

    }



    public RealmResults<Posts> getAllPosts(){


        return realm.where(Posts.class).sort("date", Sort.ASCENDING).findAllAsync();

    }
    public void insertPosts(List<Posts> posts){
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {
                                              realm.insertOrUpdate(posts);
                                          }
                                      });

    }

    public void insertAlbums(List<Album> albums){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(albums);
            }
        });

    }

    public void insertUsers(List<User> users){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(users);
            }
        });

    }

    public void insertPhotos(List<Photos> photos){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(photos);
            }
        });

    }

    public void insertComments(List<Comments> comments){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(comments);
            }
        });

    }

    public RealmResults<Posts> getUserPosts(int user_id){

        return realm.where(Posts.class).equalTo("userId",user_id).findAllAsync();

    }

    public RealmResults<Album> getLocalAlbums(){

        return realm.where(Album.class).findAllAsync();

    }

    public RealmResults<Photos> getLocalPhotos(){

        return realm.where(Photos.class).findAllAsync();

    }

    public RealmResults<User> getUsers(){

        return realm.where(User.class).findAllAsync();

    }

    public RealmResults<Photos> getAlbumPhotos(int id){

        return realm.where(Photos.class).equalTo("albumId",id).sort("id").findAllAsync();

    }

    public RealmResults<Comments> getPostComments(int postId){

        return realm.where(Comments.class).equalTo("postId",postId).findAllAsync();

    }

    public void getPosts(){
        apiService.getPosts().enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call,
                                   Response<List<Posts>> response) {
                if (response.isSuccessful()){
                    insertPosts(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
            }
        });


    }

    public void getAlbums(){
        apiService.getAlbums().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call,
                                   Response<List<Album>> response) {
                if (response.isSuccessful()){
                    insertAlbums(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
            }
        });


    }
    public void getPhotos(){
        apiService.getPhotos().enqueue(new Callback<List<Photos>>() {
            @Override
            public void onResponse(Call<List<Photos>> call,
                                   Response<List<Photos>> response) {
                if (response.isSuccessful()){
                    insertPhotos(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Photos>> call, Throwable t) {
            }
        });


    }

    public void getAllUsers(){
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call,
                                   Response<List<User>> response) {
                if (response.isSuccessful()){
                    insertUsers(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });


    }

    public void getComments(){
        apiService.getComments().enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call,
                                   Response<List<Comments>> response) {
                if (response.isSuccessful()){
                    insertComments(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
            }
        });


    }

}

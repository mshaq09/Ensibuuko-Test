package com.ensibuuko.test.ui.dbUtlis;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ensibuuko.test.ui.models.Album;
import com.ensibuuko.test.ui.models.Posts;
import com.ensibuuko.test.ui.services.ApiClient;
import com.ensibuuko.test.ui.services.ApiService;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DbHelper {

    Realm realm;
    ApiService apiService;

    public  DbHelper(){
        realm = Realm.getDefaultInstance();
        apiService = ApiClient.buildAPIService();
        getPosts();
        getAlbums();
    }


    public RealmResults<Posts> getAllPosts(){


        return realm.where(Posts.class).findAllAsync();

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

    public RealmResults<Posts> getUserPosts(int user_id){

        return realm.where(Posts.class).equalTo("userId",user_id).findAllAsync();

    }

    public RealmResults<Album> getLocalAlbums(){

        return realm.where(Album.class).findAllAsync();

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
}

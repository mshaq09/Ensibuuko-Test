package com.ensibuuko.test.ui.main;

import androidx.lifecycle.ViewModel;

import com.ensibuuko.test.ui.dbUtlis.DbHelper;
import com.ensibuuko.test.ui.dbUtlis.LiveRealmResults;
import com.ensibuuko.test.ui.models.Album;
import com.ensibuuko.test.ui.models.Photos;
import com.ensibuuko.test.ui.models.Posts;

public class RealmViewModel extends ViewModel {

    private DbHelper repository ;

    public LiveRealmResults<Posts> getAllPosts() {
        return allPosts;
    }

    private LiveRealmResults<Posts> allPosts;

    public LiveRealmResults<Album> getAllAlbums() {
        return allAlbums;
    }

    private LiveRealmResults<Album> allAlbums;



    public RealmViewModel() {

        repository = new DbHelper();
        allPosts = new LiveRealmResults<>(repository.getAllPosts());
        allAlbums = new LiveRealmResults<>(repository.getLocalAlbums());

    }

    public LiveRealmResults<Posts> getMyPosts(int user){
        return new LiveRealmResults<>(repository.getUserPosts(user));
    }

    public LiveRealmResults<Photos> getAlbumPhotos(int id){
        return new LiveRealmResults<>(repository.getAlbumPhotos(id));
    }


    @Override
    protected void onCleared() {
        // realm.close(); // Realm is bound to the lifecycle of the ViewModel, and is destroyed when no longer needed.
        super.onCleared();
    }
}

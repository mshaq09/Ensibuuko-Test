package com.ensibuuko.test.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ensibuuko.test.databinding.ActivityAlbumBinding;
import com.ensibuuko.test.ui.adapters.PhotoAdapter;
import com.ensibuuko.test.ui.dbUtlis.MyViewModelFactory;
import com.ensibuuko.test.ui.models.Album;
import com.ensibuuko.test.ui.models.Photos;
import com.ensibuuko.test.ui.services.ClickListener;
import com.ensibuuko.test.viewmodels.RealmViewModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class AlbumActivity extends AppCompatActivity {

    private ActivityAlbumBinding binding;

    private RealmViewModel realmViewModel;

    PhotoAdapter photoAdapter;

    List<Photos> photosList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAlbumBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        realmViewModel = new ViewModelProvider(this,new MyViewModelFactory(false)).get(RealmViewModel.class);
        binding.listPhotos.setLayoutManager(new GridLayoutManager(this, 2));

        init(getIntent().getIntExtra("id",0));


    }

    public void init(int id){

        Realm realm = Realm.getDefaultInstance();
        Album album = realm.where(Album.class).equalTo("id",id).findFirst();

        if (album != null) {
            binding.title.setText(album.getTitle());
            getAlbumPhotos(album.getId());


        }
    }

    public void getAlbumPhotos(int id) {

        realmViewModel.getAlbumPhotos(id).observe(this, photos -> {

            photosList.clear();
            photosList.addAll(photos);

            photoAdapter = new PhotoAdapter(AlbumActivity.this,photosList);
            photoAdapter.setOnItemClickListener(new ClickListener() {
                @Override
                public void onItemClick(int position, View v) {

                    PhotoBottomSheet photoBottomSheet = new PhotoBottomSheet(photosList.get(position).getUrl());
                    photoBottomSheet.show(getSupportFragmentManager(), photoBottomSheet.getTag());

                }
            });
            binding.listPhotos.setAdapter(photoAdapter);

        });
    }
}

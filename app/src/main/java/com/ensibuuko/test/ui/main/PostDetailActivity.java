package com.ensibuuko.test.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.ensibuuko.test.databinding.ActivityPostDetailsBinding;
import com.ensibuuko.test.ui.models.Posts;

import io.realm.Realm;

public class PostDetailActivity extends AppCompatActivity {

    ActivityPostDetailsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init(getIntent().getIntExtra("id",0));
    }

    public void init(int id){
        Realm realm = Realm.getDefaultInstance();

        Posts post = realm.where(Posts.class).equalTo("id",id).findFirst();
        if(post != null){
            binding.body.setText(post.getBody());
            binding.animToolbar.setTitle(post.getTitle());
        }

    }
}

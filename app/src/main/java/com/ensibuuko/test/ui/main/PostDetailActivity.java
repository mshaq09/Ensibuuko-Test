package com.ensibuuko.test.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensibuuko.test.R;
import com.ensibuuko.test.databinding.ActivityPostDetailsBinding;
import com.ensibuuko.test.ui.adapters.CommentAdapter;
import com.ensibuuko.test.ui.adapters.UserAdapter;
import com.ensibuuko.test.ui.models.Comments;
import com.ensibuuko.test.ui.models.Posts;
import com.ensibuuko.test.ui.models.User;
import com.ensibuuko.test.ui.services.ClickListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class PostDetailActivity extends AppCompatActivity {

    ActivityPostDetailsBinding binding;
    private RealmViewModel realmViewModel;
    List<Comments> comments = new ArrayList<>();
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.commentList.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        binding.commentList.addItemDecoration(itemDecorator);

        realmViewModel = new ViewModelProvider(this).get(RealmViewModel.class);

        init(getIntent().getIntExtra("id",0));
    }

    public void init(int id){
        Realm realm = Realm.getDefaultInstance();

        Posts post = realm.where(Posts.class).equalTo("id",id).findFirst();
        if(post != null){
            binding.body.setText(post.getBody());
            binding.animToolbar.setTitle(post.getTitle());
            setUpComments(id);
        }

    }

    private void setUpComments(int id){

        realmViewModel.getPostComments(id).observe(this, commentsList -> {

            comments.clear();
            comments.addAll(commentsList);


            commentAdapter = new CommentAdapter(comments);

            binding.commentList.setAdapter(commentAdapter);
        });

    }
}

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
import com.ensibuuko.test.ui.dbUtlis.MyViewModelFactory;
import com.ensibuuko.test.ui.models.Comments;
import com.ensibuuko.test.ui.models.Posts;
import com.ensibuuko.test.viewmodels.RealmViewModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class PostDetailActivity extends AppCompatActivity {

    ActivityPostDetailsBinding binding;
    private RealmViewModel realmViewModel;
    List<Comments> comments = new ArrayList<>();
    CommentAdapter commentAdapter;
    int post_id;

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

        realmViewModel = new ViewModelProvider(this,new MyViewModelFactory(false)).get(RealmViewModel.class);

        init(getIntent().getIntExtra("id",0));
    }

    public void init(int id){
        post_id = id;
        Realm realm = Realm.getDefaultInstance();

        Posts post = realm.where(Posts.class).equalTo("id",id).findFirst();
        if(post != null){
            binding.body.setText(post.getBody());
            binding.animToolbar.setTitle(post.getTitle());
            setUpComments(id);
        }

        binding.sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment(binding.commentText.getText().toString());
            }
        });

    }

    private void setUpComments(int id){

        realmViewModel.getPostComments(id).observe(this, commentsList -> {

            comments.clear();
            comments.addAll(commentsList);

            if(comments.size() > 0){
                binding.empty.setVisibility(View.GONE);
                commentAdapter = new CommentAdapter(comments);
                binding.commentList.setAdapter(commentAdapter);
            }else{
                binding.commentList.setVisibility(View.GONE);
                binding.empty.setVisibility(View.VISIBLE);
            }

        });

    }

    public void sendComment(String text){
        Realm realm = Realm.getDefaultInstance();
        Comments comment = new Comments();
        comment.setBody(text);
        comment.setPostId(post_id);
        String name = Utils.getStringKey(Utils.NAME_KEY,this);
        if(name.isEmpty()){
            comment.setName("Anonymous");
        }else{
            comment.setName(name);
        }


        // Get the current max id in the users table
        // Get the current max id in the users table
        Number maxId = realm.where(Comments.class).max("id");
        // If there are no rows, currentId is null, so the next id must be 1
        // If currentId is not null, increment it by 1
        // If there are no rows, currentId is null, so the next id must be 1
        // If currentId is not null, increment it by 1
        int nextId = 1;
        if (maxId != null){
           nextId =  maxId.intValue() + 1;
        }
        comment.setId(nextId);

        realmViewModel.addComment(comment);

        setUpComments(post_id);
        binding.commentText.setText("");
        Utils.hideKeyboard(PostDetailActivity.this);
    }

}

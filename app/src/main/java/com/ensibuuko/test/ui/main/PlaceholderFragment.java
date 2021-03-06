package com.ensibuuko.test.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensibuuko.test.R;
import com.ensibuuko.test.databinding.FragmentMainBinding;
import com.ensibuuko.test.ui.adapters.AlbumAdapter;
import com.ensibuuko.test.ui.adapters.PostAdapter;
import com.ensibuuko.test.ui.adapters.UserAdapter;
import com.ensibuuko.test.ui.dbUtlis.MyViewModelFactory;
import com.ensibuuko.test.ui.dbUtlis.SwipeToDeleteCallback;
import com.ensibuuko.test.ui.models.Album;
import com.ensibuuko.test.ui.models.Posts;
import com.ensibuuko.test.ui.models.User;
import com.ensibuuko.test.ui.services.ClickListener;
import com.ensibuuko.test.viewmodels.PageViewModel;
import com.ensibuuko.test.viewmodels.RealmViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_USER_ID = "user_id";
    private FragmentMainBinding binding;

    private PageViewModel pageViewModel;

    private RealmViewModel realmViewModel;

    List<Posts> postsList = new ArrayList<>();

    List<Album> albumList = new ArrayList<>();

    List<User> userList = new ArrayList<>();

    PostAdapter postAdapter;
    AlbumAdapter albumAdapter;
    UserAdapter userAdapter;
    int user_id;
    boolean savedKey = false;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static PlaceholderFragment newInstance(int index,int user_id) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putInt(ARG_USER_ID, user_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);

        savedKey = Utils.getKey(Utils.KEY_FIRSTTIME,requireActivity());

        realmViewModel = new ViewModelProvider(this,new MyViewModelFactory(savedKey)).get(RealmViewModel.class);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            user_id = getArguments().getInt(ARG_USER_ID,0);
        }
        pageViewModel.setIndex(index);

        if(savedKey){
            Utils.save(Utils.KEY_FIRSTTIME,false,requireActivity());
        }


    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayoutManager layoutManager= new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        binding.listPosts.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.divider));
        binding.listPosts.addItemDecoration(itemDecorator);


        pageViewModel.getText().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                switch (s){
                    case 2:
                        getUsers();
                        break;
                    case 3:
                        getAllAlbums();
                        break;
                    case 4:
                        getMyPosts(user_id);
                        break;
                    default:
                        getResults();
                        break;
                }

            }
        });




        return root;
    }

    public void getResults(){
        binding.progressBar.setVisibility(View.VISIBLE);
        realmViewModel.getAllPosts().observe(this, posts -> {

            postsList.clear();
            postsList.addAll(posts);



            postAdapter = new PostAdapter(requireActivity(),postsList);
            postAdapter.setOnItemClickListener(new ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Intent intent = new Intent(requireActivity(),PostDetailActivity.class);
                    intent.putExtra("id",postsList.get(position).getId());
                    startActivity(intent);
                }
            });
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(postAdapter));
            itemTouchHelper.attachToRecyclerView(binding.listPosts);
            binding.listPosts.setAdapter(postAdapter);

            binding.progressBar.setVisibility(View.GONE);
        });


    }

    public void getMyPosts(int user_id){

        if(user_id > 0){

            realmViewModel.getMyPosts(user_id).observe(this, posts -> {

                postsList.clear();
                postsList.addAll(posts);


                postAdapter = new PostAdapter(requireActivity(),postsList);
                postAdapter.setOnItemClickListener(new ClickListener() {
                                                       @Override
                                                       public void onItemClick(int position, View v) {
                                                           Intent intent = new Intent(requireActivity(),PostDetailActivity.class);
                                                           intent.putExtra("id",postsList.get(position).getId());
                                                           startActivity(intent);
                                                       }
                                                   });
                        binding.listPosts.setAdapter(postAdapter);
            });

        }




    }

    public void getAllAlbums(){
        binding.progressBar.setVisibility(View.VISIBLE);
        realmViewModel.getAllAlbums().observe(this, albums -> {

            albumList.clear();
            albumList.addAll(albums);

            albumAdapter = new AlbumAdapter(requireActivity(),albumList);
            albumAdapter.setOnItemClickListener(new ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Intent intent = new Intent(requireActivity(),AlbumActivity.class);
                    intent.putExtra("id",albumList.get(position).getId());
                    startActivity(intent);
                }
            });
            binding.listPosts.setAdapter(albumAdapter);
            binding.progressBar.setVisibility(View.GONE);

        });


    }

    public void getUsers(){

        realmViewModel.getAllUsers().observe(this, posts -> {

            userList.clear();
            userList.addAll(posts);


            userAdapter = new UserAdapter(userList);
            userAdapter.setOnItemClickListener(new ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    UserBottomSheet userBottomSheet = new UserBottomSheet(userList.get(position));
                    userBottomSheet.show(requireActivity().getSupportFragmentManager(), userBottomSheet.getTag());

                }
            });
            binding.listPosts.setAdapter(userAdapter);
        });


    }
}
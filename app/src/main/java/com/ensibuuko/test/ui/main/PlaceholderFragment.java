package com.ensibuuko.test.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensibuuko.test.R;
import com.ensibuuko.test.databinding.ActivityMainBinding;
import com.ensibuuko.test.databinding.FragmentMainBinding;
import com.ensibuuko.test.ui.adapters.AlbumAdapter;
import com.ensibuuko.test.ui.adapters.PostAdapter;
import com.ensibuuko.test.ui.models.Album;
import com.ensibuuko.test.ui.models.Posts;
import com.ensibuuko.test.ui.services.ClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentMainBinding binding;

    private PageViewModel pageViewModel;

    private RealmViewModel realmViewModel;

    List<Posts> postsList = new ArrayList<>();

    List<Album> albumList = new ArrayList<>();

    PostAdapter postAdapter;
    AlbumAdapter albumAdapter;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        realmViewModel = new ViewModelProvider(this).get(RealmViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);


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
                        getMyPosts();
                        break;
                    case 3:
                        getAllAlbums();
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

        realmViewModel.getAllPosts().observe(this, posts -> {

            postsList.clear();
            postsList.addAll(posts);

            Log.e("here",""+postsList.size());

            postAdapter = new PostAdapter(requireActivity(),postsList);
            binding.listPosts.setAdapter(postAdapter);
        });


    }

    public void getMyPosts(){

        realmViewModel.getMyPosts(1).observe(this, posts -> {

            postsList.clear();
            postsList.addAll(posts);

            Log.e("here",""+postsList.size());

            postAdapter = new PostAdapter(requireActivity(),postsList);
            binding.listPosts.setAdapter(postAdapter);
        });


    }

    public void getAllAlbums(){

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

        });


    }
}
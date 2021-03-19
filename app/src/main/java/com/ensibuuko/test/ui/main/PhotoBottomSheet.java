package com.ensibuuko.test.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ensibuuko.test.R;
import com.ensibuuko.test.databinding.PhotoBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

public class PhotoBottomSheet extends BottomSheetDialogFragment {

    PhotoBottomSheetBinding binding;
    String url;


    public PhotoBottomSheet(String url){
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PhotoBottomSheetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Picasso.get().load(url).placeholder(R.drawable.ic_placeholder).into(binding.fullImage);


        return root;
    }
}

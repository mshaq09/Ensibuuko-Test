package com.ensibuuko.test.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ensibuuko.test.databinding.UserBottomSheetBinding;
import com.ensibuuko.test.ui.adapters.UserPageAdapter;
import com.ensibuuko.test.ui.models.User;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UserBottomSheet  extends BottomSheetDialogFragment {

    User user;
    UserBottomSheetBinding userBottomSheetBinding;

    public UserBottomSheet(User userl){
        this.user = userl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userBottomSheetBinding = UserBottomSheetBinding.inflate(inflater, container, false);
        View root = userBottomSheetBinding.getRoot();

        UserPageAdapter userPageAdapter = new UserPageAdapter(requireActivity(), getChildFragmentManager(),user);

        userBottomSheetBinding.viewPager.setAdapter(userPageAdapter);
        userBottomSheetBinding.tabs.setupWithViewPager(userBottomSheetBinding.viewPager);

        return root;
    }



}

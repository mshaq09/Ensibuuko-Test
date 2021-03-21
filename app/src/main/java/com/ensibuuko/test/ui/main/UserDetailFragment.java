package com.ensibuuko.test.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.ensibuuko.test.databinding.UserDetailFragmentBinding;
import com.ensibuuko.test.ui.models.User;

import io.realm.Realm;

public class UserDetailFragment extends Fragment {

    UserDetailFragmentBinding userBinding;
    User user;
    private static final String ARG_USER_ID = "user_id";

    public static UserDetailFragment newInstance(int user) {
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_USER_ID, user);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        userBinding = UserDetailFragmentBinding.inflate(inflater, container, false);
        View root = userBinding.getRoot();

        init();

        return root;
    }

    public void init(){

        int user_id = 0;
        if (getArguments() != null) {
            user_id = getArguments().getInt(ARG_USER_ID,0);
        }

        Realm realm = Realm.getDefaultInstance();

        user = realm.where(User.class).equalTo("id",user_id).findFirst();

        if(user != null){

            userBinding.name.setText(user.getName());
            userBinding.email.setText(user.getEmail());
            userBinding.phone.setText(user.getPhone());
            userBinding.website.setText(user.getWebsite());
            userBinding.company.setText(user.getCompany().getName());
            userBinding.catchphrase.setText(user.getCompany().getCatchPhrase());
            String address = String.format("%s %s,%s", user.getAddress().getSuite(), user.getAddress().getStreet(), user.getAddress().getCity());
            userBinding.address.setText(address);

        }


    }
}

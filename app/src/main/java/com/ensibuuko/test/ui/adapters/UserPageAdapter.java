package com.ensibuuko.test.ui.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ensibuuko.test.R;
import com.ensibuuko.test.ui.main.PlaceholderFragment;
import com.ensibuuko.test.ui.main.UserDetailFragment;
import com.ensibuuko.test.ui.models.User;

public class UserPageAdapter extends FragmentPagerAdapter {


    @StringRes
    private static final int[] USER_TITLES = new int[]{R.string.tab_user, R.string.tab_user_posts};
    private final Context mContext;
    private User user;


    public UserPageAdapter(Context context, @NonNull FragmentManager fm, User muser) {
        super(fm);
        mContext = context;
        user = muser;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Log.e("here id",""+position);
        if(position == 0){
            return UserDetailFragment.newInstance(user.getId());
        }
        return PlaceholderFragment.newInstance(4,user.getId());
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(USER_TITLES[position]);
    }


    @Override
    public int getCount() {
        return 2;
    }
}

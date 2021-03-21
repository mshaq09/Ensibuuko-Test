package com.ensibuuko.test.ui.dbUtlis;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ensibuuko.test.ui.main.RealmViewModel;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private boolean mParam;


    public MyViewModelFactory(boolean param) {
        mParam = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RealmViewModel(mParam);
    }
}

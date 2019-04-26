package com.wanandroid.java.modules.main.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wanandroid.java.R;

/**
 * Created by YZH on 2019/4/24.
 * From the BaoBao project
 *
 * @author YZH
 */
public class HomePageFragment extends Fragment{

    public static final String TAG=HomePageFragment.class.getSimpleName();

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_homepage, container, false);
        }
        //FragmentLoadStyle
        return mRootView;
    }
}

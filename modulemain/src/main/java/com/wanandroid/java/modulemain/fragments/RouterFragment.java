package com.wanandroid.java.modulemain.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wanandroid.java.modulemain.R;


/**
 * Created by YZH on 2019/4/24.
 * From the BaoBao project
 *
 * @author YZH
 */
public class RouterFragment extends Fragment {

    public static final String TAG=RouterFragment.class.getSimpleName();

    private View mRootView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_router, container, false);
        }
        //FragmentLoadStyle
        return mRootView;
    }
}

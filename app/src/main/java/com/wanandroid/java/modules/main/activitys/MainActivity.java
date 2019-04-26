package com.wanandroid.java.modules.main.activitys;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wanandroid.java.R;
import com.wanandroid.java.base.BaseActivity;
import com.wanandroid.java.base.BaseBindActivity;
import com.wanandroid.java.base.simple.core.BaseMVPActivity;
import com.wanandroid.java.modules.main.fragments.HomePageFragment;
import com.wanandroid.java.modules.main.fragments.KnowledgeFragment;
import com.wanandroid.java.modules.main.fragments.OfficialFragment;
import com.wanandroid.java.modules.main.fragments.ProjectFragment;
import com.wanandroid.java.modules.main.fragments.RouterFragment;

import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;


/**
 * @author yangzhihang
 */
public class MainActivity extends BaseMVPActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.main_radio_group)
    RadioGroup mRgMain;

    private Fragment mShowFragment;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

//        mRgMain=(RadioGroup)findViewById(R.id.main_radio_group);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mShowFragment = new HomePageFragment();
        transaction.add(R.id.main_container, mShowFragment, HomePageFragment.TAG);
        transaction.commit();

        mRgMain.setOnCheckedChangeListener(this);
    }

    @Override
    protected void regEvent(boolean event) {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void showLoadingEmpty() {

    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//
////        mRgMain=(RadioGroup)findViewById(R.id.main_radio_group);
//
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        mShowFragment = new HomePageFragment();
//        transaction.add(R.id.main_container, mShowFragment, HomePageFragment.TAG);
//        transaction.commit();
//
//        mRgMain.setOnCheckedChangeListener(this);
//    }

    //    @Override
//    protected int getContentViewId(Bundle savedInstanceState) {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    protected void initView() {
//        butterknife.ButterKnife.bind(this);
//
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        mShowFragment = new HomePageFragment();
//        transaction.add(R.id.main_container, mShowFragment, HomePageFragment.TAG);
//        transaction.commit();
//    }
//
//    @Override
//    protected void regEvent(boolean event) {
//        mRgMain.setOnCheckedChangeListener(event ? this : null);
//    }
//
//    @Override
//    protected int hasTitle() {
//        return R.string.app_name;
//    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_main_homepage:
                switchPages(HomePageFragment.TAG, HomePageFragment.class);
                break;
            case R.id.rb_main_knowledge:
                switchPages(KnowledgeFragment.TAG, KnowledgeFragment.class);
                break;
            case R.id.rb_main_router:
                switchPages(RouterFragment.TAG, RouterFragment.class);
                break;
            case R.id.rb_main_official:
                switchPages(OfficialFragment.TAG, OfficialFragment.class);
                break;
            case R.id.rb_main_project:
                switchPages(ProjectFragment.TAG, ProjectFragment.class);
                break;
            default:
                break;
        }
    }

    /**
     * 切换首页tab fragment
     *
     * @param tag
     * @param cls
     */
    public void switchPages(String tag, Class<? extends Fragment> cls) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(mShowFragment);
        mShowFragment = fm.findFragmentByTag(tag);
        if (mShowFragment != null) {
            transaction.show(mShowFragment);
        } else {
            try {
                mShowFragment = cls.getConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            transaction.add(R.id.main_container, mShowFragment, tag);
        }
        transaction.commit();
    }

    /**
     * 获取权限
     */
    private void requestPermissions() {

        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .requestEach(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d("tag", permission.name + " is granted.");
                            Toast.makeText(MainActivity.this, "用户已经同意该权限", Toast.LENGTH_LONG).show();

                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                            Log.d("tag", permission.name + " is denied. More info should be provided.");
                            Toast.makeText(MainActivity.this, "用户拒绝了该权限，没有选中『不再询问』（Never ask again）," +
                                    "那么下次再次启动时。还会提示请求权限的对话框", Toast.LENGTH_LONG).show();

                        } else {
                            // 用户拒绝了该权限，而且选中『不再询问』
                            Log.d("tag", permission.name + " is denied.");
                            Toast.makeText(MainActivity.this, "用户拒绝了该权限，而且选中『不再询问』", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

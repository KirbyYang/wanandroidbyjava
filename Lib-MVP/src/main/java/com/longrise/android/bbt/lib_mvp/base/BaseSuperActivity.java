package com.longrise.android.bbt.lib_mvp.base;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.longrise.android.bbt.lib_mvp.R;
import com.longrise.android.bbt.lib_mvp.cache.BundlePool;
import com.longrise.android.bbt.lib_mvp.common.StrictConsts;
import com.longrise.android.bbt.lib_mvp.init.MvpManager;
import com.longrise.android.bbt.lib_mvp.util.permission.PermissionCallback;
import com.longrise.android.bbt.lib_mvp.util.permission.PermissionManager;

/**
 * Created by godliness on 2018/12/30.
 * From the BaoBao project
 *
 * @author godliness
 *         公共基础类
 */

abstract class BaseSuperActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();

        if (!MvpManager.sRegistered) {
            //注册应用程序级上下文
            MvpManager.init(getApplication());
        }
    }

    /**
     * 申请权限响应
     *
     * @param requestCode 权限请求码 {@link com.longrise.android.bbt.lib_mvp.util.permission.PermissionConsts}
     * @param result      权限请求结果 ture 权限申请通过
     */
    protected void onPermissionsResult(int requestCode, boolean result) {

    }

    /**
     * 检查权限
     *
     * @param permission 权限类型 {@link com.longrise.android.bbt.lib_mvp.util.permission.PermissionConsts}
     * @see #onPermissionsResult(int, boolean) 应该重写该方法
     */
    protected final void checkPermisson(final String permission) {
        PermissionManager.checkPermission(this, permission, new PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                onPermissionsResult(PermissionManager.get(permission).mRequestCode, true);
            }
        });
    }

    /**
     * setContentView()之前的一些配置
     */
    protected void beforeSetContentView() {
        initTheme();
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        orientationToPortrait();
        resetStatusBarColor();
    }

    protected void initTheme() {
        setTheme(R.style.Lib_MVP_AppCompat_Theme);
    }

    /**
     * 竖屏
     */
    protected void orientationToPortrait() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 横屏
     */
    protected void orientationToLandscape() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 重写该方法设置状态栏颜色
     */
    protected void resetStatusBarColor() {
//        setStatusBarClor(-1);
    }

    /**
     * 状态显示状态
     * View.SYSTEM_UI_FLAG_VISIBLE 可见状态
     * View.INVISIBLE 不可见状态，Activity会伸展全屏显示
     * View.SYSTEM_UI_FLAG_FULLSCREEN Activity全屏显示，且状态栏被隐藏覆盖掉
     * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
     *
     * @param visible 状态栏显示
     */
    protected void setStatusBarVisible(int visible) {
        getWindow().getDecorView().setSystemUiVisibility(visible);
    }

    /**
     * 当前Activity是否处于健康状态
     */
    public final boolean isHealthState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !isFinishing() && !isDestroyed();
        }
        return !isFinishing();
    }

    @Override
    public final void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        StrictConsts.checkStartActivityForResult(intent);
        try {
            super.startActivityForResult(intent, requestCode, options);
        } catch (Exception e) {
            StrictConsts.releaseCatchException(e);
        } finally {
            BundlePool.recycle(intent);
        }
        overridePendingTransition(R.anim.lib_mvp_base_slide_right_in, R.anim.lib_mvp_base_slide_remain);
    }

    @Override
    public final ComponentName startService(Intent service) {
        ComponentName name = null;
        try {
            name = super.startService(service);
        } catch (Exception e) {
            StrictConsts.releaseCatchException(e);
        }
        return name;
    }

    @Override
    public boolean stopService(Intent name) {
        boolean stopService = false;
        try {
            stopService = super.stopService(name);
        } catch (Exception e) {
            StrictConsts.releaseCatchException(e);
        }
        return stopService;
    }

    @Override
    public final boolean bindService(Intent service, ServiceConnection conn, int flags) {
        boolean bindService = false;
        try {
            bindService = super.bindService(service, conn, flags);
        } catch (Exception e) {
            StrictConsts.releaseCatchException(e);
        }
        return bindService;
    }

    /**
     * 广播的注册与卸载都不应该使用Activity级别上下文
     */
    @Override
    public final Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
        StrictConsts.checkRegisterReceiver(false);
        Intent intent = null;
        try {
            intent = super.registerReceiver(receiver, filter, broadcastPermission, scheduler);
        } catch (Exception e) {
            StrictConsts.releaseCatchException(e);
        }
        return intent;
    }

    /**
     * 广播的注册与卸载都不应该使用Activity级别上下文
     */
    @Override
    public final void unregisterReceiver(BroadcastReceiver receiver) {
        StrictConsts.checkRegisterReceiver(false);
        try {
            super.unregisterReceiver(receiver);
        } catch (Exception e) {
            StrictConsts.releaseCatchException(e);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.lib_mvp_base_slide_right_out);
    }

    /**
     * @see #onPermissionsResult(int, boolean)
     */
    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onPermissionsResult(requestCode, true);
        } else {
            onPermissionsResult(requestCode, false);
        }
    }
}



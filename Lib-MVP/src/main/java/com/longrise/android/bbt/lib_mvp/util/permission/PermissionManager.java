package com.longrise.android.bbt.lib_mvp.util.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;

import com.longrise.android.bbt.lib_mvp.R;
import com.longrise.android.bbt.lib_mvp.util.MvpLog;

/**
 * Created by godliness on 2019/1/14.
 * From the BaoBao project
 *
 * @author godliness
 *         权限管理
 *         {@link com.longrise.android.bbt.lib_mvp.base.BaseSuperActivity#onPermissionsResult(int, boolean)}
 *         <p>
 *         说明：
 *         1、首先检查是否已经被授权过该权限。
 *         2、如果被授权则{@link PermissionCallback#onPermissionGranted()}回调。
 *         3、如果未被授权，则自动申请该权限{@link com.longrise.android.bbt.lib_mvp.base.BaseSuperActivity#onPermissionsResult(int, boolean)}。
 *         4、如果用户之前有决绝过该权限、并且选择不再提示，则自动弹窗通知用户该权限的必要性。
 */

public final class PermissionManager {

    private static final ArrayMap<String, Permission> PERMISSION = new ArrayMap<>();

    static {
        PERMISSION.put(Manifest.permission.CAMERA, new Permission("相机", PermissionConsts.RequestCode.CAMERA));
        PERMISSION.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, new Permission("人体传感器", PermissionConsts.RequestCode.WRITE_EXTERNAL_STORAGE));
        PERMISSION.put(Manifest.permission.READ_CALENDAR, new Permission("读取日历", PermissionConsts.RequestCode.READ_CALENDAR));
        PERMISSION.put(Manifest.permission.READ_CONTACTS, new Permission("读取联系人", PermissionConsts.RequestCode.READ_CONTACTS));
        PERMISSION.put(Manifest.permission.ACCESS_FINE_LOCATION, new Permission("定位", PermissionConsts.RequestCode.ACCESS_FINE_LOCATION));
        PERMISSION.put(Manifest.permission.RECORD_AUDIO, new Permission("录音", PermissionConsts.RequestCode.RECORD_AUDIO));
        PERMISSION.put(Manifest.permission.CALL_PHONE, new Permission("拨打电话", PermissionConsts.RequestCode.CALL_PHONE));
        PERMISSION.put(Manifest.permission.BODY_SENSORS, new Permission("", PermissionConsts.RequestCode.BODY_SENSORS));
        PERMISSION.put(Manifest.permission.SEND_SMS, new Permission("发送短信", PermissionConsts.RequestCode.SEND_SMS));
        PERMISSION.put(Manifest.permission.SYSTEM_ALERT_WINDOW, new Permission("悬浮窗", PermissionConsts.RequestCode.SYSTEM_ALERT_WINDOW));
    }

    public static class Permission {

        public String mMsg;
        public int mRequestCode;

        Permission(String message, int requestCode) {
            this.mMsg = message;
            this.mRequestCode = requestCode;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Permission{");
            sb.append("mMsg='").append(mMsg).append('\'');
            sb.append(", mRequestCode=").append(mRequestCode);
            sb.append('}');
            return sb.toString();
        }
    }

    public static Permission get(String key) {
        return PERMISSION.get(key);
    }

    /**
     * @param target     Context
     * @param permission 申请的权限类型 {@link PermissionConsts}
     * @see com.longrise.android.bbt.lib_mvp.base.BaseSuperActivity#onPermissionsResult(int, boolean)
     */
    public static void checkPermission(Activity target, String permission, PermissionCallback callback) {
        if (ContextCompat.checkSelfPermission(target, permission) == PackageManager.PERMISSION_GRANTED) {
            if (callback != null) {
                callback.onPermissionGranted();
            }
            return;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(target, permission)) {
            //用户拒绝权限，并且选择了不再提醒
            PermissionHelper.showPermissionRationale(target, permission);
        } else {
            //申请权限
            ActivityCompat.requestPermissions(target, new String[]{permission}, PERMISSION.get(permission).mRequestCode);
        }
    }

    private static class PermissionHelper {

        /**
         * 当用户拒绝权限申请，并且选择了不在显示，此时要告诉用户该权限的必要性，并告知需要打开
         */
        private static void showPermissionRationale(Activity target, String permission) {
            if (target == null || target.isFinishing()) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(target);
            builder.setTitle(target.getString(R.string.lib_mvp_string_permission_title));
            builder.setMessage(String.format(target.getString(R.string.lib_mvp_string_permission_msg), PERMISSION.get(permission).mMsg));
            try {
                builder.create().show();
            } catch (Exception e) {
                MvpLog.print(e);
            }
        }
    }
}

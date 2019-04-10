package com.longrise.android.bbt.lib_mvp.util.permission;

import android.Manifest;

/**
 * Created by godliness on 2019/1/15.
 * From the BaoBao project
 *
 * @author godliness
 *         辅助{@link PermissionManager}
 */

public final class PermissionConsts {

    /**
     * requestCode
     */
    public static class RequestCode {
        public static final int CAMERA = 111;
        public static final int WRITE_EXTERNAL_STORAGE = 112;
        public static final int READ_CALENDAR = 113;
        public static final int READ_CONTACTS = 114;
        public static final int ACCESS_FINE_LOCATION = 115;
        public static final int RECORD_AUDIO = 116;
        public static final int CALL_PHONE = 117;
        public static final int BODY_SENSORS = 118;
        public static final int SEND_SMS = 119;
        public static final int SYSTEM_ALERT_WINDOW = 120;
    }

    /**
     * Permission
     */
    public static class Permisson {
        public static final String CAMERA = Manifest.permission.CAMERA;
        public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
        public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
        public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
        public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
        public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
        public static final String SEND_SMS = Manifest.permission.SEND_SMS;
        public static final String SYSTEM_ALERT_WINDOW = Manifest.permission.SYSTEM_ALERT_WINDOW;
    }

}

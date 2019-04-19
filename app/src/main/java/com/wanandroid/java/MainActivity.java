package com.wanandroid.java;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions();
    }

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

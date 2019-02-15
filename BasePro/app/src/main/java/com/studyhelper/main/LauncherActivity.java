package com.studyhelper.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.studyhelper.baselib.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create on 2018/10/24.
 * @author jogern
 */
public class LauncherActivity extends BaseActivity {

      private static final int PERMISSIONS_REQUEST = 0x64;

      private List<String> unPermissions = new ArrayList<>();
      private String[]     permissiones  = new String[]{
              Manifest.permission.READ_EXTERNAL_STORAGE,
              Manifest.permission.WRITE_EXTERNAL_STORAGE,
      };

      @Override
      protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                  for (String permission : permissiones) {
                        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager
                                .PERMISSION_GRANTED) {
                              unPermissions.add(permission);
                        }
                  }
                  if (!unPermissions.isEmpty()) {
                        setContentView(createContentView());
                        showSure();
                        return;
                  }
            }
            gotoMain();
      }


      private void showSure() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示").setMessage("正在申请app所需的权限,请点击允许,否则功能无法正常");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                        //将List转为数组
                        String[] permissions = unPermissions.toArray(new String[0]);
                        ActivityCompat.requestPermissions(LauncherActivity.this, permissions, PERMISSIONS_REQUEST);
                  }
            });
            AlertDialog alertDialog = builder.setCancelable(false).create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
      }


      private void gotoMain() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
      }

      private View createContentView() {
            int parent = FrameLayout.LayoutParams.MATCH_PARENT;
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(parent, parent);
            ImageView imageView = new ImageView(this);
//            imageView.setBackgroundResource(R.drawable.ic_listen_01);
            imageView.setLayoutParams(params);
            return imageView;
      }


      @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
              grantResults) {
            if (requestCode == PERMISSIONS_REQUEST && grantResults.length > 0) {
                  List<String> refusePermissions = new ArrayList<>();
                  for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                              continue;
                        }
                        refusePermissions.add(unPermissions.get(i));
                  }
                  if (refusePermissions.isEmpty()) {
                        gotoMain();
                  } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("请允许相关的权限");
                        builder.setPositiveButton("直接进入", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                    gotoMain();
                              }
                        });
                        builder.setNegativeButton("去打开权限", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                    Intent localIntent = new Intent();
                                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                                    startActivity(localIntent);
                                    finish();
                              }
                        });
                        AlertDialog alertDialog = builder.setCancelable(false).create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                  }
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      }
}

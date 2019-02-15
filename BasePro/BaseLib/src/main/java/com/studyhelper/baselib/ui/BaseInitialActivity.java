package com.studyhelper.baselib.ui;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
 
/**
 * Create on 2018/5/28.
 * @author jogern
 */
 
public abstract class BaseInitialActivity extends FragmentActivity {
 
 
      @Override
      protected void onCreate(@Nullable Bundle savedInstanceState) {
            setOrientation();
            super.onCreate(savedInstanceState);
            if (isHideNavigation() && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                  View decorView = getWindow().getDecorView();
                  decorView.setSystemUiVisibility(View.GONE);
            }
      }
 
      protected void setOrientation() {
            //设置全屏
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            // 横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
      }
 
      /**
       * 是否实现全屏沉浸式显示(即是隐藏虚拟按键)
       * @return {@code true} 表示隐藏虚拟按键
       * {@code false} 默认
       */
      protected boolean isHideNavigation() {
            return false;
      }
 
      @Override
      public void onWindowFocusChanged(boolean hasFocus) {
            if (isHideNavigation() && hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                  final View decorView = getWindow().getDecorView();
                  final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View
                          .SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                          .SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View
                          .SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                  decorView.setSystemUiVisibility(flags);
                  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                  decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                              if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                    decorView.setSystemUiVisibility(flags);
                              }
                        }
                  });
            }
            super.onWindowFocusChanged(hasFocus);
      }
 
 
 
}
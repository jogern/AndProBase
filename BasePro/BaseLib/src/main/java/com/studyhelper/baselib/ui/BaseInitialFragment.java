package com.studyhelper.baselib.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
/**
 * Create on 2018/5/28.
 * @author jogern
 */
 
public abstract class BaseInitialFragment extends Fragment {
 
      private View    mView;
      private boolean isInitial;
 
      @Nullable
      @Override
      public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
              savedInstanceState) {
            if (mView == null) {
                  mView = onInflaterView(inflater, container);
                  isInitial = true;
            }
            onCreateView(savedInstanceState);
            if (mView != null) {
                  ViewGroup parent = (ViewGroup) mView.getParent();
                  if (parent != null) {
                        parent.removeView(mView);
                  }
                  return mView;
            }
            return super.onCreateView(inflater, container, savedInstanceState);
      }
 
      @Override
      public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            if (isInitial) {
                  isInitial = false;
                  initialView(view);
            }
            onViewCreated(savedInstanceState);
      }
 
      /**
       * 加载View
       * @param inflater
       * @param container
       * @return View
       */
      protected abstract View onInflaterView(LayoutInflater inflater, ViewGroup container);
 
      /**
       * 初始化
       * @param view
       */
      protected abstract void initialView(View view);
 
      protected void onCreateView(@Nullable Bundle savedInstanceState) {}
 
      protected void onViewCreated(@Nullable Bundle savedInstanceState) {}
 
      protected boolean onBackPressed() {
            return false;
      }
}
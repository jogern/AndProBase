package com.studyhelper.baselib.mvp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.studyhelper.baselib.ui.BaseActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Create on 2018/10/9.
 * @author jogern
 */
public abstract class BaseViewDelegate {

      private static final int    WHAT_SHOW_DIALOG    = 0x64;
      private static final int    WHAT_DISMISS_DIALOG = 0x65;
      private static final String KEY_DIALOG_ID       = "dialog_id";
      private static final String KEY_DIALOG_MSG      = "dialog_msg";

      private final SparseArray<View>        mViews   = new SparseArray<>();
      private final Map<String, AlertDialog> mDialogS = new HashMap<>();

      private final Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                  if (msg.what == WHAT_SHOW_DIALOG) {
                        Bundle bundle = msg.getData();
                        if (bundle == null) {
                              return;
                        }
                        String dialogId = bundle.getString(KEY_DIALOG_ID);
                        if (dialogId == null || TextUtils.isEmpty(dialogId)) {
                              return;
                        }
                        AlertDialog dialog = mDialogS.get(dialogId);
                        if (dialog == null && getContext() != null) {
                              dialog = ProgressDialog.show(getContext(), null, bundle.getString(KEY_DIALOG_MSG));
                              mDialogS.put(dialogId, dialog);
                        }
                        if (dialog != null && !dialog.isShowing()) {
                              dialog.show();
                        }
                        return;
                  }
                  if (msg.what == WHAT_DISMISS_DIALOG) {
                        String dialogId = (String) msg.obj;
                        if (TextUtils.isEmpty(dialogId)) {
                              return;
                        }
                        AlertDialog dialog = mDialogS.get(dialogId);
                        if (dialog != null) {
                              dialog.dismiss();
                        }
                        mDialogS.remove(dialogId);
                        return;
                  }
                  handleDelegateMessage(msg);
            }
      };

      private BaseActivity mBaseMvpActivity;
      private OnDelegate   mOnDelegate;
      private View         mRootView;

      /**
       * 加载View
       * @param inflater
       * @param container
       */
      protected abstract void create(LayoutInflater inflater, ViewGroup container);


      protected <P extends BasePresenter> P getPresenter() {
            return mOnDelegate.getPresenter();
      }

      /**
       * 初始化控件
       */
      protected void initialView() {}

      public void onViewCreated(@Nullable Bundle savedInstanceState) {}

      protected void setContentView(View rootView) {
            mRootView = rootView;
      }

      public View getRootView() {
            return mRootView;
      }

      public final <T extends View> T findView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                  view = mRootView.findViewById(id);
                  if (view != null) {
                        mViews.put(id, view);
                  }
            }
            return (T) view;
      }

      public final Context getContext() {
            return mBaseMvpActivity;
      }

      protected Handler getDelegateHandler() {
            return mHandler;
      }

      protected void handleDelegateMessage(Message msg) {}

      public void toast(final String msg) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                  Toast.makeText(getRootView().getContext(), msg, Toast.LENGTH_SHORT).show();
            } else {
                  mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                              Toast.makeText(getRootView().getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                  });
            }
      }

      public String showProgressLoadingDialog(String message) {
            String uuid = UUID.randomUUID().toString();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_DIALOG_ID, uuid);
            bundle.putString(KEY_DIALOG_MSG, message);
            Message msg = mHandler.obtainMessage(WHAT_SHOW_DIALOG);
            msg.setData(bundle);
            msg.sendToTarget();
            return uuid;
      }

      public void dismissProgressLoadingDialog(String dialogId) {
            mHandler.obtainMessage(WHAT_DISMISS_DIALOG, dialogId).sendToTarget();
      }

      public void handerPost(Runnable runnable) {
            mHandler.post(runnable);
      }

      void setActivity(BaseActivity activity, OnDelegate onDelegate) {
            mBaseMvpActivity = activity;
            mOnDelegate = onDelegate;
      }

      protected Activity getBaseActivity() {
            return mBaseMvpActivity;
      }
}

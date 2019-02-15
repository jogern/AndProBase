package com.studyhelper.baselib.ui;

import android.app.Activity;
import android.support.annotation.AnimRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
 
/**
 * Create on 2018/5/28.
 * @author jogern
 */
 
public abstract class BaseFragment extends BaseInitialFragment {
 
      private BaseActivity mBaseActivity;
 
      @Override
      public void onAttach(Activity context) {
            super.onAttach(context);
            final FragmentActivity activity = getActivity();
            if (activity == null) {
                  return;
            }
            if (activity instanceof BaseActivity) {
                  mBaseActivity = (BaseActivity) activity;
            } else {
                  throw new IllegalStateException("Activity must extends  BaseActivity in attached to Activity");
            }
      }
 
      protected BaseActivity getBaseActivity() {
            if (mBaseActivity == null) {
                  final FragmentActivity activity = getActivity();
                  if (activity == null) {
                        throw new IllegalStateException("Fragment detached to Activity");
                  }
                  if (activity instanceof BaseActivity) {
                        mBaseActivity = (BaseActivity) activity;
                  } else {
                        throw new IllegalStateException("Activity must extends  BaseActivity in attached to " +
                                "Activity");
                  }
            }
            return mBaseActivity;
      }
 
      /** 增加Fragment到栈中 */
      protected void addFragmentInStack(@Nullable Fragment fragment) {
            getBaseActivity().addFragmentInStack(fragment);
      }
 
      /** 增加Fragment到栈中,有动画效果 */
      protected void addFragmentInStack(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes
              int popExit, @Nullable Fragment fragment) {
            getBaseActivity().addFragmentInStack(enter, exit, popEnter, popExit, fragment);
      }

      /** 增加Fragment到容器中 */
      protected void addFragmentInContainer(@Nullable Fragment fragment) {
            getBaseActivity().addFragmentInContainer(fragment);
      }

      /**
       * 增加Fragment到容器中,有动画效果
       * <br/>
       * 请使用.setCustomAnimations(enter, exit, popEnter, popExit)，
       * 这个方法的第1个参数对应进栈动画，第4个参数对应出栈动画，<br/>
       * 所以是.setCustomAnimations(进栈动画, exit, popEnter, 出栈动画))
       */
      protected void addFragmentInContainer(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes
              int popExit, @Nullable Fragment fragment) {
            getBaseActivity().addFragmentInContainer(enter, exit, popEnter, popExit, fragment);
      }
 
      /** 移除栈顶的Fragment */
      protected void removePopFragmentOfStack() {
            getBaseActivity().removePopFragmentOfStack();
      }
 
 
}
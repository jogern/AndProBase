package com.studyhelper.baselib.ui;

import android.support.annotation.AnimRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
 
/**
 * Create on 2018/5/28.
 * @author jogern
 */
 
public abstract class BaseActivity extends BaseInitialActivity {
 
      private int mFragmentContainerId;
 
      protected void setFragmentContainerId(int fragmentContainerId) {
            mFragmentContainerId = fragmentContainerId;
      }

      /** 增加Fragment到栈中 */
      protected void addFragmentInStack(@Nullable Fragment fragment) {
            if (fragment != null) {
                  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                  String tag = fragment.getClass().getName();
                  ft.replace(mFragmentContainerId, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
            }
      }

      /** 增加Fragment到容器中 */
      protected void addFragmentInContainer(@Nullable Fragment fragment) {
            if (fragment != null) {
                  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                  String tag = fragment.getClass().getName();
                  ft.replace(mFragmentContainerId, fragment, tag).commitAllowingStateLoss();
            }
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
            if (fragment != null) {
                  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                  ft.setCustomAnimations(enter, exit, popEnter, popExit);
                  String tag = fragment.getClass().getName();
                  ft.replace(mFragmentContainerId, fragment, tag).commitAllowingStateLoss();
            }
      }
      /**
       * 增加Fragment到栈中,有动画效果
       * <br/>
       * 请使用.setCustomAnimations(enter, exit, popEnter, popExit)，
       * 这个方法的第1个参数对应进栈动画，第4个参数对应出栈动画，<br/>
       * 所以是.setCustomAnimations(进栈动画, exit, popEnter, 出栈动画))
       */
      protected void addFragmentInStack(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes
              int popExit, @Nullable Fragment fragment) {
            if (fragment != null) {
                  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                  ft.setCustomAnimations(enter, exit, popEnter, popExit);
                  String tag = fragment.getClass().getName();
                  ft.replace(mFragmentContainerId, fragment, tag)
                          //将Fragment添加到回退栈中
                          .addToBackStack(tag).commitAllowingStateLoss();
            }
      }
 
      /** 移除栈顶的Fragment */
      protected void removePopFragmentOfStack() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            int entryCount = fragmentManager.getBackStackEntryCount();
            if (entryCount > 1) {
                  fragmentManager.popBackStack();
            }
      }
 
      /** 获取栈顶的Fragment */
      @Nullable
      protected Fragment getPopFragmentOfStack() {
            FragmentManager manager = getSupportFragmentManager();
            int count = manager.getBackStackEntryCount();
            if (count > 0) {
                  FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(count - 1);
 
                  return manager.findFragmentByTag(entry.getName());
            }
 
//            List<Fragment> fragments = manager.getFragments();
//            if (fragments != null && fragments.size() > 0) {
//                  return fragments.get(fragments.size() - 1);
//            }
            return null;
      }
 
 
      @Override
      public void onBackPressed() {
            Fragment popFragment = getPopFragmentOfStack();
            if (popFragment instanceof BaseInitialFragment) {
                  if (((BaseInitialFragment) popFragment).onBackPressed()) {
                        return;
                  }
            }
            FragmentManager manager = getSupportFragmentManager();
            int entryCount = manager.getBackStackEntryCount();
            if (entryCount > 1) {
                  super.onBackPressed();
            } else {
                  finish();
            }
      }
 
}
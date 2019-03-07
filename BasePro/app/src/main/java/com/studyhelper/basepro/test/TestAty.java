package com.studyhelper.basepro.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.studyhelper.baselib.mvp.BaseMvpActivity;

/**
 * Create on 2019/2/16.
 * @author jogern
 */
public class TestAty extends BaseMvpActivity<TestView,TestPresenter> {


      @Override
      protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//getPresenter().adbLoad();
      }


}

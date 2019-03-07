package com.studyhelper.basepro.test;


import android.os.Bundle;

import com.studyhelper.baselib.mvp.BasePresenter;

/**
 * Create on 2019/2/16.
 * @author jogern
 */
public class TestPresenter extends BasePresenter<TestView> {

      public void adbLoad(){
            getDelegate().initialView();
      }



}

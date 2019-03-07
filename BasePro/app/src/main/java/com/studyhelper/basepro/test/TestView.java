package com.studyhelper.basepro.test;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.studyhelper.baselib.mvp.BaseViewDelegate;
import com.studyhelper.basepro.R;

/**
 * Create on 2019/2/16.
 * @author jogern
 */
public class TestView extends BaseViewDelegate {


      @Override
      protected void create(LayoutInflater inflater, ViewGroup container) {
            setContentView(inflater.inflate(R.layout.aty_test, container, false));
      }

      @Override
      protected void initialView() {

      }
}

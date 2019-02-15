package com.studyhelper.baselib.mvp.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Create on 2018/10/9.
 * @author jogern
 */
public interface IViewDelegate {

      /**
       * 加载View
       * @param inflater
       * @param container
       */
      void create(LayoutInflater inflater, ViewGroup container);

      /**
       * 初始化控件
       */
      void initialView();

}

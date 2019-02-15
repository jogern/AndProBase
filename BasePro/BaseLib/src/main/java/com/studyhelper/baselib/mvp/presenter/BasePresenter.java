package com.studyhelper.baselib.mvp.presenter;

import com.studyhelper.baselib.mvp.view.IViewDelegate;

/**
 * Create on 2018/10/9.
 * @author jogern
 */
public abstract class BasePresenter<D extends IViewDelegate> {

      private D mDelegate;

      public final void attachDelegate(D delegate) {
            mDelegate = delegate;
      }

      protected D getDelegate() {
            return mDelegate;
      }

}

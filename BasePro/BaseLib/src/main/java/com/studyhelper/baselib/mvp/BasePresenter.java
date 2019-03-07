package com.studyhelper.baselib.mvp;


/**
 * Create on 2018/10/9.
 * @author jogern
 */
public class BasePresenter<D> {

      private D mDelegate;

      final void attachDelegate(D delegate) {
            mDelegate = delegate;
      }

      protected D getDelegate() {
            return mDelegate;
      }

      protected void onCreated() { }

      protected void onDestroy() { }

}

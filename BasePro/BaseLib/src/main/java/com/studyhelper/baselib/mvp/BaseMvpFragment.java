package com.studyhelper.baselib.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studyhelper.baselib.mvp.presenter.BasePresenter;
import com.studyhelper.baselib.mvp.view.BaseViewDelegate;
import com.studyhelper.baselib.ui.BaseFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Create on 2018/10/9.
 * @author jogern
 */
public abstract class BaseMvpFragment<D extends BaseViewDelegate, P extends BasePresenter<D>> extends
        BaseFragment {

      private D mDelegate;
      private P mPresenter;

      // protected abstract Class<D> delegateClass();

      /// protected abstract Class<P> presenterClass();

      public D getViewDelegate() {
            return mDelegate;
      }

      public P getPresenter() {
            return mPresenter;
      }

      private void createDelegate() {
            //返回表示此 Class 所表示的实体类的 直接父类 的 Type。注意，是直接父类
            //这里type结果是 com.dfsj.generic.GetInstanceUtil<com.dfsj.generic.User>
            Type type = getClass().getGenericSuperclass();
            // 判断 是否泛型
            if (type instanceof ParameterizedType) {
                  // 返回表示此类型实际类型参数的Type对象的数组.
                  // 当有多个泛型类时，数组的长度就不是1了
                  Type[] ptype = ((ParameterizedType) type).getActualTypeArguments();
                  //return (Class) ptype[0];  //将第一个泛型T对应的类返回（这里只有一个）

                  if (ptype.length < 2) {
                        throw new RuntimeException("create Delegate error len < 2");
                  }
                  Class<D> dClass = (Class<D>) ptype[0];
                  try {
                        mDelegate = dClass.newInstance();
                        mDelegate.setActivity(getBaseActivity());
                  } catch (InstantiationException e) {
                        throw new RuntimeException("create Delegate error" + e.getMessage());
                  } catch (IllegalAccessException e) {
                        throw new RuntimeException("create Delegate error" + e.getMessage());
                  } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                  }

                  Class<P> pClass = (Class<P>) ptype[1];
                  try {
                        mPresenter = pClass.newInstance();
                        mPresenter.attachDelegate(mDelegate);
                        return;
                  } catch (InstantiationException e) {
                        e.printStackTrace();
                  } catch (IllegalAccessException e) {
                        e.printStackTrace();
                  } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                  }
            }


//            try {
//                  mDelegate = delegateClass().newInstance();
//            } catch (java.lang.InstantiationException e) {
//                  throw new RuntimeException("create Delegate error" + e.getMessage());
//            } catch (IllegalAccessException e) {
//                  throw new RuntimeException("create Delegate error" + e.getMessage());
//            }
//            try {
//                  mPresenter = presenterClass().newInstance();
//                  mPresenter.attachDelegate(mDelegate);
//                  return;
//            } catch (java.lang.InstantiationException e) {
//                  e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                  e.printStackTrace();
//            }
            throw new RuntimeException("create Preseter error");
      }

      @Override
      public void onAttach(Context context) {
            super.onAttach(context);
            if (mDelegate == null) {
                  createDelegate();
            }
      }

      @Override
      protected final View onInflaterView(LayoutInflater inflater, ViewGroup container) {
            mDelegate.create(inflater, container);
            return mDelegate.getRootView();
      }

      @Override
      protected void onViewCreated(@Nullable Bundle savedInstanceState) {
            mDelegate.onViewCreated(savedInstanceState);
      }

      @Override
      protected void initialView(View view) {
            mDelegate.initialView();
      }

      public final <T extends View> T findView(int id) {
            return mDelegate.findView(id);
      }
}

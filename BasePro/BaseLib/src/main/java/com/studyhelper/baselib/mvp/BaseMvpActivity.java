package com.studyhelper.baselib.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.studyhelper.baselib.Logcat;
import com.studyhelper.baselib.ui.BaseActivity;
import com.studyhelper.baselib.util.ClassUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Create on 2018/10/9.
 * @author jogern
 */
public abstract class BaseMvpActivity<D extends BaseViewDelegate, P extends BasePresenter<D>> extends
        BaseActivity {

      private D mDelegate;
      private P mPresenter;

      public D getViewDelegate() {
            return mDelegate;
      }

      public P getPresenter() {
            return mPresenter;
      }

      private void createDelegate() {
            //返回表示此 Class 所表示的实体类的 直接父类 的 Type。注意，是直接父类
            //这里type结果是 com.dfsj.generic.GetInstanceUtil<com.dfsj.generic.User>
            //Logcat.logD("class: "+this.getClass().getName());
            Type type = this.getClass().getGenericSuperclass();
            // 判断 是否泛型
            if (type instanceof ParameterizedType) {
                  // 返回表示此类型实际类型参数的Type对象的数组.
                  // 当有多个泛型类时，数组的长度就不是1了
                  Type[] ptype = ((ParameterizedType) type).getActualTypeArguments();
                  //return (Class) ptype[0];  //将第一个泛型T对应的类返回（这里只有一个）
                  Class<D> dClass = ClassUtil.getClass(ptype, BaseViewDelegate.class);
                  try {
                        mDelegate = dClass.newInstance();
                        mDelegate.setActivity(this);
                  } catch (Exception e) {
                        throw new RuntimeException("create Delegate error" + e.getMessage());
                  }

                  Class<P> pClass = ClassUtil.getClass(ptype,BasePresenter.class);
                  try {
                        mPresenter = pClass.newInstance();
                        mPresenter.attachDelegate(mDelegate);
                        onDelegateCreated();
                        return;
                  } catch (Exception e) {
                       Logcat.e(e);
                  }
            }
            throw new RuntimeException("create Preseter error");
      }

      @Override
      protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(newBase);
            createDelegate();
      }

      /** view代理对象创建成功后执行 */
      protected void onDelegateCreated() {}

      @Override
      protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mDelegate.create(getLayoutInflater(), null);
            setContentView(mDelegate.getRootView());
            mDelegate.initialView();
            mDelegate.onViewCreated(savedInstanceState);
            mPresenter.onCreated();
            initialView();
      }

      public final <T extends View> T findView(int id) {
            return mDelegate.findView(id);
      }

      protected void initialView() {}

      @Override
      protected void onRestoreInstanceState(Bundle savedInstanceState) {
            super.onRestoreInstanceState(savedInstanceState);
            if (mDelegate == null) {
                  createDelegate();
            }
      }

      @Override
      protected void onDestroy() {
            super.onDestroy();
            getPresenter().onDestroy();
      }
}

package com.studyhelper.baselib.util;


import com.studyhelper.baselib.Logcat;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Create on 2019/2/20.
 * @author jogern
 */
public class ClassUtil {

      private ClassUtil() { }

      /**
       * 获取泛型class对象
       */
      public static <T> Class<T> getViewModel(Object obj, Class<?> filterClass) {
            Class<?> currentClass = obj.getClass();
            Class<T> tClass = getGenericClass(currentClass, filterClass);
//            if (tClass == null || tClass == AndroidViewModel.class || tClass == NoViewModel.class) {
//                  return null;
//            }
            Logcat.d("tClass == null: "+(tClass==null));
            return tClass;
      }

      private static <T> Class<T> getGenericClass(Class<?> klass, Class<?> filterClass) {
            Type type = klass.getGenericSuperclass();
            if (!(type instanceof ParameterizedType)) {
                  return null;
            }
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();
            for (Type t : types) {
                  Class<T> tClass = (Class<T>) t;
                 // Logcat.d("tClass: "+tClass.getName());
                  if (filterClass.isAssignableFrom(tClass)) {
                        return tClass;
                  }
            }
            return null;
      }

      public static <T> Class<T> getClass(Type[] types, Class<?> filterClass) {
            for (Type t : types) {
                  Class<T> tClass = (Class<T>) t;
                  // Logcat.d("tClass: "+tClass.getName());
                  if (filterClass.isAssignableFrom(tClass)) {
                        return tClass;
                  }
            }
            return null;
      }


}

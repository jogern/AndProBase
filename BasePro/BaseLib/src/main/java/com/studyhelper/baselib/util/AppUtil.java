package com.studyhelper.baselib.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.studyhelper.baselib.Logcat;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Create on 2018/8/21.
 * @author jogern
 */
public class AppUtil {

      private AppUtil() { }

      /**
       * 静默安装Apk
       * @param apkUri 格式:"file:///mnt/sdcard/starapp/starAppShop/viva.android.tv.apk"
       * @throws Exception
       */
      public static void installSilent(Uri apkUri) throws Exception {
            if (apkUri != null) {
                  Class<?> activityTherad = Class.forName("android.app.ActivityThread");
                  Class<?> paramTypes[] = getParamTypes(activityTherad, "getPackageManager");
                  Method method = activityTherad.getMethod("getPackageManager", paramTypes);
                  Object PackageManagerService = method.invoke(activityTherad);
                  Class<?> pmService = PackageManagerService.getClass();
                  Class<?> paramTypes1[] = getParamTypes(pmService, "installPackage");
                  method = pmService.getMethod("installPackage", paramTypes1);
                  method.invoke(PackageManagerService, apkUri, null, 0, null);
            }
      }


      private static Class<?>[] getParamTypes(Class<?> cls, String mName) {
            Class<?> cs[] = null;
            Method[] mtd = cls.getMethods();
            for (int i = 0; i < mtd.length; i++) {
                  if (!mtd[i].getName().equals(mName)) {
                        continue;
                  }
                  cs = mtd[i].getParameterTypes();
            }
            return cs;
      }

      /**
       * 杀死除本应用和系统应用之外的所有后台程序
       */
      public static void killAllBackgroundApp(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            //获取系统中所有正在运行的进程
            List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
            String currentPkgName = context.getApplicationInfo().packageName;
            for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
                  //一个进程中所有运行的应用的包名
                  String[] pkgNameList = appProcessInfo.pkgList;
                  for (String pkgName : pkgNameList) {
                        PackageInfo packageInfo = queryPackageInfo(context, pkgName);
                        if (packageInfo != null) {
                              if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 &&
                                      !currentPkgName.equals(pkgName)) {
                                    forceStopAPK(context, pkgName);
                              }
                        }
                  }
            }
      }

      /**
       * 根据包名查询PackageInfo（应用信息）
       * @param pkgName
       * @return PackageInfo 没有返回null
       */
      public static PackageInfo queryPackageInfo(Context context, String pkgName) {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                  packageInfo = packageManager.getPackageInfo(pkgName, 0);
            } catch (PackageManager.NameNotFoundException e) {
                  Logcat.e(e);
            }
            return packageInfo;
      }

      /**
       * 强制停止后台运行的应用
       * @param pkgName 包名
       */
      public static void forceStopAPK(Context context, String pkgName) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            Method forceStopPackage;
            try {
                  forceStopPackage = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage",
                          String.class);
                  forceStopPackage.setAccessible(true);
                  forceStopPackage.invoke(am, pkgName);
            } catch (Exception e) {
                  Logcat.e(e);
            }
      }

}

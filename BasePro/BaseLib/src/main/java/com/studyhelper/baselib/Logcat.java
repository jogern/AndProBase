package com.studyhelper.baselib;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Create on 2018/5/4.
 * @author jogern
 */
public class Logcat {

      private static final String  FORMAT       = ": %s: %s/ L";
      /** stackTrace 的固定下标 */
      private static final int     FIXED_INDEX  = 4;
      /** 每行字数 */
      private static final int     LINE_LEN     = 100;
      /** TAG前缀 以便过滤log */
      private static       String  TAG_PREFIX   = "Logcat";
      /** TAG的占位格式：线程名/ 类/ 方法名/ */
      private static       String  TAG_FORMAT   = TAG_PREFIX + FORMAT;

      private Logcat() { }

      private static int allow_level = Log.DEBUG;

      public static void printfLevel(int level){
            allow_level=level;
      }

      public static void initForApp(String tagPrefix) {
            TAG_PREFIX = tagPrefix;
            TAG_FORMAT = TAG_PREFIX + FORMAT;
      }

      public static void v(String msg) {
            if (allow_level <= Log.VERBOSE) {
                  printf(Log.VERBOSE, generateTagAndMsg(msg));
            }
      }

      public static void d(String msg) {
            if (allow_level<=Log.DEBUG) {
                  i(msg);
            }
      }

      public static void i(String msg) {
            if (allow_level <= Log.INFO) {
                  printf(Log.INFO, generateTagAndMsg(msg));
            }
      }

      public static void w(String msg) {
            if (allow_level <= Log.WARN) {
                  printf(Log.WARN, generateTagAndMsg(msg));
            }
      }

      public static void e(String msg) {
            if (allow_level<=Log.ERROR) {
                  printf(Log.ERROR, generateTagAndMsg(msg));
            }
      }

      public static void logD(String msg) {
            Log.i(getThrowableTag(), msg);
      }

      public static void logW(String msg) {
            Log.e(getThrowableTag(), msg);
      }

      public static void logE(String msg) {
            Log.e(getThrowableTag(), msg);
      }

      public static void e(Throwable e) {
            Log.e(getThrowableTag(), "", e);
      }

      private static String getThrowableTag() {
            StackTraceElement caller = null;
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace.length > FIXED_INDEX) {
                  caller = stackTrace[FIXED_INDEX];
            }
            String clsName = caller == null ? "" : caller.getClassName();
            if (TextUtils.isEmpty(clsName)) {
                  clsName = "UNKNOWN";
            }
            int line = caller == null ? -1 : caller.getLineNumber();
            String tag = Thread.currentThread().getName();
            return String.format(TAG_FORMAT + line, tag, clsName.substring(clsName.lastIndexOf(".") + 1));
      }

      private static String[] generateTagAndMsg(String msg) {
            StackTraceElement caller = null;
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace.length > FIXED_INDEX) {
                  caller = stackTrace[FIXED_INDEX];
            }
            String clsName = caller == null ? "" : caller.getClassName();
            if (TextUtils.isEmpty(clsName)) {
                  clsName = "UNKNOWN";
            }
            int line = caller == null ? -1 : caller.getLineNumber();
            String tag = Thread.currentThread().getName();
            tag = String.format(TAG_FORMAT + line, tag, clsName.substring(clsName.lastIndexOf(".") + 1));

            if (TextUtils.isEmpty(msg) || msg.length() <= LINE_LEN) {
                  return new String[]{tag, msg};
            } else {
                  List<String> list = createMsg(msg);
                  list.add(0, tag);
                  return list.toArray(new String[0]);
            }
      }

      private static List<String> createMsg(String msg) {
            List<String> list = new ArrayList<>();
            int len = TextUtils.isEmpty(msg) ? 0 : msg.length();
            int size = len / LINE_LEN;
            for (int i = 0; i < size; i++) {
                  list.add(msg.substring(LINE_LEN * i, LINE_LEN * (i + 1)));
            }
            list.add(msg.substring(LINE_LEN * size, msg.length()));
            return list;
      }

      private static void printf(int logLevel, String[] tag) {
            int len = tag == null ? 0 : tag.length;
            if (len < 1) {
                  return;
            }
            String logTag = tag[0];
            if (len >= 2) {
                  for (int i = 1; i < len; i++) {
                        realePrintf(logLevel, logTag, tag[i]);
                  }
            } else {
                  realePrintf(logLevel, logTag, "");
            }
      }

      private static void realePrintf(int logLevel, String tag, String msg) {
            switch (logLevel) {
                  case Log.VERBOSE:
                        Log.d(tag, msg);
                        break;
                  case Log.DEBUG:
                        Log.d(tag, msg);
                        break;
                  case Log.INFO:
                        Log.i(tag, msg);
                        break;
                  case Log.WARN:
                        Log.w(tag, msg);
                        break;
                  case Log.ERROR:
                        Log.e(tag, msg);
                        break;
                  default:
                        Log.d(tag, msg);
            }
      }
}
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

      /** TAG的占位格式：[T:线程名] [J:类] [func:方法名] [L:行号] */
      private static final String FORMAT = "[T:%s] [J:%s] [func:%s] [L:%s] ";
      /** stackTrace 的固定下标 */
      private static final int    FIXED_INDEX = 4;
      /** 每行字数 */
      private static final int    LINE_LEN = 500;
      /** TAG前缀 以便过滤log */
      private static       String TAG_PREFIX = "log";


      private Logcat() { }

      private static int allow_level = Log.DEBUG;

      public static void initForApp(String tagPrefix, int level) {
            allow_level = level;
            if (!TextUtils.isEmpty(tagPrefix)) {
                  TAG_PREFIX = tagPrefix;
            }
      }

      public static void v(String msg) {
            if (allow_level <= Log.VERBOSE) {
                  printf(Log.VERBOSE, generateTagAndMsg(msg));
            }
      }

      public static void d(String msg) {
            if (allow_level <= Log.DEBUG) {
                  printf(Log.WARN, generateTagAndMsg(msg));
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
            if (allow_level <= Log.ERROR) {
                  printf(Log.ERROR, generateTagAndMsg(msg));
            }
      }

      public static void logD(String msg) {
            Log.i(TAG_PREFIX, getThrowableTag() + msg);
      }

      public static void logW(String msg) {
            Log.e(TAG_PREFIX, getThrowableTag() + msg);
      }

      public static void logE(String msg) {
            Log.e(TAG_PREFIX, getThrowableTag() + msg);
      }

      public static void e(Throwable e) {
            List<String> stringList = new ArrayList<>();
            stringList.add(getThrowableTag());
            if (e != null) {
                  String msg = e.toString();
                  stringList.add(msg);

                  StackTraceElement[] stackTrace = e.getStackTrace();
                  for (StackTraceElement element : stackTrace) {
                        msg = element.toString();
                        stringList.add(msg);
                  }
                  Throwable cause = e.getCause();
                  while (cause != null) {
                        stringList.add("\t\t");
                        stringList.add(cause.toString());
                        stackTrace = cause.getStackTrace();
                        for (StackTraceElement element : stackTrace) {
                              msg = element.toString();
                              stringList.add(msg);
                        }
                        cause = cause.getCause();
                  }
            }
            printf(Log.ERROR, stringList.toArray(new String[0]));
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
            String methodName = caller == null ? "" : caller.getMethodName();
            int line = caller == null ? -1 : caller.getLineNumber();
            String tag = Thread.currentThread().getName();
            String cName = clsName.substring(clsName.lastIndexOf(".") + 1);
            return String.format(FORMAT, tag, cName, methodName, String.valueOf(line));
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
            String methodName = caller == null ? "" : caller.getMethodName();
            int line = caller == null ? -1 : caller.getLineNumber();
            String tag = Thread.currentThread().getName();
            String cName = clsName.substring(clsName.lastIndexOf(".") + 1);
            tag = String.format(FORMAT, tag, cName, methodName, String.valueOf(line));

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
                  if (len > 2) {
                        String msg;
                        int maxLen = 0;
                        for (int i = 1; i < len; i++) {
                              msg = logTag + tag[i];
                              if (msg.length() > maxLen) {
                                    maxLen = msg.length();
                              }
                        }
                        realPrintf(logLevel, "", getLineSp(maxLen));
                        for (int i = 1; i < len; i++) {
                              realPrintf(logLevel, logTag,  tag[i]);
                        }
                        realPrintf(logLevel, "", getLineSp(maxLen));
                  } else {
                        for (int i = 1; i < len; i++) {
                              realPrintf(logLevel, logTag,  tag[i]);
                        }
                  }
            } else {
                  realPrintf(logLevel, logTag, "");
            }
      }


      private static String getLineSp(int strlen) {
            StringBuilder msg = new StringBuilder();
            for (int i = 0; i < strlen; i++) {
                  msg.append("-");
            }
            return msg.toString();
      }

      private static void realPrintf(int logLevel, String tag, String msg) {
            switch (logLevel) {
                  case Log.VERBOSE:
                        Log.d(TAG_PREFIX, tag + msg);
                        break;
                  case Log.DEBUG:
                        Log.d(TAG_PREFIX, tag +  msg);
                        break;
                  case Log.INFO:
                        Log.i(TAG_PREFIX, tag +  msg);
                        break;
                  case Log.WARN:
                        Log.w(TAG_PREFIX, tag +  msg);
                        break;
                  case Log.ERROR:
                        Log.e(TAG_PREFIX, tag +  msg);
                        break;
                  default:
                        Log.d(TAG_PREFIX, tag +  msg);
            }
      }
}
package com.studyhelper.baselib.util;

import android.database.Cursor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Create on 2019/2/20.
 * @author jogern
 */
public class CloseUtil {

      private CloseUtil() { }

      public static void closeIo(InputStream in) {
            if (in != null) {
                  try {
                        in.close();
                  } catch (IOException e) {
                        e.printStackTrace();
                  }
            }
      }

      public static void closeIo(OutputStream out) {
            if (out != null) {
                  try {
                        out.close();
                  } catch (IOException e) {
                        e.printStackTrace();
                  }
            }
      }

      public static void closeInOut(InputStream in, OutputStream out) {
            closeIo(in);
            closeIo(out);
      }

      public static void closeOutIn(OutputStream out,InputStream in){
            closeIo(out);
            closeIo(in);
      }

      public static void closeCursor(Cursor cursor) {
            if (cursor != null) {
                  cursor.close();
            }
      }


}

package gaofu.xposedhook;

import android.text.TextUtils;
import android.util.Log;

import java.util.Collection;

/**
 * Created by gaofu.
 * Created on 2016/5/2.
 */
public class Util {

    public static String getLogTag(String tag) {
        return TextUtils.isEmpty(tag) ? "Xposed" : tag;
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static void printStackTrace(String tag)  {
        StackTraceElement[] elements = new RuntimeException().fillInStackTrace().getStackTrace();
        /*
         * elements[0] gaofu.xposedhook.Util.printStackTrace(Util.java)
         * elements[1] gaofu.xposedhook.callback.MethodHook.beforeHookedMethod(MethodHook.java)
         * elements[2] de.robv.android.xposed.XposedBridge.handleHookedMethod(XposedBridge.java)
         */
        for (int i = 3; i < elements.length; i++) {
            Log.i(tag, elements[i].toString());
        }
    }
}

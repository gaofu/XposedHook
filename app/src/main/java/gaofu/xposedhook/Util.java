package gaofu.xposedhook;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Collection;

/**
 * Created by gaofu.
 * Created on 2016/5/2.
 */
public class Util {
    private static final Gson gson = new Gson();

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

    public static String toString(Object o) {
        if (o == null) {
            return null;
        }

        try {
            // 如果o的类实现了public toString()方法，调用toString()
            o.getClass().getDeclaredMethod("toString");
            return o.toString();
        } catch (NoSuchMethodException e) {
            // 如果o的类没有实现public toString()方法，转换为json字符串
            return gson.toJson(o);
        }
    }
}

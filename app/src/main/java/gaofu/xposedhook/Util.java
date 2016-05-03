package gaofu.xposedhook;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
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

    public static void printStack(String tag)  {
        try {
            Class<?> threadClazz = Class.forName("android.os.Debug");
            Method method = threadClazz.getMethod("getCallers", int.class, int.class);
            String out = (String) method.invoke(null, 1, 20);
            Log.d(tag, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

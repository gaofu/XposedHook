package gaofu.xposedhook.callback;

import android.util.Log;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import gaofu.xposedhook.Util;

import static java.lang.String.format;

/**
 * Created by gaofu.
 * Created on 2016/5/2.
 */
public class MethodHook extends XC_MethodHook {
    private String tag;
    private boolean printStackTrace;
    private String method;

    public MethodHook() {
    }

    public MethodHook(String tag, boolean printStackTrace) {
        this.tag = tag;
        this.printStackTrace = printStackTrace;
    }

    public MethodHook(int priority) {
        super(priority);
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        method = param.method.toString();
        Log.d(tag, format("beforeHookedMethod %s", method));

        if (0 == param.args.length) {
            Log.d(tag, format("Method %s has no parameters", method));
        } else {
            Log.d(tag, format("Method %s has %d parameters", method, param.args.length));
            for (int i = 0; i < param.args.length; i++) {
                Object arg = param.args[i];
                if (arg == null) {
                    Log.d(tag, format("Method %s's param %d is null", method, i));
                } else {
                    Log.d(tag, format("Method %s's param %d is (Type=%s, Value=%s)",
                            method, i, arg.getClass().getName(), String.valueOf(arg)));
                }
            }
        }

        if (printStackTrace) {
            Util.printStackTrace(tag);
        }
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        Class<?> returnType = ((Method) param.method).getReturnType();
        if (Void.TYPE != returnType) {
            Object result = param.getResult();
            if (result == null) {
                Log.d(tag, format("Method %s returns null", method));
            } else {
                Log.d(tag, format("Method %s returns -> (Type=%s, Value=%s)",
                        method, result.getClass().getName(), Util.toString(result)));
           }
        }
        Log.d(tag, format("afterHookedMethod %s", method));
    }

//    private String getResultString(Object result) {
//        StringBuilder sb = new StringBuilder(result.getClass().getName()).append('{');
//        for (String name : resultFields.keySet()) {
//            String type = resultFields.get(name);
//            sb.append(name).append('(').append(type).append(")=");
//            try {
//                Object fieldValue = getField(result, name, type);
//                sb.append(fieldValue);
//            } catch (Throwable throwable) {
//                sb.append("null");
//            }
//            sb.append(',');
//        }
//        int pos = sb.length() - 1;
//        sb.replace(pos, pos, "}");
//
//        return sb.toString();
//    }

//    private Object getField(Object object, String name, String type){
//        switch(type){
//            case "int":     return XposedHelpers.getIntField(object, name);
//            case "byte":    return XposedHelpers.getByteField(object, name);
//            case "char":    return XposedHelpers.getCharField(object, name);
//            case "long":    return XposedHelpers.getLongField(object, name);
//            case "short":   return XposedHelpers.getShortField(object, name);
//            case "float":   return XposedHelpers.getFloatField(object, name);
//            case "double":  return XposedHelpers.getDoubleField(object, name);
//            case "boolean": return XposedHelpers.getBooleanField(object, name);
//            default:        return XposedHelpers.getObjectField(object, name);
//        }
//    }

}

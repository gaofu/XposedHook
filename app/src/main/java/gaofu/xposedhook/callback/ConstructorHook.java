package gaofu.xposedhook.callback;

import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import gaofu.xposedhook.Util;

import static java.lang.String.format;

/**
 * Created by gaofu.
 * Created on 2016/5/2.
 */
public class ConstructorHook extends XC_MethodHook {
    private String className;
    private String tag;

    public ConstructorHook() {
    }

    public ConstructorHook(String className, String tag) {
        this.className = className;
        this.tag = tag;
    }

    public ConstructorHook(int priority) {
        super(priority);
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        Log.d(tag, format("beforeHookedMethod %s constructor", className));

        if (0 == param.args.length) {
            Log.d(tag, format("Call %s's no argument constructor", className));
        } else {
            Log.d(tag, format("Call %s's constructor with %d parameters",
                    className, param.args.length));

            for (int i = 0; i < param.args.length; i++) {
                Object arg = param.args[i];
                if (arg == null) {
                    Log.d(tag, format("class %s constructor param %d is null", className, i));
                } else {
                    Log.d(tag, format("class %s constructor param %d -> (Type=%s, Value=%s)",
                            className, i, arg.getClass().getName(), Util.toString(arg)));
                }
            }
        }

        Util.printStackTrace(tag);
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        Log.d(tag, format("afterHookedMethod %s constructor", className));
    }
}

package gaofu.xposedhook;

import android.os.Environment;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import gaofu.xposedhook.callback.ConstructorHook;
import gaofu.xposedhook.callback.MethodHook;
import gaofu.xposedhook.domain.HookClass;
import gaofu.xposedhook.domain.HookMethod;
import gaofu.xposedhook.domain.HookPackage;

import static de.robv.android.xposed.XposedBridge.hookAllConstructors;
import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.*;
import static java.lang.String.format;

/**
 * Created by gaofu.
 * Created on 2016/5/2.
 */
public class XposedHookPackage implements IXposedHookLoadPackage{

    public static final String VERBOSE_TAG = "XposedHook";

    /**
     * YAML format configuration file.
     * Contains package names, class names, method names, parameter types to be hooked.
     */
//    private static final File hookConfig = new File("/sdcard", "hooks.yaml");
    private static final File hookConfig = new File(Environment.getExternalStorageDirectory(), "hooks.yaml");

    /**
     * Hook information loaded from {@link #hookConfig} file.
     */
    private static final CopyOnWriteArrayList<HookPackage> hookPackages = new CopyOnWriteArrayList<>();

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        /** Return if the configuration file does not exist. */
        if (!hookConfig.isFile()) {
            Log.v(VERBOSE_TAG, format(
                    "Xposed hook configuration file '%s' does not exist!", hookConfig));
            return;
        }

        /**
         * Load configurations if hookPackages is empty
         * or the file is modified during the past 30 seconds.
         */
        if (hookPackages.isEmpty() || isUpdated(hookConfig)) {
            hookPackages.clear();
            Log.v(VERBOSE_TAG, format("Load configuration file(%s) in app '%s'",
                    hookConfig, lpparam.processName));
            loadPackages();
            if (hookPackages.isEmpty()) {
                return;
            }
        }

        Log.v(VERBOSE_TAG, format("hookPackages in app(%s) is %s",
                lpparam.processName, hookPackages.toString()));

        for (HookPackage hookPackage : hookPackages) {
            Log.v(VERBOSE_TAG, "hookPackage:" + hookPackage.toString());
            if (lpparam.packageName.equals(hookPackage.getName())) {
                List<HookClass> hookClasses = hookPackage.getHookClasses();
                Log.v(VERBOSE_TAG, "hookClasses:" + hookClasses.toString());

                if (Util.isNotEmpty(hookClasses)) {
                    for (HookClass hookClass : hookClasses) {
                        String className = hookClass.getName();
                        Class<?> clazz = findClass(className, lpparam.classLoader);
                        List<HookMethod> hookMethods = hookClass.getHookMethods();
                        String tag = Util.getLogTag(hookClass.getTag());

                        /** Hook constructors */
                        if (hookClass.isConstructor()) {
                            hookAllConstructors(clazz, new ConstructorHook(className, tag));
                        }

                        if (Util.isNotEmpty(hookMethods)) {
                            /** Hook methods */
                            for (HookMethod hookMethod : hookMethods) {
                                String methodName = hookMethod.getName();
                                List<String> params = hookMethod.getParams();
                                boolean printStackTrace = hookMethod.isPrintStackTrace();

                                MethodHook methodHook = new MethodHook(tag, printStackTrace);
                                Object[] typesAndCallback;
                                if (Util.isEmpty(params)) {
                                    typesAndCallback = new Object[]{methodHook};
                                } else {
                                    typesAndCallback = new Object[params.size() + 1];
                                    params.toArray(typesAndCallback);
                                    typesAndCallback[params.size()] = methodHook;
                                }
                                findAndHookMethod(clazz, methodName, typesAndCallback);
                            }
                        }
                    }
                } else {
                    Log.v(VERBOSE_TAG, "No class to be hooked!");
                }

                /**
                 * {@code lpparam.packageName} is the name of the package being loaded.
                 * So break after found the package being loaded.
                 */
                break;
            }
        }
    }

    /** Reload configurations if the file is modified during the past 30 seconds. */
    private boolean isUpdated(File file) {
        return FileUtils.isFileNewer(file, (System.currentTimeMillis() - 30 * 1000));
    }

    private void loadPackages() {
        Yaml yaml = new Yaml();
        InputStream is = null;
        try {
            is = FileUtils.openInputStream(hookConfig);
            for (Object o : yaml.loadAs(is, List.class)) {
                hookPackages.add((HookPackage) o);
            }
        } catch (IOException e) {
            log(e.getMessage());
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
}

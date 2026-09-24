package com.example.bgmispoof;

import android.os.Build;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {

    // Target game: BGMI
    private static final String TARGET_PACKAGE = "com.pubg.imobile";

    // Edit these to change the spoofed device.
    // NP03J is the model string the original Magisk module used.
    private static final String FAKE_MODEL = "NP03J";
    private static final String FAKE_DEVICE = "NP03J";
    private static final String FAKE_PRODUCT = "NP03J";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!TARGET_PACKAGE.equals(lpparam.packageName)) return;

        try {
            XposedHelpers.setStaticObjectField(Build.class, "MODEL", FAKE_MODEL);
            XposedHelpers.setStaticObjectField(Build.class, "DEVICE", FAKE_DEVICE);
            XposedHelpers.setStaticObjectField(Build.class, "PRODUCT", FAKE_PRODUCT);
            XposedBridge.log("BgmiSpoof: Build fields set to " + FAKE_MODEL);
        } catch (Throwable t) {
            XposedBridge.log("BgmiSpoof: failed to set Build fields: " + t);
        }

        // Some games read props through SystemProperties instead of Build.
        try {
            Class<?> sp = XposedHelpers.findClass("android.os.SystemProperties", lpparam.classLoader);
            XC_MethodHook propHook = new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    Object key = param.args.length > 0 ? param.args[0] : null;
                    if (key instanceof String && ((String) key).endsWith(".model")
                            && ((String) key).startsWith("ro.product")) {
                        param.setResult(FAKE_MODEL);
                    }
                }
            };
            XposedBridge.hookAllMethods(sp, "get", propHook);
        } catch (Throwable t) {
            XposedBridge.log("BgmiSpoof: SystemProperties hook failed: " + t);
        }
    }
}

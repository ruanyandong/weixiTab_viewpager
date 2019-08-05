package com.ai.viewpagertab.utils;

import android.util.Log;
import com.ai.viewpagertab.BuildConfig;

public class L {

    private static final String TAG = "hyman";
    private static boolean sDebug = BuildConfig.DEBUG;

    public static void d(String msg,Object... args){
        if (!sDebug){
            return;
        }
        Log.d(TAG,String.format(msg,args));
    }

}

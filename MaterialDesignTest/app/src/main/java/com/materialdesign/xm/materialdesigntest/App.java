package com.materialdesign.xm.materialdesigntest;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by xm on 2015/8/20.
 */
public class App extends Application {

    private static App instance = null;

    private RefWatcher refWatcher;

    public static App getInstance() {
        return instance;
    }

    public RefWatcher getRefWatcher(Context context) {
        return refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        refWatcher = LeakCanary.install(this);
    }
}

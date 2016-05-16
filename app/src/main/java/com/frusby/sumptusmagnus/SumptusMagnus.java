package com.frusby.sumptusmagnus;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by alexisjouhault on 4/4/16.
 */
public class SumptusMagnus extends Application {

    private final String TAG = SumptusMagnus.class.toString();

    public static SumptusMagnus getApplication(@NonNull Context context) {
        if (context instanceof SumptusMagnus) {
            return (SumptusMagnus) context;
        }
        return (SumptusMagnus) context.getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Launching application");
//        Log.d("Application", "Launching Dagger");
//        appComponent = DaggerApplicationComponent.builder()
//                .applicationModule(new ApplicationModule())
//                .build();
    }
}

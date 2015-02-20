package com.example.patres.prototype1;

import android.app.Application;
import android.content.Context;

public class App extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }

    /**
     * Return a localized string from the application's resources
     *
     * @param resId Resource id for the string
     */
    public static final String getStr(int resId) {
        return mContext.getResources().getString(resId);
    }

    /**
     * Return a localized string from the application's resources,
     * substituting the format arguments.
     *
     * @param resId Resource id for the format string
     * @param formatArgs The format arguments that will be used for substitution
     */
    public static final String getStr(int resId, Object... formatArgs) {
        return mContext.getResources().getString(resId, formatArgs);
    }
}
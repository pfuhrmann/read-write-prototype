package com.example.patres.prototype1;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    /**
     * Tag used for logs
     */
    private static final String TAG = App.class.getName();

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * Return a localized string from the application's resources
     *
     * @param resId Resource id for the string
     */
    public static String getStr(int resId) {
        return mContext.getResources().getString(resId);
    }

    /**
     * Return a localized string from the application's resources,
     * substituting the format arguments.
     *
     * @param resId Resource id for the format string
     * @param formatArgs The format arguments that will be used for substitution
     */
    public static String getStr(int resId, Object... formatArgs) {
        return mContext.getResources().getString(resId, formatArgs);
    }

    /**
     * Return a tag string
     */
    public static String getTag() {
        return TAG;
    }
}

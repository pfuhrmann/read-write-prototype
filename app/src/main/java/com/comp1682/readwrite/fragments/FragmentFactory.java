package com.comp1682.readwrite.fragments;

import android.app.Fragment;

/**
 * Fragment factory pattern
 */
public class FragmentFactory {

    public static Fragment getFragment(String type) {
        switch (type) {
            case "tag-read":
                return new ReadActionFragment();
            case "tag-write":
                return new WriteActionFragment();
            case "tag-other":
                return new OtherActionsFragment();
            case "write-email":
                return new WriteEmailFragment();
            case "write-text":
                return new WriteTextFragment();
            case "write-uri":
                return new WriteUriFragment();
            default:
                return null;
        }
    }
}

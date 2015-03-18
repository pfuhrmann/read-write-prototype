package com.comp1682.readwrite.fragments;

import android.app.Fragment;

/**
 * Fragment factory pattern
 */
public class FragmentFactory {

    public static Fragment getFragment(String type) {
        if (type.equals("tag-read")) {
            return new ReadActionFragment();
        } else if (type.equals("tag-write")) {
            return new WriteActionFragment();
        } else if (type.equals("write-email")) {
            return new WriteEmailFragment();
        } else if (type.equals("write-text")) {
            return new WriteTextFragment();
        } else if (type.equals("write-uri")) {
            return new WriteUriFragment();
        } else {
            return null;
        }
    }
}

package com.comp1682.readwrite.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Immutable Encoding operation result
 */
public class EncodeResult implements Parcelable {
    // Parcel keys
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";

    private final boolean mStatus;
    private final String mMessage;

    /**
     * @param status Status of the encoding process
     * @param message Associated message with result
     */
    public EncodeResult(boolean status, String message) {
        mStatus = status;
        mMessage = message;
    }

    public boolean getStatus() {
        return mStatus;
    }

    public String getMessage() {
        return mMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_STATUS, mStatus);
        bundle.putString(KEY_MESSAGE, mMessage);
        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator<EncodeResult> CREATOR = new Creator<EncodeResult>() {
        @Override
        public EncodeResult createFromParcel(Parcel source) {
            // read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle();
            // instantiate a person using values from the bundle
            return new EncodeResult(bundle.getBoolean(KEY_STATUS),
                    bundle.getString(KEY_MESSAGE));
        }

        @Override
        public EncodeResult[] newArray(int size) {
            return new EncodeResult[size];
        }
    };
}

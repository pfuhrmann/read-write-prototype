package com.example.patres.prototype1.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.Toast;

import com.example.patres.prototype1.R;

public class NFCManager {

    // Intent extra category mappings
    public static final int CATEGORY_READ = 0;
    public static final int CATEGORY_ENCODE = 1;

    /**
     * PendingIntent object so the Android system can populate it with the details of the tag when
     *  it is scanned.
     */
    private PendingIntent mPendingIntent;

    /**
     * NFC Adapter instance
     */
    private NfcAdapter mNfcAdapter;

    /**
     * Fragments activity instance
     */
    private Activity mActivity;

    /**
     * Intent filters for NFC
     */
    private IntentFilter[] mIntentFiltersArray;

    /**
     * Intent extra data
     */
    private Bundle mExtra;


    /**
     * New NFCManager instance
     * @param activity Activity instance
     */
    public NFCManager(Activity activity, Bundle extra) {
        mActivity = activity;
        mExtra = extra;
    }

    /**
     * Prepare for NFC foreground dispatch
     */
    public void prepare() {
        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(mActivity);
        if (mNfcAdapter == null) {
            Toast.makeText(mActivity, R.string.nfc_not_available, Toast.LENGTH_LONG).show();
            return;
        }

        // Foreground dispatch preparation
        int intentFlags = PendingIntent.FLAG_ONE_SHOT + PendingIntent.FLAG_UPDATE_CURRENT;
        Intent intent = new Intent(mActivity, mActivity.getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .replaceExtras(mExtra);
        mPendingIntent = PendingIntent.getActivity(
                mActivity, 0, intent, intentFlags);

        // Intent filters preparation
        IntentFilter ndefFilter = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mIntentFiltersArray = new IntentFilter[] {ndefFilter};
    }

    /**
     * Enable NFC foreground dispatch
     */
    public void enableForegroundDispatch() {
        mNfcAdapter.enableForegroundDispatch(
                mActivity, mPendingIntent, mIntentFiltersArray, null);
    }

    /**
     * Disable NFC foreground dispatch
     */
    public void disableForegroundDispatch() {
        mNfcAdapter.disableForegroundDispatch(mActivity);
    }
}

package com.example.patres.prototype1.Helpers;

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
    public static final String CATEGORY_READ = "read";
    public static final String CATEGORY_ENCODE = "encode";

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
     * Intent extra category
     */
    private String mCategory;


    /**
     * New NFCManager instance
     * @param activity Activity instance
     */
    public NFCManager(Activity activity, String category) {
        mActivity = activity;
        mCategory = category;
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
        Bundle bundle = new Bundle();
        bundle.putString("category", mCategory);
        Intent intent = new Intent(mActivity, mActivity.getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtras(bundle);
        mPendingIntent = PendingIntent.getActivity(mActivity, 0, intent, 0);

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

package com.comp1682.readwrite.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.comp1682.readwrite.R;

public class NFCManager {

    // Intent extra category mappings
    public static final int CATEGORY_READ = 0;
    public static final int CATEGORY_ENCODE = 1;
    public static final int CATEGORY_COPY_READ = 2;
    public static final int CATEGORY_COPY_ENCODE = 3;

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
    private final Activity mActivity;

    /**
     * Intent filters for NFC
     */
    private IntentFilter[] mIntentFiltersArray;

    /**
     * Intent extra data
     */
    private final Bundle mExtra;


    /**
     * New NFCManager instance
     * @param activity Activity instance
     * @param extra Extra data for NFC intent
     */
    private NFCManager(Activity activity, Bundle extra) {
        mActivity = activity;
        mExtra = extra;
    }

    /**
     * Static factory method
     * Instantiate new NFC manager for reading tags
     */
    public static NFCManager getReadManager(Activity activity) {
        Bundle args = new Bundle();
        args.putInt("category", NFCManager.CATEGORY_READ);

        return new NFCManager(activity, args);
    }

    /**
     * Static factory method
     * Instantiate new NFC manager for encoding tags
     */
    public static NFCManager getEncodeManager(Activity activity, NdefRecord record) {
        Bundle args = new Bundle();
        args.putInt("category", NFCManager.CATEGORY_ENCODE);
        args.putParcelable("record", record);

        return new NFCManager(activity, args);
    }

    /**
     * Static factory method
     * Instantiate new NFC manager for step 1 of copying tags
     */
    public static NFCManager getCopyReadManager(Activity activity) {
        Bundle args = new Bundle();
        args.putInt("category", NFCManager.CATEGORY_COPY_READ);

        return new NFCManager(activity, args);
    }

    /**
     * Static factory method
     * Instantiate new NFC manager for step 2 of copying tags
     */
    public static NFCManager getCopyEncodeManager(Activity activity, Parcelable[] msgs) {
        Bundle args = new Bundle();
        args.putInt("category", NFCManager.CATEGORY_COPY_ENCODE);
        args.putParcelableArray("ndef", msgs);

        return new NFCManager(activity, args);
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

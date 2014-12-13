package com.example.patres.prototype1.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.Toast;

import com.example.patres.prototype1.MainActivity;

public class NFCAwareFragment extends Fragment {

    /**
     * PendingIntent object so the Android system can populate it with the details of the tag when
     *  it is scanned.
     */
    private PendingIntent pIntent;

    /**
     * NFC Adapter instance
     */
    private NfcAdapter mNfcAdapter;

    /**
     * Fragments activity instance
     */
    private Activity activity;

    /**
     * Intent filters for NFC
     */
    private IntentFilter[] intentFiltersArray;

    public NFCAwareFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();

        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (mNfcAdapter == null) {
            Toast.makeText(activity, "NFC is not available", Toast.LENGTH_LONG).show();
            return;
        }

        // Foreground dispatch preparation
        pIntent = PendingIntent.getActivity(
                activity, 0, new Intent(activity, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Intent filters preparation
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        }
        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[] {ndef, };
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(1);
    }

    @Override
    public void onPause() {
        super.onPause();
        mNfcAdapter.disableForegroundDispatch(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNfcAdapter.enableForegroundDispatch(activity, pIntent, intentFiltersArray, null);
    }
}

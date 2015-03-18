package com.comp1682.readwrite.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.comp1682.readwrite.utils.NFCManager;

public class NfcAwareFragment extends Fragment {

    /**
     * NFC helper class
     */
    private NFCManager nfcManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nfcManager = NFCManager.getReadManager(getActivity());
        nfcManager.prepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        nfcManager.enableForegroundDispatch();
    }

    @Override
    public void onPause() {
        super.onPause();
        nfcManager.disableForegroundDispatch();
    }
}

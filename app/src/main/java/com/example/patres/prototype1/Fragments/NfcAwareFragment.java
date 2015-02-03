package com.example.patres.prototype1.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.example.patres.prototype1.Helpers.NFCManager;

public class NfcAwareFragment extends Fragment {

    /**
     * NFC helper class
     */
    private NFCManager nfcManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Extra data for NFC intent
        Bundle bundle = new Bundle();
        bundle.putInt("category", NFCManager.CATEGORY_READ);

        nfcManager = new NFCManager(getActivity(), bundle);
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

package com.comp1682.readwrite.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.nfc.NdefRecord;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.comp1682.readwrite.utils.NFCManager;
import com.comp1682.readwrite.R;

public class EncodeDialogFragment extends DialogFragment {

    /**
     * NFC helper class
     */
    private NFCManager mNfcManager;

    /**
     * Static factory method
     */
    public static EncodeDialogFragment newInstance(NdefRecord record) {
        Bundle args = new Bundle();
        args.putParcelable("ndef_record", record);
        EncodeDialogFragment fragment = new EncodeDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NdefRecord record = getArguments().getParcelable("ndef_record");
        mNfcManager = NFCManager.getEncodeManager(getActivity(), record);
        mNfcManager.prepare();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        return new AlertDialog.Builder(getActivity())
                .setView(inflater.inflate(R.layout.dialog_encode, null))
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing just close modal
                    }
                }).create();
    }

    @Override
    public void onResume() {
        super.onResume();
        mNfcManager.enableForegroundDispatch();
    }

    @Override
    public void onPause() {
        super.onPause();
        mNfcManager.disableForegroundDispatch();
    }
}

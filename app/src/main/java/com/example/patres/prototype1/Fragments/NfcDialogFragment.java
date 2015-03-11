package com.example.patres.prototype1.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.nfc.NdefRecord;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.patres.prototype1.helpers.NFCManager;
import com.example.patres.prototype1.R;

public class NfcDialogFragment extends DialogFragment {

    /**
     * Extra write data for NFCManager
     */
    private NdefRecord mRecord;

    /**
     * NFC helper class
     */
    private NFCManager mNfcManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = new Bundle();
        bundle.putInt("category", NFCManager.CATEGORY_ENCODE);
        bundle.putParcelable("record", mRecord);
        mNfcManager = new NFCManager(getActivity(), bundle);
        mNfcManager.prepare();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        return new AlertDialog.Builder(getActivity())
                .setView(inflater.inflate(R.layout.fragment_nfc_dialog, null))
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

    /**
     *
     * @param record Extra message for intent (NDEFRecord)
     */
    public void setNdefRecord(NdefRecord record) {
        mRecord = record;
    }
}

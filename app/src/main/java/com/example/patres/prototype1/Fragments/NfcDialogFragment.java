package com.example.patres.prototype1.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.patres.prototype1.R;

public class NfcDialogFragment extends DialogFragment {

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
}

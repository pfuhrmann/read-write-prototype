package com.comp1682.readwrite.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.comp1682.readwrite.App;
import com.comp1682.readwrite.utils.NFCManager;
import com.comp1682.readwrite.R;

public class CopyDialogFragment extends DialogFragment {

    /**
     * NFC helper class
     */
    private NFCManager mNfcManager;

    /**
     * Step in copy process
     */
    private int mStep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = new Bundle();
        mStep = getArguments().getInt("step");
        // Read source tag
        if (mStep == 1) {
            bundle.putInt("category", NFCManager.CATEGORY_COPY_READ);
        // Write destination tag
        } else if (mStep == 2) {
            Parcelable[] msgs = getArguments().getParcelableArray("ndef");
            bundle.putInt("category", NFCManager.CATEGORY_COPY_ENCODE);
            bundle.putParcelableArray("ndef", msgs);
        }

        mNfcManager = new NFCManager(getActivity(), bundle);
        mNfcManager.prepare();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Setup custom dialog layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_copy, null);
        TextView step = (TextView) view.findViewById(R.id.textView9);
        TextView mMessage = (TextView) view.findViewById(R.id.textView8);

        // Setup view for different steps
        if (mStep == 1) {
            step.setText(App.getStr(R.string.copy_step_1));
        } else if (mStep == 2) {
            step.setText(App.getStr(R.string.copy_step_2));
            mMessage.setText(App.getStr(R.string.approach_destination_tag));
        }

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
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

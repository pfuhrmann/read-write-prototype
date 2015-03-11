package com.example.patres.prototype1.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.nfc.NdefRecord;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.patres.prototype1.activities.MainActivity;
import com.example.patres.prototype1.R;

public class WriteEmailFragment extends InnerFragment
        implements View.OnClickListener {

    private EditText mTo;
    private EditText mSubject;
    private EditText mBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_write_email, container, false);
        mTo = (EditText) view.findViewById(R.id.editTextTo);
        mSubject = (EditText) view.findViewById(R.id.editTextSubject);
        mBody = (EditText) view.findViewById(R.id.editTextBody);
        // Encode button
        Button btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.SECTION_WRITE);
    }

    @Override
    public void onClick(View v) {
        // NDEF Record to write
        String uriStr = "mailto:" + mTo.getText() + "?subject=" + mSubject.getText() +
                "&body=" + mBody.getText();
        Uri uri = Uri.parse(uriStr);
        NdefRecord record = NdefRecord.createUri(uri);

        // Show dialog fragment
        NfcDialogFragment fragment = new NfcDialogFragment();
        fragment.setNdefRecord(record);
        FragmentManager fragmentManager = getFragmentManager();
        fragment.show(fragmentManager, "dialog_fragment");
    }
}

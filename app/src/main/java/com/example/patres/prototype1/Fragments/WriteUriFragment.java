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
import android.widget.Spinner;

import com.example.patres.prototype1.activities.MainActivity;
import com.example.patres.prototype1.R;

public class WriteUriFragment extends InnerFragment
        implements View.OnClickListener {

    private EditText mText;
    private Spinner mSpinner;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.SECTION_WRITE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_write_uri, container, false);
        mText = (EditText) view.findViewById(R.id.editTextTo);
        mSpinner = (Spinner) view.findViewById(R.id.spinner);
        // Encode button
        Button btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        // NDEF Record to write
        String uriStr = mSpinner.getSelectedItem().toString() + mText.getText();
        Uri uri = Uri.parse(uriStr);
        NdefRecord record = NdefRecord.createUri(uri);
        EncodeDialogFragment fragment = new EncodeDialogFragment();
        fragment.setNdefRecord(record);

        FragmentManager fragmentManager = getFragmentManager();
        fragment.show(fragmentManager, "dialog_fragment");
    }
}

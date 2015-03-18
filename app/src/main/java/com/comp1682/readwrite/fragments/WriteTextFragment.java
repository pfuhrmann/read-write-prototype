package com.comp1682.readwrite.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.nfc.NdefRecord;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.comp1682.readwrite.R;
import com.comp1682.readwrite.activities.MainActivity;

public class WriteTextFragment extends InnerFragment
        implements View.OnClickListener {

    private EditText mText;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.SECTION_WRITE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_write_text, container, false);
        mText = (EditText) view.findViewById(R.id.editTextTo);
        // Encode button
        Button btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        // Create Text NDEF Record
        NdefRecord record = NdefRecord.createTextRecord("en", mText.getText().toString());

        // Instantiate encode fragment
        Bundle args = new Bundle();
        args.putParcelable("ndef_record", record);
        EncodeDialogFragment fragment = new EncodeDialogFragment();
        fragment.setArguments(args);
        // Show encode fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragment.show(fragmentManager, "encode_fragment");
    }
}

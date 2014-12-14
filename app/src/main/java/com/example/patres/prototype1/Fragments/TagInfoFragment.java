package com.example.patres.prototype1.Fragments;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.patres.prototype1.MainActivity;
import com.example.patres.prototype1.R;

import java.util.Arrays;

public class TagInfoFragment extends NfcAwareFragment {

    /**
     * NFC mTag instance
     */
    private Tag mTag;

    /**
     * NDEF messages contained in mTag
     */
    private NdefMessage[] mNdef;

    public TagInfoFragment setTag(Tag tag) {
        mTag = tag;
        return this;
    }

    public TagInfoFragment setNdef(NdefMessage[] ndef) {
        mNdef = ndef;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tag_info, container, false);

        NdefMessage ndef = getFirstNdefMessage();
        NdefRecord record = ndef.getRecords()[0];

        // Map mTag info to view
        TextView type = (TextView) rootView.findViewById(R.id.textViewType);
        type.setText(Arrays.toString(record.getType()));
        TextView data = (TextView) rootView.findViewById(R.id.textViewData);
        data.setText(Arrays.toString(record.getPayload()));
        TextView tech = (TextView) rootView.findViewById(R.id.textViewTech);
        tech.setText(Arrays.toString(mTag.getTechList()));
        TextView id = (TextView) rootView.findViewById(R.id.textViewId);
        id.setText(Arrays.toString(mTag.getId()));

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.SECTION_INFO);
    }

    private NdefMessage getFirstNdefMessage() {
        return mNdef[0];
    }
}

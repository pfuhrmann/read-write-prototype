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
     * NFC tag instance
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
        View view = inflater.inflate(R.layout.fragment_tag_info, container, false);

        // Map Tag Info
        TextView idTagTV = (TextView) view.findViewById(R.id.textViewTagId);
        if (mTag.getId() != null) {
            idTagTV.setText(new String(mTag.getId()));
        } else {
            idTagTV.setText(R.string.not_available);
        }
        // Map Tag Technology info to view
        TextView techTagTV = (TextView) view.findViewById(R.id.textViewTagTech);
        techTagTV.setText(Arrays.toString(mTag.getTechList()));

        // Map NDEF Info to view
        // TODO: Way of displaying multiple NDEF messages
        if (hasNdefMessage()) {
            NdefMessage ndef = getFirstNdefMessage();
            NdefRecord record = ndef.getRecords()[0];

            // Map ID to view
            TextView idNdefTV = (TextView) view.findViewById(R.id.textViewNdefId);
            idNdefTV.setText(new String(record.getId()));
            // Map Record Type to view
            TextView typeNdefTV = (TextView) view.findViewById(R.id.textViewNdefType);
            typeNdefTV.setText(parseTypeToString(record.getType()));
            // Map TNF info to view
            TextView tnfNdefTV = (TextView) view.findViewById(R.id.textViewNdefTnf);
            tnfNdefTV.setText(parseTnfToString(record.getTnf()));
            // Map Payload to view
            TextView payloadNdefTV = (TextView) view.findViewById(R.id.textViewNdefPayload);
            payloadNdefTV.setText(new String(record.getPayload()));
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.SECTION_INFO);
    }

    private NdefMessage getFirstNdefMessage() {
        return mNdef[0];
    }

    private Boolean hasNdefMessage() {
        return !(mNdef == null);
    }

    /**
     * Convert TNF NDEF record value to human readable string
     *
     * @param tnf TNF number from NdefRecord
     * @return Parsed string
     */
    private String parseTnfToString(short tnf) {
        String tnfString = getString(R.string.not_available);

        switch (tnf) {
            case NdefRecord.TNF_ABSOLUTE_URI:
                tnfString = "TNF_ABSOLUTE_URI";
                break;
            case NdefRecord.TNF_EMPTY:
                tnfString = "TNF_EMPTY";
                break;
            case NdefRecord.TNF_EXTERNAL_TYPE:
                tnfString = "TNF_EXTERNAL_TYPE";
                break;
            case NdefRecord.TNF_MIME_MEDIA:
                tnfString = "TNF_MIME_MEDIA";
                break;
            case NdefRecord.TNF_UNCHANGED:
                tnfString = "TNF_UNCHANGED";
                break;
            case NdefRecord.TNF_UNKNOWN:
                tnfString = "TNF_UNKNOWN";
                break;
            case NdefRecord.TNF_WELL_KNOWN:
                tnfString = "TNF_WELL_KNOWN";
                break;
        }

        return tnfString;
    }

    /**
     * Convert Type NDEF record value to human readable string
     *
     * @param type Type sequence from NdefRecord
     * @return Parsed string
     */
    private String parseTypeToString(byte[] type) {
        String typeString = "N/A";

        if (Arrays.equals(type, NdefRecord.RTD_URI)) {
            typeString = "RTD_URI";
        } else if (Arrays.equals(type, NdefRecord.RTD_ALTERNATIVE_CARRIER)) {
            typeString = "RTD_ALTERNATIVE_CARRIER";
        } else if (Arrays.equals(type, NdefRecord.RTD_HANDOVER_CARRIER)) {
            typeString = "RTD_HANDOVER_CARRIER";
        } else if (Arrays.equals(type, NdefRecord.RTD_HANDOVER_REQUEST)) {
            typeString = "RTD_HANDOVER_REQUEST";
        } else if (Arrays.equals(type, NdefRecord.RTD_HANDOVER_SELECT)) {
            typeString = "RTD_HANDOVER_SELECT";
        } else if (Arrays.equals(type, NdefRecord.RTD_SMART_POSTER)) {
            typeString = "RTD_SMART_POSTER";
        } else if (Arrays.equals(type, NdefRecord.RTD_TEXT)) {
            typeString = "RTD_TEXT";
        }

        return typeString;
    }
}

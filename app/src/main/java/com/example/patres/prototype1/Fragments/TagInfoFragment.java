package com.example.patres.prototype1.fragments;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.patres.prototype1.activities.MainActivity;
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
        // Tag ID
        TextView idTagTV = (TextView) view.findViewById(R.id.textViewTagId);
        idTagTV.setText(parseIdToHexString(mTag.getId()));
        // Tag Technology
        TextView techTagTV = (TextView) view.findViewById(R.id.textViewTagTech);
        techTagTV.setText(parseTechnologiesToString(mTag.getTechList()));

        // Map NDEF Info to view
        // TODO: Way of displaying multiple NDEF messages
        if (hasNdefMessage()) {
            NdefMessage ndef = getFirstNdefMessage();
            // This is safe as parsed NDEF always has at least one record
            NdefRecord record = ndef.getRecords()[0];

            // NDEF ID
            TextView idNdefTV = (TextView) view.findViewById(R.id.textViewNdefId);
            idNdefTV.setText(parseIdToHexString(record.getId()));
            // NDEF Type
            TextView typeNdefTV = (TextView) view.findViewById(R.id.textViewNdefType);
            typeNdefTV.setText(parseNdefTypeToString(record.getType()));
            // NDEF TNF
            TextView tnfNdefTV = (TextView) view.findViewById(R.id.textViewNdefTnf);
            tnfNdefTV.setText(parseTnfToString(record.getTnf()));
            // NDEF Payload
            TextView payloadNdefTV = (TextView) view.findViewById(R.id.textViewNdefPayload);
            payloadNdefTV.setText(new String(record.getPayload()));
        }
        else
        {
            // Disable NDEF layout as there is no NDEF message
            RelativeLayout NdefLayout = (RelativeLayout) view.findViewById(R.id.relativeNdefLayout);
            NdefLayout.setVisibility(View.INVISIBLE);
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
     * Convert NDEF TNF record value to human readable string
     *
     * @param tnf TNF number from NdefRecord
     * @return Parsed string
     */
    private String parseTnfToString(short tnf) {
        String tnfString = getString(R.string.not_available);

        switch (tnf) {
            case NdefRecord.TNF_ABSOLUTE_URI:
                tnfString = "URI";
                break;
            case NdefRecord.TNF_EMPTY:
                tnfString = "Empty";
                break;
            case NdefRecord.TNF_EXTERNAL_TYPE:
                tnfString = "External Type";
                break;
            case NdefRecord.TNF_MIME_MEDIA:
                tnfString = "MIME Media";
                break;
            case NdefRecord.TNF_UNCHANGED:
                tnfString = "Unchanged";
                break;
            case NdefRecord.TNF_UNKNOWN:
                tnfString = "Unknown";
                break;
            case NdefRecord.TNF_WELL_KNOWN:
                tnfString = "Well Known";
                break;
        }

        return tnfString;
    }

    /**
     * Convert NDEF Type record value to human readable string
     *
     * @param type Type sequence from NdefRecord
     * @return Parsed string
     */
    private String parseNdefTypeToString(byte[] type) {
        String typeString = getString(R.string.not_available);

        if (Arrays.equals(type, NdefRecord.RTD_URI)) {
            typeString = "URI";
        } else if (Arrays.equals(type, NdefRecord.RTD_ALTERNATIVE_CARRIER)) {
            typeString = "Alternative Carrier";
        } else if (Arrays.equals(type, NdefRecord.RTD_HANDOVER_CARRIER)) {
            typeString = "Handover Carrier";
        } else if (Arrays.equals(type, NdefRecord.RTD_HANDOVER_REQUEST)) {
            typeString = "Handover Request";
        } else if (Arrays.equals(type, NdefRecord.RTD_HANDOVER_SELECT)) {
            typeString = "Handover Select";
        } else if (Arrays.equals(type, NdefRecord.RTD_SMART_POSTER)) {
            typeString = "Smart Poster";
        } else if (Arrays.equals(type, NdefRecord.RTD_TEXT)) {
            typeString = "Text";
        }

        return typeString;
    }

    /**
     * Parse clean technologies from Tag (without packages prefix)
     *
     * @param techs Technologies returned from NFC Tag
     * @return Parsed string
     */
    private String parseTechnologiesToString(String[] techs) {
        StringBuilder techString = new StringBuilder();

        for (String tech : techs) {
            if (techString.length() != 0) {
                techString.append(", ");
            }
            // Input: android.nfc.tech.NfcA
            // Output: NfcA
            techString.append(tech.substring(17));
        }

        return techString.toString();
    }

    /**
     * Parse ID from tag in Hexadecimal string format
     *
     * @param id ID (UID) from NFC Tag (or NDEF)
     * @return Parsed HEX string
     */
    private String parseIdToHexString(byte[] id) {
        if (id != null && id.length > 0) {
            StringBuilder idHexString = new StringBuilder();
            String format;

            for(int i=0; i<id.length; i++) {
                if (i != id.length-1) {
                    format = "%02X:";
                } else {
                    format = "%02X";
                }
                idHexString.append(String.format(format, id[i]));
            }

            return idHexString.toString();
        }

        return getString(R.string.not_available);
    }
}

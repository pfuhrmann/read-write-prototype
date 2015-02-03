package com.example.patres.prototype1.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.nfc.NdefRecord;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.patres.prototype1.Activities.MainActivity;
import com.example.patres.prototype1.R;

import java.nio.charset.Charset;
import java.util.Locale;

public class WriteTextFragment extends Fragment
        implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_write_text, container, false);
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
        EditText text = (EditText) v.findViewById(R.id.editText);
        NdefRecord record = createTextRecord(text.getText().toString(), Locale.getDefault(), true);
        NfcDialogFragment fragment = new NfcDialogFragment();
        fragment.setNdefRecord(record);

        FragmentManager fragmentManager = getFragmentManager();
        fragment.show(fragmentManager, "dialog_fragment");
    }

    public NdefRecord createTextRecord(String payload, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = payload.getBytes(utfEncoding);
        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], data);

        return record;
    }
}

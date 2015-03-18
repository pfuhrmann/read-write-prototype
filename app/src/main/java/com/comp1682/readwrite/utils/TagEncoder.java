package com.comp1682.readwrite.utils;

import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;

import com.comp1682.readwrite.R;
import com.comp1682.readwrite.App;
import com.comp1682.readwrite.models.EncodeResult;

import java.io.IOException;

public class TagEncoder {

    /**
     * Write NDEF message to the Tag
     *
     * @param message NDEF formatted message
     * @param tag Discovered NFC tag
     * @return Response class
     */
    public EncodeResult writeTag(NdefMessage message, Tag tag) {
        String mess;
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                // Initiate connection between tag
                ndef.connect();
                if (!ndef.isWritable()) {
                    return new EncodeResult(false, App.getStr(R.string.tag_read_only));
                }

                // Check tag's capacity against message size
                int capacity = ndef.getMaxSize();
                int size = message.toByteArray().length;
                if (capacity < size) {
                    mess = App.getStr(R.string.tag_insufficient_capacity,
                            capacity, size);
                    return new EncodeResult(false, mess);
                }

                // Write now
                ndef.writeNdefMessage(message);
                return new EncodeResult(true, App.getStr(R.string.tag_encoded));
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        // Initiate connection between tag
                        format.connect();
                        // Format tag to NDEF
                        format.format(message);
                        return new EncodeResult(true,
                                App.getStr(R.string.tag_encoded_formatted));
                    } catch (IOException e) {
                        return new EncodeResult(false, App.getStr(R.string.tag_format_failed));
                    }
                } else {
                    return new EncodeResult(false, App.getStr(R.string.tag_no_ndef));
                }
            }
        } catch (Exception e) {
            return new EncodeResult(false, App.getStr(R.string.tag_encoding_failed));
        }
    }
}

package com.example.patres.prototype1.utils;

import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;

import com.example.patres.prototype1.R;
import com.example.patres.prototype1.App;

import java.io.IOException;

public class TagWriter {

    /**
     * Write NDEF message to the Tag
     *
     * @param message NDEF formatted message
     * @param tag Discovered NFC tag
     * @return Response class
     */
    public WriteResponse writeTag(NdefMessage message, Tag tag) {
        String mess;
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                // Initiate connection between tag
                ndef.connect();
                if (!ndef.isWritable()) {

                    return new WriteResponse(0, App.getStr(R.string.tag_read_only));
                }

                // Check tag's capacity against message size
                int capacity = ndef.getMaxSize();
                int size = message.toByteArray().length;
                if (capacity < size) {
                    mess = App.getStr(R.string.tag_insufficient_capacity,
                            capacity, size);

                    return new WriteResponse(0, mess);
                }

                // Write now
                ndef.writeNdefMessage(message);

                return new WriteResponse(1, App.getStr(R.string.tag_encoded));
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        // Initiate connection between tag
                        format.connect();
                        // Format tag to NDEF
                        format.format(message);

                        return new WriteResponse(1,
                                App.getStr(R.string.tag_encoded_formatted));
                    } catch (IOException e) {
                        return new WriteResponse(0, App.getStr(R.string.tag_format_failed));
                    }
                } else {
                    return new WriteResponse(0, App.getStr(R.string.tag_no_ndef));
                }
            }
        } catch (Exception e) {
            return new WriteResponse(0, App.getStr(R.string.tag_encoding_failed));
        }
    }

    /**
     * Immutable response class
     */
    public class WriteResponse {
        int mStatus;
        String mMessage;

        WriteResponse(int status, String message) {
            mStatus = status;
            mMessage = message;
        }

        public int getStatus() {
            return mStatus;
        }

        public String getMessage() {
            return mMessage;
        }
    }
}

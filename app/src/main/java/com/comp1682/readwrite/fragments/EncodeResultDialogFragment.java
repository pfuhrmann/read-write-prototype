package com.comp1682.readwrite.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.comp1682.readwrite.App;
import com.comp1682.readwrite.R;
import com.comp1682.readwrite.models.EncodeResult;

public class EncodeResultDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Setup custom dialog layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_encode_result, null);
        TextView mMessage = (TextView) view.findViewById(R.id.textView8);
        ImageView mIcon = (ImageView) view.findViewById(R.id.imageView2);

        // Map result data to layout
        EncodeResult mResult = getArguments().getParcelable("result");
        mIcon.setImageDrawable(mResult.getStatus() ?
                App.getDraw(R.drawable.success_icon) :
                App.getDraw(R.drawable.failure_icon));
        mMessage.setText(mResult.getMessage());

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing just close modal
                    }
                }).create();
    }
}

package com.example.patres.prototype1.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patres.prototype1.App;
import com.example.patres.prototype1.R;
import com.example.patres.prototype1.utils.TagEncoder;

public class EncodeResultDialogFragment extends DialogFragment {

    /**
     * Response from encoding process
     */
    private TagEncoder.EncodeResult mResult;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_encode_result, null);

        TextView mMessage = (TextView) view.findViewById(R.id.textView8);
        ImageView mIcon = (ImageView) view.findViewById(R.id.imageView2);

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

    /**
     *
     * @param response Response model from encoding process
     */
    public void setResult(TagEncoder.EncodeResult response) {
        this.mResult = response;
    }
}

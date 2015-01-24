package com.example.patres.prototype1.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.patres.prototype1.MainActivity;
import com.example.patres.prototype1.R;

public class WriteTextFragment extends NfcAwareFragment
        implements View.OnClickListener {

    private Button btn;

    public WriteTextFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_text, container, false);
        btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.SECTION_WRITE);
    }

    public void onClick(View v) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Message")
                .setTitle("Title");

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        Toast.makeText(this.getActivity(), "Text!", Toast.LENGTH_SHORT).show();
    }
}

package com.example.patres.prototype1.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.patres.prototype1.MainActivity;
import com.example.patres.prototype1.R;

public class WriteTextFragment extends Fragment
        implements View.OnClickListener {

    public WriteTextFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        FragmentManager fragmentManager = getFragmentManager();
        NfcDialogFragment fragment = new NfcDialogFragment();
        fragment.show(fragmentManager, "dialog_fragment");
    }
}

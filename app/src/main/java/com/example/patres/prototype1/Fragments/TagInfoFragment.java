package com.example.patres.prototype1.Fragments;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.patres.prototype1.MainActivity;
import com.example.patres.prototype1.R;

public class TagInfoFragment extends NFCAwareFragment {

    private Tag tag;

    public TagInfoFragment() {
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tag_info, container, false);
        TextView data = (TextView) rootView.findViewById(R.id.textView4);
        data.setText(tag.getTechList().toString());
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.SECTION_INFO);
    }
}

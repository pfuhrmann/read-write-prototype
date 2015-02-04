package com.example.patres.prototype1.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.patres.prototype1.Adapters.NavigationListViewAdapter;
import com.example.patres.prototype1.Activities.MainActivity;
import com.example.patres.prototype1.Models.Data.WriteTagListItemsData;
import com.example.patres.prototype1.Models.NavigationListViewItem;
import com.example.patres.prototype1.R;

import java.util.List;

public class WriteTagFragment extends Fragment
        implements AdapterView.OnItemClickListener {

    // Navigation list section mappings
    public static final int WRITE_TEXT = 0;
    public static final int WRITE_URI = 1;
    public static final int WRITE_EMAIL = 2;

    private List<NavigationListViewItem> mItems;

    public WriteTagFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.SECTION_WRITE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);

        // Setup List view
        ListView list = (ListView) view.findViewById(R.id.list);
        mItems = new WriteTagListItemsData();
        list.setAdapter(new NavigationListViewAdapter(getActivity(), mItems));
        list.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = new Fragment();

        switch (position) {
            case WriteTagFragment.WRITE_TEXT:
                fragment = new WriteTextFragment();
                break;
            case WriteTagFragment.WRITE_URI:
                fragment = new WriteUriFragment();
                break;
            case WriteTagFragment.WRITE_EMAIL:
                fragment = new WriteEmailFragment();
                break;
        }

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}

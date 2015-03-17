package com.comp1682.readwrite.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.comp1682.readwrite.adapters.NavigationListViewAdapter;
import com.comp1682.readwrite.activities.MainActivity;
import com.comp1682.readwrite.App;
import com.comp1682.readwrite.models.data.WriteTagListItemsData;
import com.comp1682.readwrite.models.NavigationListViewItem;
import com.comp1682.readwrite.R;

import java.util.List;

public class WriteActionFragment extends Fragment
        implements AdapterView.OnItemClickListener {

    // Navigation list section mappings
    private static final int WRITE_TEXT = 0;
    private static final int WRITE_URI = 1;
    private static final int WRITE_EMAIL = 2;

    public WriteActionFragment() {
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
        List<NavigationListViewItem> mItems = new WriteTagListItemsData();
        list.setAdapter(new NavigationListViewAdapter(getActivity(), mItems));
        list.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = new Fragment();

        switch (position) {
            case WriteActionFragment.WRITE_TEXT:
                fragment = new WriteTextFragment();
                break;
            case WriteActionFragment.WRITE_URI:
                fragment = new WriteUriFragment();
                break;
            case WriteActionFragment.WRITE_EMAIL:
                fragment = new WriteEmailFragment();
                break;
        }

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();

        // Set home button as up action
        MainActivity activity = (MainActivity) getActivity();
        activity.getNavigationDrawerFragment().getDrawerToggle().setDrawerIndicatorEnabled(false);
        ActionBar actionBar = activity.getActionBar();
        if (null != actionBar) {
            activity.getActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            Log.e(App.getTag(), "Action bar is null");
        }
    }
}

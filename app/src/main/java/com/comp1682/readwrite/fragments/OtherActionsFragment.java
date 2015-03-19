package com.comp1682.readwrite.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.comp1682.readwrite.adapters.NavigationListViewAdapter;
import com.comp1682.readwrite.activities.MainActivity;
import com.comp1682.readwrite.models.data.OtherActionsListItemsData;
import com.comp1682.readwrite.models.NavigationListViewItem;
import com.comp1682.readwrite.R;

import java.util.List;

public class OtherActionsFragment extends Fragment
        implements AdapterView.OnItemClickListener {

    // Navigation list section mappings
    private static final int COPY = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.SECTION_OTHER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);

        // Setup List view
        ListView list = (ListView) view.findViewById(R.id.list);
        List<NavigationListViewItem> mItems = new OtherActionsListItemsData();
        list.setAdapter(new NavigationListViewAdapter(getActivity(), mItems));
        list.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case OtherActionsFragment.COPY:
                // Show Copy dialog
                CopyDialogFragment fragment = CopyDialogFragment.newInstanceStep1();
                FragmentManager fragmentManager = getFragmentManager();
                fragment.show(fragmentManager, "copy_fragment");
                break;
        }
    }
}

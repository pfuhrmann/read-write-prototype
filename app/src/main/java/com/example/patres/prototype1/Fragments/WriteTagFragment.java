package com.example.patres.prototype1.Fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.patres.prototype1.Adapters.NavigationListViewAdapter;
import com.example.patres.prototype1.MainActivity;
import com.example.patres.prototype1.Models.Data.WriteTagListItemsData;
import com.example.patres.prototype1.Models.NavigationListViewItem;

import java.util.List;

public class WriteTagFragment extends ListFragment {

    private List<NavigationListViewItem> mItems;

    public WriteTagFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.SECTION_WRITE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the items list
        mItems = new WriteTagListItemsData();

        // initialize and set the list adapter
        setListAdapter(new NavigationListViewAdapter(getActivity(), mItems));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        NavigationListViewItem item = mItems.get(position);

        // do something
        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
    }
}

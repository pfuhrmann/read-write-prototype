package com.example.patres.prototype1.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.patres.prototype1.Adapters.NavigationListViewAdapter;
import com.example.patres.prototype1.MainActivity;
import com.example.patres.prototype1.Models.Data.WriteTagListItemsData;
import com.example.patres.prototype1.Models.NavigationListViewItem;
import com.example.patres.prototype1.R;

import java.util.List;

public class WriteTagFragment extends NfcAwareFragment
        implements AdapterView.OnItemClickListener {

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
        ListView list1 = (ListView) view.findViewById(R.id.list);
        mItems = new WriteTagListItemsData();
        list1.setAdapter(new NavigationListViewAdapter(getActivity(), mItems));
        list1.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // retrieve theListView item
        NavigationListViewItem item = mItems.get(position);

        // do something
        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
    }
}

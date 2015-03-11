package com.example.patres.prototype1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.patres.prototype1.models.NavigationListViewItem;
import com.example.patres.prototype1.R;

import java.util.List;

public class NavigationListViewAdapter extends ArrayAdapter<NavigationListViewItem> {

    public NavigationListViewAdapter(Context context, List<NavigationListViewItem> items) {
        super(context, R.layout.navigation_listview_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.navigation_listview_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        NavigationListViewItem item = getItem(position);
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvDescription.setText(item.getDescription());

        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     * http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    private static class ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
    }
}

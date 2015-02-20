package com.example.patres.prototype1.Models.Data;

import com.example.patres.prototype1.App;
import com.example.patres.prototype1.Models.NavigationListViewItem;
import com.example.patres.prototype1.R;

import java.util.ArrayList;

/**
 * Data holder used in 'Write' action list
 */
public class WriteTagListItemsData extends ArrayList<NavigationListViewItem> {

    public WriteTagListItemsData() {
        add(new NavigationListViewItem(App.getStr(R.string.navigation_write_text),
                App.getStr(R.string.navigation_write_text_desc)));
        add(new NavigationListViewItem(App.getStr(R.string.navigation_write_uri),
                App.getStr(R.string.navigation_write_uri_desc)));
        add(new NavigationListViewItem(App.getStr(R.string.navigation_write_email),
                App.getStr(R.string.navigation_write_email_desc)));
    }
}

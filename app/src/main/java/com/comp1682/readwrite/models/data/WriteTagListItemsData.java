package com.comp1682.readwrite.models.data;

import com.comp1682.readwrite.App;
import com.comp1682.readwrite.models.NavigationListViewItem;
import com.comp1682.readwrite.R;

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

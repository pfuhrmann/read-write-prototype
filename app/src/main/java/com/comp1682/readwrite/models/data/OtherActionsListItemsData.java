package com.comp1682.readwrite.models.data;

import com.comp1682.readwrite.App;
import com.comp1682.readwrite.models.NavigationListViewItem;
import com.comp1682.readwrite.R;

import java.util.ArrayList;

/**
 * Data holder used in 'Write' action list
 */
public class OtherActionsListItemsData extends ArrayList<NavigationListViewItem> {

    public OtherActionsListItemsData() {
        add(new NavigationListViewItem(App.getStr(R.string.navigation_copy_tag),
                App.getStr(R.string.navigation_copy_tag_desc)));
    }
}

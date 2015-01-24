package com.example.patres.prototype1.Models.Data;

import com.example.patres.prototype1.Models.NavigationListViewItem;

import java.util.ArrayList;

public class WriteTagListItemsData extends ArrayList<NavigationListViewItem> {

    public WriteTagListItemsData() {
        add(new NavigationListViewItem("Write Text", "Create NDEF text record"));
        add(new NavigationListViewItem("test 2", "test 2B"));
        add(new NavigationListViewItem("test 3", "test 3B"));
    }
}

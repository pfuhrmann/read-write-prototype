package com.example.patres.prototype1.Models.Data;

import android.content.res.Resources;

import com.example.patres.prototype1.Models.NavigationListViewItem;

import java.util.ArrayList;

public class WriteTagListItemsData extends ArrayList<NavigationListViewItem> {

    public WriteTagListItemsData() {
        add(new NavigationListViewItem("Text", "Create NDEF text record"));
        add(new NavigationListViewItem("URI/URL", "Create NDEF URI/URL record"));
        add(new NavigationListViewItem("Email", "Create email record"));
    }
}

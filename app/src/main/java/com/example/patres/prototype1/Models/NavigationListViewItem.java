package com.example.patres.prototype1.models;

public class NavigationListViewItem {
    private final String title;
    private final String description;

    public NavigationListViewItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}

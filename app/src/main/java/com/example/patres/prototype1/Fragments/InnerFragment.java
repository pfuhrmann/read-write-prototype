package com.example.patres.prototype1.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.patres.prototype1.Activities.MainActivity;

/**
 * Fragment which is inner in regards to navigation
 * - provides navigation up and back functionality
 */
public class InnerFragment extends Fragment
{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Navigate back
            case android.R.id.home:
                resetActionBar();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        resetActionBar();
    }

    private void resetActionBar() {
        MainActivity activity = (MainActivity) getActivity();
        activity.getNavigationDrawerFragment().getDrawerToggle().setDrawerIndicatorEnabled(true);
        getFragmentManager().popBackStackImmediate();
    }
}

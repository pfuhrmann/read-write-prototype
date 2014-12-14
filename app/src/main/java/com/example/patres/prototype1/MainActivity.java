package com.example.patres.prototype1;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.example.patres.prototype1.Fragments.NavigationDrawerFragment;
import com.example.patres.prototype1.Fragments.ReadTagFragment;
import com.example.patres.prototype1.Fragments.TagInfoFragment;
import com.example.patres.prototype1.Fragments.WriteTagFragment;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    // Navigation section mappings
    public static final int SECTION_READ = 0;
    public static final int SECTION_WRITE = 1;
    public static final int SECTION_INFO = 2;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new Fragment();

        switch (position) {
            case MainActivity.SECTION_READ:
                fragment = new ReadTagFragment();
                break;
            case MainActivity.SECTION_WRITE:
                fragment = new WriteTagFragment();
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            showTagInfo(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent =  getIntent();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            showTagInfo(intent);
        }
    }

    private void showTagInfo(Intent intent)
    {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        // Get NDEF messages
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage[] ndefMsgs = new NdefMessage[rawMsgs.length];
        for (int i = 0; i < rawMsgs.length; i++) {
            ndefMsgs[i] = (NdefMessage) rawMsgs[i];
        }

        // Display info fragment
        FragmentManager fragmentManager = getFragmentManager();
        TagInfoFragment fragmentTagInfo = new TagInfoFragment();
        fragmentTagInfo.setTag(tag).setNdef(ndefMsgs);
        fragmentTagInfo.setRetainInstance(true);
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragmentTagInfo)
                .commit();
    }

    public void onSectionAttached(int section) {
        switch (section) {
            case MainActivity.SECTION_READ:
                mTitle = getString(R.string.title_section_read);
                break;
            case MainActivity.SECTION_WRITE:
                mTitle = getString(R.string.title_section_write);
                break;
            case MainActivity.SECTION_INFO:
                mTitle = getString(R.string.title_section_tag_info);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
}

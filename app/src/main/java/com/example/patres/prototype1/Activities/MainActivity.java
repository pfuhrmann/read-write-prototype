package com.example.patres.prototype1.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.example.patres.prototype1.fragments.NavigationDrawerFragment;
import com.example.patres.prototype1.fragments.ReadActionFragment;
import com.example.patres.prototype1.fragments.TagInfoFragment;
import com.example.patres.prototype1.fragments.WriteActionFragment;
import com.example.patres.prototype1.helpers.NFCManager;
import com.example.patres.prototype1.helpers.TagWriter;
import com.example.patres.prototype1.R;

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
                fragment = new ReadActionFragment();
                break;
            case MainActivity.SECTION_WRITE:
                fragment = new WriteActionFragment();
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
        filterNfcDispatch(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent =  getIntent();
        filterNfcDispatch(intent);
    }

    /**
     * Filtering for NFC dispatches
     * - Category extra is used for diff between read and encode modes
     */
    private void filterNfcDispatch(Intent intent) {
        int category = intent.getIntExtra("category", -1);

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            if (NFCManager.CATEGORY_READ == category) {
                showTagInfo(intent);
            } else if (NFCManager.CATEGORY_ENCODE == category) {
                writeTagRecord(intent);
            }
        }
    }

    /**
     * Read info from the Tag and display it
     */
    private void showTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        TagInfoFragment fragmentTagInfo = new TagInfoFragment();

        // Get NDEF messages (if any)
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMsgs != null) {
            NdefMessage[] ndefMsgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                ndefMsgs[i] = (NdefMessage) rawMsgs[i];
            }
            fragmentTagInfo.setNdef(ndefMsgs);
        }

        // Display info fragment
        fragmentTagInfo.setTag(tag);
        fragmentTagInfo.setRetainInstance(true);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragmentTagInfo)
                .commit();
    }

    /**
     * Write NDEF message and display result of action
     */
    private void writeTagRecord(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        NdefRecord record = intent.getParcelableExtra("record");
        NdefMessage ndefMessage = new NdefMessage(record);

        // Write NDEF record
        TagWriter writer = new TagWriter();
        TagWriter.WriteResponse wr = writer.writeTag(ndefMessage, tag);
        String message = (wr.getStatus() == 1 ? "Success: " : "Failed: ") + wr.getMessage();

        Toast.makeText(this.getBaseContext(), message, Toast.LENGTH_SHORT).show();
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
        if (null != actionBar) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        } else {
            throw new AssertionError();
        }
    }

    public NavigationDrawerFragment getNavigationDrawerFragment() {
        return mNavigationDrawerFragment;
    }
}

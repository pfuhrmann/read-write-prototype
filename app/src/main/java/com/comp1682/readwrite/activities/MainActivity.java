package com.comp1682.readwrite.activities;

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
import android.util.TimingLogger;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.comp1682.readwrite.fragments.CopyDialogFragment;
import com.comp1682.readwrite.fragments.EncodeDialogFragment;
import com.comp1682.readwrite.fragments.EncodeResultDialogFragment;
import com.comp1682.readwrite.fragments.FragmentFactory;
import com.comp1682.readwrite.fragments.NavigationDrawerFragment;
import com.comp1682.readwrite.fragments.TagInfoFragment;
import com.comp1682.readwrite.models.EncodeResult;
import com.comp1682.readwrite.utils.NFCManager;
import com.comp1682.readwrite.utils.TagEncoder;
import com.comp1682.readwrite.R;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    // Navigation section mappings
    public static final int SECTION_READ = 0;
    public static final int SECTION_WRITE = 1;
    public static final int SECTION_OTHER = 2;
    public static final int SECTION_INFO = 3;

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
        String type = "";
        switch (position) {
            case MainActivity.SECTION_READ:
                type = "tag-read";
                break;
            case MainActivity.SECTION_WRITE:
                type = "tag-write";
                break;
            case MainActivity.SECTION_OTHER:
                type = "tag-other";
                break;
        }

        // Display fragment
        Fragment fragment = FragmentFactory.getFragment(type);
        FragmentManager fragmentManager = getFragmentManager();
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
            } else if (NFCManager.CATEGORY_COPY_READ == category) {
                readCopyRecord(intent);
            } else if (NFCManager.CATEGORY_COPY_ENCODE == category) {
                encodeCopyRecord(intent);
            }
        }
    }

    /**
     * Read info from the Tag and display it
     */
    private void showTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        // Display info fragment
        TagInfoFragment fragment = TagInfoFragment.newInstance(tag, rawMsgs);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    /**
     * Write NDEF message and display result of action
     */
    private void writeTagRecord(Intent intent) {
        // Write NDEF record
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        NdefRecord record = intent.getParcelableExtra("record");
        NdefMessage ndefMessage = new NdefMessage(record);
        TagEncoder writer = new TagEncoder();
        EncodeResult result = writer.encodeTag(ndefMessage, tag);

        // Dismiss Encode dialog
        Fragment dialogFragment = getFragmentManager().findFragmentByTag("encode_fragment");
        if (dialogFragment != null) {
            EncodeDialogFragment df = (EncodeDialogFragment) dialogFragment;
            df.dismiss();
        }

        // Show Result dialog
        EncodeResultDialogFragment resultFragment = EncodeResultDialogFragment.newInstance(result);
        FragmentManager fragmentManager = getFragmentManager();
        resultFragment.show(fragmentManager, "result_fragment");
    }

    /**
     * Read info from the Tag and display paste dialog
     */
    private void readCopyRecord(Intent intent) {
        TimingLogger timings = new TimingLogger("test", "read-copy");
        // Get NDEF messages (if any)
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        // Dismiss Copy dialog (step 1)
        Fragment dialogFragment = getFragmentManager().findFragmentByTag("copy_fragment");
        if (dialogFragment != null) {
            CopyDialogFragment df = (CopyDialogFragment) dialogFragment;
            df.dismiss();
        }

        // Show Copy dialog
        CopyDialogFragment fragment = CopyDialogFragment.newInstanceStep2(rawMsgs);
        FragmentManager fragmentManager = getFragmentManager();
        fragment.show(fragmentManager, "copy_fragment");
        timings.addSplit("test");
        timings.dumpToLog();
    }

    /**
     * Write copied NDEF message and display result of action
     */
    private void encodeCopyRecord(Intent intent) {
        TimingLogger timings = new TimingLogger("test", "encode-copy");
        // Write NDEF record
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra("ndef");
        NdefMessage message = (NdefMessage) rawMsgs[0];
        TagEncoder encoder = new TagEncoder();
        EncodeResult result = encoder.encodeTag(message, tag);

        // Dismiss Copy dialog (step 2)
        Fragment dialogFragment = getFragmentManager().findFragmentByTag("copy_fragment");
        if (dialogFragment != null) {
            CopyDialogFragment df = (CopyDialogFragment) dialogFragment;
            df.dismiss();
        }

        // Show Result dialog
        EncodeResultDialogFragment resultFragment = EncodeResultDialogFragment.newInstance(result);
        FragmentManager fragmentManager = getFragmentManager();
        resultFragment.show(fragmentManager, "result_fragment");
        timings.addSplit("test");
        timings.dumpToLog();
    }


    public void onSectionAttached(int section) {
        switch (section) {
            case MainActivity.SECTION_READ:
                mTitle = getString(R.string.title_section_read);
                break;
            case MainActivity.SECTION_WRITE:
                mTitle = getString(R.string.title_section_write);
                break;
            case MainActivity.SECTION_OTHER:
                mTitle = getString(R.string.title_section_other);
                break;
            case MainActivity.SECTION_INFO:
                mTitle = getString(R.string.title_section_tag_info);
                break;
        }
    }

    void restoreActionBar() {
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

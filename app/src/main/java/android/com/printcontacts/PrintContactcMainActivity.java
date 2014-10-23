package android.com.printcontacts;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import android.widget.TextView;


/**
 * This example presents the contacts saved in mobile or emulator.
 * Case not exist contacts the project show the Errors in LogCat.
 */


public class PrintContactcMainActivity extends Activity {

    private static final String CATEGORY = "printContacts";


    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
    //  setContentView(R.layout.activity_print_contactc_main);

        TextView textView = new TextView(this);
        textView.setText("Verify LogCat");
        setContentView(textView);

        Uri contacts = ContactsContract.Contacts.CONTENT_URI;
        Log.i(CATEGORY, "Uri: " + contacts);
        Cursor cursor = getContentResolver().query(contacts, null, null, null, null);
        printContacts(cursor);
    }

    private void printContacts(Cursor cursor) {

        Log.i(CATEGORY, "Login the names of contacts... ");
        int count = cursor.getCount();
        Log.i(CATEGORY, "Found " + count + " contacts");
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.i(CATEGORY,"Name:" + name);

        }
        cursor.close();

    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.print_contactc_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}

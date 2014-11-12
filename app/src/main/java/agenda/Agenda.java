package agenda;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.Contacts;

/**
 * Created by VictorSampaio on 23/10/2014.
 */
public class Agenda {
    // content://com.android.contacts/contacts

    private static final Uri URI = Contacts.CONTENT_URI;

        public List<Contact> getContacts (Context context){
            List<Contact> contacts = new ArrayList<Contact>();

            // Recover the Cursor for list of contacts
            Cursor c = context.getContentResolver().query(URI, null, null, null, null);
            try {
                while ( c.moveToNext()){
                    Contact a;
                    a = readContact(context, c);
                    contacts.add(a);
                }
            }finally {
                c.close();
            }
            return contacts;
    }



    /**
     * Read Contact - Id and Name
     */
    public Contact readContact(Context context, Cursor cursor){

        Contact c = new Contact();
        //Id and Name
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(Contacts._ID));
        c.id = id;
        String name = cursor.getString(cursor.getColumnIndexOrThrow(Contacts.DISPLAY_NAME));
        c.name = name;

        //Fone
        boolean temFone = "1".equals(cursor.getString(cursor.getColumnIndexOrThrow(Contacts.HAS_PHONE_NUMBER)));
        if (temFone){
            List<String> fones = loadFones(context, id);
            c.fones = fones;
        }

        //Photo
        Bitmap b = readPhoto(context, id);
        c.photo = b;

        return c;
    }

    /**
    * Search Phones
    */
    //Search Phones on Table 'ContactsContracts.CommonDataKinds.Phone'
    private List<String> loadFones(Context context, long id){

        List<String> fones = new ArrayList<String>();

        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
        try {
            while (cursor.moveToNext()){

                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String fone = cursor.getString(column);
                fones.add(fone);

            }
        }finally {
            cursor.close();
        }
        return fones;
    }


    /**
     * Read Photos
     */
    // Read photo of a contact
    private Bitmap readPhoto (Context context, long id){

        // Create Uri for 'id' provided
        Uri uri = ContentUris.withAppendedId(Contacts.CONTENT_URI, id);
        ContentResolver contentResolver = context.getContentResolver();
        InputStream in = Contacts.openContactPhotoInputStream(contentResolver, uri);
        if (in != null){
            Bitmap photo = BitmapFactory.decodeStream(in);

            return photo;
        }
        return null;
    }
}

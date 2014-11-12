package agenda;

import android.content.ContentUris;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.List;

/**
 * Created by VictorSampaio on 23/10/2014.
 */
public class Contact {

    public long id;
    public String name;
    public Bitmap photo;
    public List<String> fones;

    // Return the URI to this contact; example: "content://com.android.contacts/contacts/1"

    public Uri getUri(){
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        return uri;
    }

}

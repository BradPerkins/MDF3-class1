package com.example.bradperkins.project3.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bradperkins.project3.DataUtilites.Contact;
import com.example.bradperkins.project3.R;

import java.util.ArrayList;

/**
 * Created by bradperkins on 12/10/15.
 */
public class WidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final int ID_CONSTANT = 0x0101010;

    private ArrayList<Contact> mContacts;
    private Context mContext;

    public WidgetViewFactory(Context context) {
        mContext = context;
        mContacts = new ArrayList<Contact>();
    }

    @Override
    public void onCreate() {
//
//        Intent intent = Intent.makeRestartActivityTask(ComponentName.unflattenFromString(ContactProvider.CONTENT_ITEM_TYPE));

//        Uri uri = intent.getParcelableExtra(ContactProvider.CONTENT_ITEM_TYPE);

//        Cursor cursor = query(uri, DBHelper.ALL_COLUMNS, dbFilter, null, null);
//        cursor.moveToFirst();

//        String[] name = {DBHelper.NAME_NAME};
//        String[] phone = {DBHelper.NAME_PHONE};
//        String[] email = {DBHelper.NAME_EMAIL};
//
        //Get Data
        String[] name = {"name1", "name2", "name3"};
        String[] phone = {"1", "2", "3"};
        String[] email = {"A", "B", "C"};

        for(int i = 0; i < 3 ; i++) {
            mContacts.add(new Contact(name[i], phone[i], email[i]));
        }

    }

    @Override
    public void onDataSetChanged() {



    }

    @Override
    public void onDestroy() {
        mContacts.clear();
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Contact contact = mContacts.get(position);

        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.contact_row);

        itemView.setTextViewText(R.id.contact_name, contact.getContactName());

        Intent intent = new Intent();
        intent.putExtra(WidgetProvider.EXTRA_ITEM, contact);
        itemView.setOnClickFillInIntent(R.id.contact_row, intent);

        return itemView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

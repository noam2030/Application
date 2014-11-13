package com.android.application.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.application.R;
import com.android.application.adapter.AcountContactsAdapter;
import com.android.application.model.AccountContact;

import java.util.ArrayList;

/**
 * Created by noamm on 11/13/2014.
 */
public class AccountContactsFragment extends Fragment {

    private ListView listView;
    private ArrayList<AccountContact> accountContacts;
    private AcountContactsAdapter acountContactsAdapter;

    public AccountContactsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        acountContactsAdapter = new AcountContactsAdapter(getActivity());
        listView.setAdapter(acountContactsAdapter);
        accountContacts = getContacts();
        acountContactsAdapter.setList(accountContacts);
    }

    private ArrayList<AccountContact> getContacts() {
        ArrayList<AccountContact> contacts = new ArrayList<AccountContact>();
        Cursor contactCursor = null;
        try {
            contactCursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (contactCursor.getCount() > 0) {
                while (contactCursor.moveToNext()) {
                    String contactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    if (phones.moveToFirst()) {
                        String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        AccountContact accountContact = new AccountContact();
                        accountContact.name = name;
                        accountContact.phoneNumber = phoneNumber;
                        accountContact.isSelected = false;
                        getContactAccount(accountContact,contactId);
                        contacts.add(accountContact);
                    }
                }
            }
        } catch (Exception e) {
            if (contactCursor != null) {
                contactCursor.close();
            }
        }
        return contacts;
    }

    public void getContactAccount(AccountContact accountContact, String id) {

        Cursor cursor = null;
        try {
            cursor = getActivity().getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI,
                    new String[]{ContactsContract.RawContacts.ACCOUNT_NAME, ContactsContract.RawContacts.ACCOUNT_TYPE},
                    ContactsContract.RawContacts.CONTACT_ID + "=?",
                    new String[]{String.valueOf(id)},
                    null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                accountContact.acountName = (cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME)));
                accountContact.acountType = (cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE)));
                cursor.close();
            }
        } catch (Exception e) {

        } finally {
            cursor.close();
        }

    }


}
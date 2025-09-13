package com.example.recycleview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contactList;
    private SearchView searchView;
    private FloatingActionButton fabAdd;

    private static final int ADD_CONTACT_REQUEST = 1;
    private static final int CONTACT_DETAIL_REQUEST = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_contacts);
        searchView = findViewById(R.id.search_view);
        fabAdd = findViewById(R.id.fab_add);

        contactList = new ArrayList<>();
        initSampleContacts();

        contactAdapter = new ContactAdapter(this, contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactAdapter);

        contactAdapter.setOnContactClickListener(position -> {
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
            Contact contact = contactList.get(position);
            intent.putExtra("CONTACT_NAME", contact.getName());
            intent.putExtra("CONTACT_PHONE", contact.getPhone());
            intent.putExtra("CONTACT_IMAGE", contact.getImageUrl());
            intent.putExtra("CONTACT_LOCATION", contact.getLocation());
            intent.putExtra("CONTACT_POSITION", position);
            startActivityForResult(intent, CONTACT_DETAIL_REQUEST);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.filter(newText);
                return false;
            }
        });

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
            startActivityForResult(intent, ADD_CONTACT_REQUEST);
        });
    }

    private void initSampleContacts() {
        contactList.add(new Contact("Kiara", "+6285337654371", "", "Jakarta, Indonesia"));
        contactList.add(new Contact("Intar", "+6285337654371", "", "Jakarta, Indonesia"));
        contactList.add(new Contact("Keisya", "+6285337654371", "", "Jakarta, Indonesia"));
        contactList.add(new Contact("Titi", "+6285337654371", "", "Jakarta, Indonesia"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("CONTACT_NAME");
            String phone = data.getStringExtra("CONTACT_PHONE");
            String imageUrl = data.getStringExtra("CONTACT_IMAGE");
            String location = data.getStringExtra("CONTACT_LOCATION");

            Contact newContact = new Contact(name, phone, imageUrl, location);
            contactAdapter.addContact(newContact);
            recyclerView.scrollToPosition(contactList.size() - 1);
        } else if (requestCode == CONTACT_DETAIL_REQUEST && resultCode == RESULT_OK && data != null) {
            int position = data.getIntExtra("CONTACT_POSITION", -1);
            if (position != -1) {
                contactAdapter.removeContact(position);
            }
        }
    }
}
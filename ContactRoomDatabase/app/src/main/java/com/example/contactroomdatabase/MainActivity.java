package com.example.contactroomdatabase;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ContactAdapter adapter;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "contact_db").allowMainThreadQueries().build();

        Contact c = new Contact();
        c.name = "John Doe";
        c.phone = "08123456789";
        db.contactDao().insert(c);

        List<Contact> contacts = db.contactDao().getAllContacts();
        adapter = new ContactAdapter(contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}

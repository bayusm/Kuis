package com.example.kuis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kuis.database.Contact;
import com.example.kuis.database.ContactDatabase;
import com.example.kuis.recycleview.ContactAdapter;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDefaultSharedPrefValue();
        if (!isLogin()) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            finishAffinity();
            startActivity(i);
            return;
        }

        findViewById(R.id.btn_add_contact).setOnClickListener(this::onClickAddContact);
        findViewById(R.id.btn_logout).setOnClickListener(this::onClickLogout);

        Executors.newSingleThreadExecutor().execute(this::setContact);
    }

    void setDefaultSharedPrefValue() {
        if (SharedPrefManager.getSavedString(this, SharedPrefManager.LOGIN_USERNAME) == null) {
            SharedPrefManager.saveString(this, SharedPrefManager.LOGIN_USERNAME, "bayu");
            SharedPrefManager.saveString(this, SharedPrefManager.LOGIN_PASSWORD, "4386");
        }
    }

    boolean isLogin() {
        return SharedPrefManager.getSavedString(this, SharedPrefManager.LOGIN_SUCCESS) != null;
    }

    void setContact() {
        ContactDatabase contactDatabase = Room.databaseBuilder(getApplicationContext(),
                ContactDatabase.class, "database-contact").build();

        List<Contact> contacts = contactDatabase.contactDao().getAll();

        ContactAdapter contactAdapter = new ContactAdapter(contacts);

        RecyclerView recyclerView = findViewById(R.id.rv_contact);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(contactAdapter);
    }

    public void onClickAddContact(View view) {
        Intent i = new Intent(MainActivity.this, AddContactActivity.class);
        startActivity(i);
    }

    public void onClickLogout(View view) {
        SharedPrefManager.deleteValue(this, SharedPrefManager.LOGIN_SUCCESS);
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        finishAffinity();
        startActivity(i);
    }
}
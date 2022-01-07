package com.example.kuis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuis.database.Contact;
import com.example.kuis.database.ContactDatabase;

import java.util.concurrent.Executors;

public class AddContactActivity extends AppCompatActivity {

    EditText etName, etPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etName = findViewById(R.id.et_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);

        findViewById(R.id.btn_add_to_contact).setOnClickListener(this::onClickAddToContact);
        findViewById(R.id.btn_back).setOnClickListener(this::onClickBack);
    }

    void insertContact() {
        final String name = etName.getText().toString();
        final String phoneNumber = etPhoneNumber.getText().toString();
        Contact contact = new Contact();
        contact.name = name;
        contact.phoneNumber = phoneNumber;
        ContactDatabase contactDatabase = Room.databaseBuilder(getApplicationContext(),
                ContactDatabase.class, "database-contact").build();

        contactDatabase.contactDao().insert(contact);
    }

    public void onClickAddToContact(View view) {
        final String name = etName.getText().toString();
        final String phoneNumber = etPhoneNumber.getText().toString();
        if (name.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Nama dan No HP tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        Executors.newSingleThreadExecutor().execute(this::insertContact);
        onClickBack(view);
    }

    public void onClickBack(View view) {
        Intent i = new Intent(AddContactActivity.this, MainActivity.class);
        startActivity(i);
    }

}
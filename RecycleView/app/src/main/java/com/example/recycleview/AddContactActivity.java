package com.example.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    private EditText etName, etPhone, etLocation;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etLocation = findViewById(R.id.et_location);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(v -> {
            saveContact();
        });

        btnCancel.setOnClickListener(v -> {
            finish();
        });
    }

    private void saveContact() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Nama tidak boleh kosong");
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError("Nomor telepon tidak boleh kosong");
            return;
        } else if (phone.length() < 10) {
            etPhone.setError("Nomor telepon tidak valid");
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("CONTACT_NAME", name);
        resultIntent.putExtra("CONTACT_PHONE", phone);
        resultIntent.putExtra("CONTACT_IMAGE"," ");
        resultIntent.putExtra("CONTACT_LOCATION", location);

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}

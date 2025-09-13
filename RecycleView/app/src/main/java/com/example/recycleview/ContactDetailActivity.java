import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ContactDetailActivity extends AppCompatActivity {

    private ImageView imgProfile;
    private TextView tvName, tvPhone, tvLocation;
    private ImageButton btnCall, btnMessage, btnVideoCall, btnBlock;
    private Button btnTelegram, btnWhatsapp, btnShareContact, btnDelete;

    private String name, phone, imageUrl, location;
    private int contactPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        imgProfile = findViewById(R.id.img_profile_detail);
        tvName = findViewById(R.id.tv_name_detail);
        tvPhone = findViewById(R.id.tv_phone_detail);
        tvLocation = findViewById(R.id.tv_location);
        btnCall = findViewById(R.id.btn_call);
        btnMessage = findViewById(R.id.btn_message);
        btnTelegram = findViewById(R.id.btn_telegram);
        btnWhatsapp = findViewById(R.id.btn_whatsapp);
        btnShareContact = findViewById(R.id.btn_share_contact);
        btnDelete = findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        name = intent.getStringExtra("CONTACT_NAME");
        phone = intent.getStringExtra("CONTACT_PHONE");
        imageUrl = intent.getStringExtra("CONTACT_IMAGE");
        location = intent.getStringExtra("CONTACT_LOCATION");
        contactPosition = intent.getIntExtra("CONTACT_POSITION", -1);

        tvName.setText(name);
        tvPhone.setText(phone);
        tvLocation.setText(location);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.profile)
                    .into(imgProfile);
        } else {
            imgProfile.setImageResource(R.drawable.profile);
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        });

        btnMessage.setOnClickListener(v -> {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            smsIntent.setData(Uri.parse("smsto:" + phone));
            startActivity(smsIntent);
        });

        btnVideoCall.setOnClickListener(v -> {
            Toast.makeText(this, "Fitur Video Call belum tersedia", Toast.LENGTH_SHORT).show();
        });

        btnTelegram.setOnClickListener(v -> {
            Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
            telegramIntent.setData(Uri.parse("https://t.me/" + phone));
            startActivity(telegramIntent);
        });

        btnWhatsapp.setOnClickListener(v -> {
            Intent waIntent = new Intent(Intent.ACTION_VIEW);
            waIntent.setData(Uri.parse("https://wa.me/" + phone.replace("+", "")));
            startActivity(waIntent);
        });

        btnShareContact.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, name + " - " + phone);
            startActivity(Intent.createChooser(shareIntent, "Bagikan kontak dengan"));
        });

        btnDelete.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Kontak")
                .setMessage("Apakah kamu yakin ingin menghapus kontak ini?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("CONTACT_POSITION", contactPosition);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}
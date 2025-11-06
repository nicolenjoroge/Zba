package com.example.zba.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zba.data.PasswordRepository;
import com.example.zba.databinding.ActivityAppDetailsBinding;

public class AppDetailsActivity extends AppCompatActivity {

    private ActivityAppDetailsBinding appDetailsActivityBinding;
    private boolean isPasswordVisible = false;


    private String id;
    private String appName;
    private String password;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appDetailsActivityBinding = ActivityAppDetailsBinding.inflate(getLayoutInflater());
        setContentView(appDetailsActivityBinding.getRoot());

//        recieve data from the intent
        appName = getIntent().getStringExtra("appName");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        id = getIntent().getStringExtra("password_id");

        if (appName == null || password == null) {
            Toast.makeText(this, "Error loading password details", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //set data to views
        appDetailsActivityBinding.tvAppName.setText(appName);
        appDetailsActivityBinding.tvAppDisplayName.setText(appName);
        appDetailsActivityBinding.tvUsername.setText(username);
        appDetailsActivityBinding.tvPassword.setText(password);

        appDetailsActivityBinding.topAppBar.setNavigationOnClickListener(v -> finish());

        appDetailsActivityBinding.tvAppName.setText(appName);
        appDetailsActivityBinding.tvAppDisplayName.setText(appName);
        appDetailsActivityBinding.tvPassword.setText(maskPassword(password));

        appDetailsActivityBinding.btnTogglePass.setOnClickListener(v -> togglePasswordVisibility());

        appDetailsActivityBinding.btnCopyPassword.setOnClickListener(v -> copyPasswordToClipboard());

        appDetailsActivityBinding.btnUpdate.setOnClickListener(v ->
                Toast.makeText(this, "Work in progress. Update functionality coming soon!", Toast.LENGTH_SHORT).show()
        );

        appDetailsActivityBinding.btnDelete.setOnClickListener(v -> {
                    PasswordRepository.removePassword(id);
                    Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
        );
    }

    // Mask password with bullets
    private String maskPassword(String password) {
        return "•".repeat(password.length());
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            appDetailsActivityBinding.tvPassword.setText(maskPassword(password));
        } else {
            appDetailsActivityBinding.tvPassword.setText(password);
        }
        isPasswordVisible = !isPasswordVisible;
    }

    // Copy password to clipboard
    private void copyPasswordToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Password", password);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appDetailsActivityBinding = null;
    }
}

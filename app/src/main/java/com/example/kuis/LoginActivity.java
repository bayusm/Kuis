package com.example.kuis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final Handler handler = new Handler();

    private EditText etUsername, etPassword;
    private ProgressBar pbLogin;
    private ImageView ivLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        pbLogin = findViewById(R.id.pbLogin);
        ivLogin = findViewById(R.id.ivLogin);

        findViewById(R.id.btnLogin).setOnClickListener(this::onClickLogin);
    }

    public void onClickLogin(View view) {
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        pbLogin.setVisibility(View.VISIBLE);
        ivLogin.setVisibility(View.GONE);

        if (username.isEmpty() || password.isEmpty()) {
            pbLogin.setVisibility(View.GONE);
            ivLogin.setVisibility(View.VISIBLE);
            Toast.makeText(LoginActivity.this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            handler.postDelayed(() -> {
                String spUsername = SharedPrefManager.getSavedString(this, SharedPrefManager.LOGIN_USERNAME);
                String spPassword = SharedPrefManager.getSavedString(this, SharedPrefManager.LOGIN_PASSWORD);

                Log.d("username", "user" + username);
                Log.d("password", "pass" + password);

                if (username.equals(spUsername) && password.equals(spPassword)) {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    SharedPrefManager.saveString(this, SharedPrefManager.LOGIN_SUCCESS, "success");
                    finishAffinity();
                    startActivity(i);
                } else {
                    pbLogin.setVisibility(View.GONE);
                    ivLogin.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Username dan password salah", Toast.LENGTH_SHORT).show();
                }
            }, 3000);
        }
    }
}
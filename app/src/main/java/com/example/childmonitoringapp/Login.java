package com.example.childmonitoringapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText etLoginEmail, etLoginPassword;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> {
            String email = etLoginEmail.getText().toString();
            String password = etLoginPassword.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(email, password);
        });

        tvRegister.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));
        tvForgotPassword.setOnClickListener(v -> startActivity(new Intent(Login.this, ForgotPasswordActivity.class)));
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    class GeoFencingActivity : AppCompatActivity() {
        private lateinit var binding: ActivityGeoFencingBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            binding = ActivityGeoFencingBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }
    }
}

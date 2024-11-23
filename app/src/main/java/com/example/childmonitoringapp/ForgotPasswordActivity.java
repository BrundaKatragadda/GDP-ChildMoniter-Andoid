package com.example.childmonitoringapp;


import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etSecurityAnswer, etNewPassword, etConfirmNewPassword;
    private Button btnResetPassword;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize UI components
        etSecurityAnswer = findViewById(R.id.etSecurityAnswer);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Set up reset password button click listener
        btnResetPassword.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        String securityAnswer = etSecurityAnswer.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmNewPassword = etConfirmNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(securityAnswer) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmNewPassword)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve user's security question answer from Firestore
        firestore.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            String storedAnswer = document.getString("securityQuestion");
                            if (storedAnswer != null && storedAnswer.equalsIgnoreCase(securityAnswer)) {
                                // Update password in Firebase Authentication
                                auth.getCurrentUser().updatePassword(newPassword)
                                        .addOnCompleteListener(passwordTask -> {
                                            if (passwordTask.isSuccessful()) {
                                                Toast.makeText(this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                                                finish(); // Close the activity
                                            } else {
                                                Toast.makeText(this, "Failed to reset password: " + passwordTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(this, "Incorrect security answer", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Failed to retrieve security question: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

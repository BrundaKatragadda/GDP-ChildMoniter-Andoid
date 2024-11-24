package com.example.childmonitoringapp;


import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail, etSecurityAnswer;
    private Button btnVerify;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.et_email);
        etSecurityAnswer = findViewById(R.id.et_security_answer);
        btnVerify = findViewById(R.id.btn_verify);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnVerify.setOnClickListener(view -> verifyEmailAndSecurityAnswer());
    }

    private void verifyEmailAndSecurityAnswer() {
        String email = etEmail.getText().toString().trim();
        String securityAnswer = etSecurityAnswer.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(securityAnswer)) {
            etSecurityAnswer.setError("Security answer is required");
            return;
        }

        // Check if the email is registered in Firebase
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        // Document found with this email
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        String storedSecurityAnswer = document.getString("securityAnswer");

                        // Check if the security answer matches
                        if (storedSecurityAnswer != null && storedSecurityAnswer.equals(securityAnswer)) {
                            sendPasswordResetEmail(email);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Incorrect security answer", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Error sending reset email", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

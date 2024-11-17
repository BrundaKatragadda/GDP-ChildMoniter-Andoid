package com.example.childmonitoringapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class register extends AppCompatActivity {
    TextInputEditText editTextEmail,editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = Firebase.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword= findViewById(R.id.password);
        buttonReg= findViewById(R.id.btn_register);

        buttonReg.setOnClickListener(new View.OnClickListerner(){
            @Override
            public void onClick(View View)
            {
                String email,password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(Register.this,"Enter Email",Toast.Length_Short).show();
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(Register.this,"Enter Password",Toast.Length_Short).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }


}
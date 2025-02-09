package com.example.childmonitoringapp;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button button; // Variable name is now button
    TextView textView;
    FirebaseUser user;


    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Find views by their IDs
        button = findViewById(R.id.logout); // Logout button assigned to button
        textView = findViewById(R.id.user_details);

        // Get the current user
        user = auth.getCurrentUser();

        // Check if the user is logged in
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        } else {
            // Display the user's email
            textView.setText(user.getEmail());
        }

        // Set up the logout button listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out the user
                FirebaseAuth.getInstance().signOut();
                // Redirect to Login activity
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }

        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        });
    }
        class ChildMainActivity : AppCompatActivity() {
            private lateinit var binding: ActivityChildMainBinding

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()
                binding = ActivityChildMainBinding.inflate(layoutInflater)
                setContentView(binding.root)
                startService(Intent(this, LocationService::class.java))
                getChildInfo()
            }

            private fun getChildInfo() {
                val db = FirebaseFirestore.getInstance()
                db.collection("user").whereEqualTo("email", PrefsHelper.getData("userEmail")).get()
                        .addOnSuccessListener { documents ->
                        binding.childName.text = "Welcome ${documents.first().getString("username")}"
                }
            .addOnFailureListener { e ->
                        e.printStackTrace()
                }
            }
        }
}

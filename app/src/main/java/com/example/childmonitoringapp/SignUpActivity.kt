package com.org.childmonitorparent.auth.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.org.childmonitorparent.R
import com.org.childmonitorparent.databinding.ActivitySignupBinding
import com.org.childmonitorparent.parent.activities.ChildLinkActivity
import com.org.childmonitorparent.parent.activities.ParentMainActivity
import com.org.childmonitorparent.auth.models.User
import com.org.childmonitorparent.auth.viewmodels.AuthViewModel
import com.org.childmonitorparent.child.activities.ChildMainActivity
import com.org.childmonitorparent.parent.activities.KidsLocationActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private var userType = "Parent"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.parentType -> {
                    userType = "Parent"
                }

                R.id.childType -> {
                    userType = "Child"
                }
            }
        }
    }

    fun onSignUpClick(view: View) {
        val username = binding.username.text.toString()
        val email = binding.email.text.toString()
        val phone = binding.phone.text.toString()
        val password = binding.password.text.toString()
        // Perform sign-up logic here
        if (username.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
            val user = User(userType, username, email, phone, password)
            val viewModel = AuthViewModel()
            viewModel.signUp(user) { success ->
                if (success) {
                    Toast.makeText(this, "Sign up successful.", Toast.LENGTH_SHORT).show()
                    if (userType == "Parent")
                        startActivity(Intent(this, ChildLinkActivity::class.java))
                    else startActivity(Intent(this, ChildMainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    fun onLoginClick(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
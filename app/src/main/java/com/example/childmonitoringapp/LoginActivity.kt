package com.org.childmonitorparent.auth.activities

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.org.childmonitorparent.R
import com.org.childmonitorparent.databinding.ActivityLoginBinding
import com.org.childmonitorparent.parent.activities.KidsLocationActivity
import com.org.childmonitorparent.auth.viewmodels.AuthViewModel
import com.org.childmonitorparent.child.activities.ChildMainActivity
import com.org.childmonitorparent.utils.PrefsHelper

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onLoginClick(view: View) {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        // Perform login logic here
        if (email.isNotEmpty() && password.isNotEmpty()) {
            val authViewModel = AuthViewModel()
            authViewModel.login(email, password) { success ->
                if (success) {
                    Toast.makeText(this, "Login successful.", Toast.LENGTH_SHORT).show()
                    if (PrefsHelper.getData("userType") == "Parent")
                        startActivity(Intent(this, KidsLocationActivity::class.java))
                    else startActivity(Intent(this, ChildMainActivity::class.java))
                    finish()
                } else Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    fun onSignUpClick(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    fun onPasswordVisibilityClick(view: View) {
        val isPasswordVisible = binding.password.transformationMethod == null
        if (isPasswordVisible) {
            // Hide password
            binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.togglePassword.setImageResource(R.drawable.ic_eye_visibility) // Change icon
        } else {
            // Show password
            binding.password.transformationMethod = null
            binding.togglePassword.setImageResource(R.drawable.ic_eye_visibility_off) // Change icon
        }
        // Move cursor to the end of the text
        binding.password.setSelection(binding.password.text?.length ?: 0)
    }
}
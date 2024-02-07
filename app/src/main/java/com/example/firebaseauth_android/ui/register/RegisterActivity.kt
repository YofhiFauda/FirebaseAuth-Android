package com.example.firebaseauth_android.ui.register

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.firebaseauth_android.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.example.firebaseauth_android.ui.login.LoginActivity
import com.example.firebaseauth_android.R


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        setupView()
        register()
        setupAction()

    }


    private fun register() {
        binding.registerButton.setOnClickListener {
            val name = binding.edRegiterUsername.text.toString()
            val email = binding.edRegisterEmail.text.toString().trim()
            val password = binding.edRegisterPassword.text.toString().trim()

            when {
                name.isEmpty() -> {
                    binding.edRegiterUsername.error = resources.getString(R.string.message_validation, "name")

                }
                email.isEmpty() -> {
                    binding.edRegisterEmail.error = resources.getString(R.string.message_validation, "email")
                }
                password.isEmpty() -> {
                    binding.edRegisterPassword.error = resources.getString(R.string.message_validation, "password")
                }
                else -> {
                    if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                            if (it.isSuccessful){
                                AlertDialog.Builder(this@RegisterActivity).apply {
                                    setTitle("Congratulations")
                                    setMessage("Your account successfully created!")
                                    setPositiveButton("Next") { dialog, _ ->
                                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }
                            else{
                                Toast.makeText(this@RegisterActivity, "Please Try Again", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                    else {
                        Toast.makeText(this, resources.getString(R.string.signup_error), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun setupAction() {
        binding.tvSignIn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}
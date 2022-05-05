package com.example.electrobus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.electrobus.databinding.ActivityAuthBinding
import com.example.electrobus.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        val email = binding.emailEditText.text
        val password = binding.passwordEditText.text
        if(email.isNotEmpty() && password.isNotEmpty()){
            FirebaseAuth.getInstance().
            createUserWithEmailAndPassword(email.toString(),
                password.toString()).addOnCompleteListener{
                if(it.isSuccessful){
                    showHome(it.result?.user?.email?: "", ProviderType.BASIC)
                }else{
                    showAlert()
                }
            }
        }
        */
    }
}
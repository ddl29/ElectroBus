package com.example.electrobus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.electrobus.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //SetUp
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email?:"", provider?:"")
    }

    private fun setup(email: String, provider: String){
        title = "Inicio"
        //binding.emailTextView.text = email
        //binding.providerTextView.text = provider

        /*binding.logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }*/
    }
}
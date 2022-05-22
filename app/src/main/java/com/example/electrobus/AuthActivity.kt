package com.example.electrobus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.window.SplashScreen
import androidx.appcompat.app.AlertDialog
import com.example.electrobus.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(500)
        setTheme(R.style.Theme_ElectroBus)

        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setup
        setup()
    }

    private fun setup(){
        title = "Authentication"

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etxEmail
            val password = binding.etxPassword

            var noErrors = true

            if(email.text.isEmpty()){
                email.setError("Ingresa tu email.")
                noErrors = false
            }
            if(password.text.isEmpty()){
                password.setError("Ingresa tu contraseña.")
                noErrors = false
            }

            if(noErrors){
                FirebaseAuth.getInstance().
                signInWithEmailAndPassword(email.text.toString(),
                    password.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        val uid = it.result?.user?.uid.toString()
                        //showHome(typeUser)
                        //Toast.makeText(this, getTypeUser(uid), Toast.LENGTH_SHORT).show()
                        getTypeUser(uid)


                    }else{
                        showAlert()
                    }
                }
            }else{
                return@setOnClickListener
            }
        }
    }

    private fun getTypeUser(uid: String){
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("typeUser").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                showHome(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String){
        val homeIntent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
    }
}


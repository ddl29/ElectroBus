package com.example.electrobus

import android.content.Intent
import android.content.res.Resources
import android.content.res.Resources.getSystem
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.window.SplashScreen
import androidx.appcompat.app.AlertDialog
import com.example.electrobus.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

enum class UserType{
    Conductor,
    Pasajero
}

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    lateinit var locale : Locale

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(500)
        setTheme(R.style.Theme_ElectroBus)

        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val defselect : String = "${getString(R.string.spnDefault)}"
        var lang = ArrayList<String>()
        lang.add(defselect)
        lang.add(" Español")
        lang.add(" English")
        lang.add("Français")
        var adapter = ArrayAdapter(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, lang)
        binding.spnLangSelect.adapter = adapter

        binding.spnLangSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, selItem: Int, id: Long) {
                when(selItem) {
                    0 -> { }
                    1 -> setLocale("es")
                    2 -> setLocale("en")
                    3 -> setLocale("fr")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        //setup
        setup()
    }

    fun setLocale (lang : String) {
        locale = Locale(lang)
        var res = resources
        var config = res.configuration
        config.locale = locale
        var metrics = res.displayMetrics

        res.updateConfiguration(config,metrics)
        var refresh = Intent(this, AuthActivity::class.java)
        startActivity(refresh)
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
                        getUserTypeAndShowHome(uid)

                    }else{
                        showAlert()
                    }
                }
            }else{
                return@setOnClickListener
            }
        }
    }

    private fun getUserTypeAndShowHome(uid: String){
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("typeUser").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                showHome(snapshot.value.toString())
                //Toast.makeText(applicationContext, snapshot.value.toString(), Toast.LENGTH_SHORT).show()
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

    private fun showHome(userType: String){
        if(userType == "Conductor")
            startActivity(Intent(this, HomeDriverActivity::class.java))
        else
            startActivity(Intent(this, HomeActivity::class.java))
    }
}


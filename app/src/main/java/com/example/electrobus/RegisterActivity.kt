package com.example.electrobus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.electrobus.databinding.ActivityAuthBinding
import com.example.electrobus.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.intellij.lang.annotations.Language
import java.util.*
import kotlin.collections.ArrayList

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

        registerUser()
    }

    private fun registerUser() {
        val name = binding.etxNombre
        val id = binding.etxMatricula
        val email = binding.etxEmail
        val password = binding.etxPassword
        var typeUser = ""

        binding.registerSendButton.setOnClickListener {
            var noErrors = true

            typeUser = getUserType()

            if(name.text.isEmpty()){
                name.setError("Ingresa tu nombre.")
                noErrors = false
            }
            if(id.text.isEmpty()){
                id.setError("Ingresa tu matrícula o nómina.")
                noErrors = false
            }
            if(email.text.isEmpty()){
                email.setError("Ingresa tu email.")
                noErrors = false
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email.text).matches()){
                email.setError("Email no válido.")
                noErrors = false
            }
            if(password.text.isEmpty()){
                password.setError("Ingresa tu contraseña.")
                noErrors = false
            }
            if(password.text.length < 6){
                password.setError("La contraseña debe tener al menos 6 caracteres.")
                noErrors = false
            }

            if(typeUser == "undefined"){
                Toast.makeText(this, "Selecciona un tipo de usuario.", Toast.LENGTH_SHORT).show()
                noErrors = false
            }

            if(noErrors){
                FirebaseAuth.getInstance().
                createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).
                addOnCompleteListener {
                    if(it.isSuccessful){
                        var user = User(name.text.toString(), id.text.toString(),
                            email.text.toString(), password.text.toString(),
                            typeUser,"Garza Sada")
                        FirebaseAuth.getInstance().currentUser?.let { it1 ->
                            FirebaseDatabase.getInstance().getReference("Users").child(it1.uid)
                        }?.setValue(user)?.addOnCompleteListener { it ->
                            if(it.isSuccessful){
                                Toast.makeText(this, "Usuario registrado con éxito.", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, AuthActivity::class.java))
                            } else {
                                Toast.makeText(this, "Ha ocurrido un error. Intente de nuevo.", Toast.LENGTH_SHORT).show()
                            }
                        }

                    } else {
                        Toast.makeText(this, "Ha ocurrido dos error. Intente de nuevo.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                return@setOnClickListener
            }

        }
    }

    private fun getUserType(): String {
        return when(binding.rgpUserType.checkedRadioButtonId){
            R.id.btnPasajero -> "Pasajero"
            R.id.btnConductor -> "Conductor"
            else -> "undefined"
        }
    }
}
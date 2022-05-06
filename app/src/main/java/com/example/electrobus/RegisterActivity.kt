package com.example.electrobus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.electrobus.databinding.ActivityAuthBinding
import com.example.electrobus.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

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
        val name = binding.nombrePlainText
        val matricula = binding.matriculaPlainText
        val email = binding.emailPlainText
        val password = binding.passwordPlainText
        val user_type = when(binding.userTypeRadioGroup.checkedRadioButtonId){
            R.id.pasajeroButton -> "pasajero"
            else -> "conductor"
        }

        binding.registerSendButton.setOnClickListener {
            var noErrors = true

            if(name.text.isEmpty()){
                name.setError("Ingresa tu nombre.")
                noErrors = false
            }
            if(matricula.text.isEmpty()){
                matricula.setError("Ingresa tu matrícula o nómina.")
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

            if(noErrors){
                FirebaseAuth.getInstance().
                createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).
                addOnCompleteListener {
                    if(it.isSuccessful){
                        var user = User(name.text.toString(), matricula.text.toString(),
                            email.text.toString(), password.text.toString(),
                            user_type)
                        FirebaseAuth.getInstance().currentUser?.let { it1 ->
                            FirebaseDatabase.getInstance().getReference("Users").child(it1.uid)
                        }?.setValue(user)?.addOnCompleteListener { it ->
                            if(it.isSuccessful){
                                Toast.makeText(this, "Usuario registrado con éxito.", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, AuthActivity::class.java))
                            }else{
                                Toast.makeText(this, "Ha ocurrido unnnn error. Intente de nuevo.", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }else{
                        Toast.makeText(this, "Ha ocurrido dos error. Intente de nuevo.", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                return@setOnClickListener
            }

        }
    }
}
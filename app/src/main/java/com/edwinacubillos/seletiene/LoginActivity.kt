package com.edwinacubillos.seletiene

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvRegistrar: TextView = findViewById(R.id.tvRegistrar)

        tvRegistrar.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    fun bIniciar_sesionClicked(view: View) {
        var email = etEmail.text.toString()
        var password = etContrasena.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Debe digitar todos los datos",Toast.LENGTH_SHORT).show()
            etContrasena.error = "Debe digitar contrasena"
        } else{
            if (password.length<6) {
                etContrasena.error = "Password debe tener minimo 6 caracteres"
            } else {
                iniciarSesionFirebase(email,password)
            }
        }
    }

    private fun iniciarSesionFirebase(email: String, password: String) {


    }
}

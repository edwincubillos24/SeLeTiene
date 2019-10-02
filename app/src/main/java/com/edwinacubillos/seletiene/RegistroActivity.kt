package com.edwinacubillos.seletiene

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
    }

    fun bRegistrar_sesionClicked(view: View) {
        var email = etEmail.text.toString()
        var password = etContrasena.text.toString()
        var reppassword = etRepContrasena.text.toString()

        if (!(password == reppassword)) {
            etContrasena.error = "Las contraseñas no coinciden"
        } else {
            createUser(email, password)
        }
    }

    private fun createUser(email: String, password: String) {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    createUserDatabase(email)
                    goToMainActivity()
                } else {
                    Log.w("Registro", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, task.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun createUserDatabase(email: String) {

    }

    private fun goToMainActivity() {
        var intent = Intent (this, MainActivity::class.java )
        startActivity(intent)
    }
}

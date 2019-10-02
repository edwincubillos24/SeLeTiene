package com.edwinacubillos.seletiene

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

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
          //  if (password.length<6) {
         //       etContrasena.error = "Password debe tener minimo 6 caracteres"
         //   } else {
                iniciarSesionFirebase(email,password)
          //  }
        }
    }

    private fun iniciarSesionFirebase(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    goToMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LoginActivity", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, task.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun goToMainActivity() {
        var intent = Intent (this, MainActivity::class.java )
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            Toast.makeText(this,"Bienvenido "+currentUser.email, Toast.LENGTH_SHORT).show()
            goToMainActivity()
        }
    }
}

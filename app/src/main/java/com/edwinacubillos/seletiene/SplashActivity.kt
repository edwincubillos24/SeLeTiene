package com.edwinacubillos.seletiene

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.edwinacubillos.seletiene.utils.TIMER_SPLASH
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {

    val TIMER_SPLASH : Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Log.d("Estoy en", "OnCreate")
        val timer = Timer()
        timer.schedule(timerTask {
            goToLoginActivity()
        },TIMER_SPLASH)
    }

    fun goToLoginActivity(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onStart() {
        super.onStart()
        Log.d("Estoy en", "OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Estoy en", "OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Estoy en", "OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Estoy en", "OnStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Estoy en", "OnRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Estoy en", "OnDestroy")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this,"Adios",Toast.LENGTH_SHORT).show()
    }


}

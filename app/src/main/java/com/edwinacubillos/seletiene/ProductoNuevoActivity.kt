package com.edwinacubillos.seletiene

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edwinacubillos.seletiene.model.Producto
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_producto_nuevo.*
import kotlinx.android.synthetic.main.content_producto_nuevo.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class ProductoNuevoActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private var currentUser: FirebaseUser? = null
    private var idProducto: String? = null
    private var latitud: String = ""
    private var longitud: String = ""

    private lateinit var myRefProducto: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_nuevo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser

        database = FirebaseDatabase.getInstance()
        myRefProducto = database.getReference("productos")
        idProducto = myRefProducto.push().key

        iv_producto.setOnClickListener {
            tomarFoto()
        }

        bt_guardar.setOnClickListener {
            //guardarProducto()
            guardarFoto()
        }
    }

    private fun guardarFoto() {
        val storage = FirebaseStorage.getInstance()
        val myRefFoto = storage.reference
            .child("productos")
            .child(currentUser!!.uid)
            .child(idProducto!!)

        val bitmap = (iv_producto.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = myRefFoto
            .putBytes(data)

        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    Log.d("url",it.message.toString())
                    throw it
                }
            }
            return@Continuation myRefFoto.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val downloadUrl = task.result.toString()
                Log.d("url",downloadUrl)
                guardarProducto(downloadUrl)
            } else {
            }
        }
    }

    private fun guardarProducto(downloadUrl: String) {
        var geocoder = Geocoder(this)
        var ubicacion = et_ubicacion.text.toString()

        lateinit var list: MutableList<Address>

        try{
            list = geocoder.getFromLocationName(ubicacion,1)
        }catch (e: IOException){

        }

        if (list.size>0){
            var address: Address = list.get(0)
            latitud = address.latitude.toString()
            longitud = address.longitude.toString()
        }

        val producto = Producto(
            idProducto!!,
            currentUser?.uid,
            et_nombre.text.toString(),
            et_descripcion.text.toString(),
            et_precio.text.toString().toInt(),
            rb_disponible.isChecked,
            downloadUrl,
            longitud,
            latitud,
            et_cantidad.text.toString().toInt(),
            ubicacion)

        myRefProducto.child(idProducto!!).setValue(producto)
        onBackPressed()
    }

    private fun tomarFoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, 1000)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            iv_producto.setImageBitmap(imageBitmap)
        } else
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "foto cancelada", Toast.LENGTH_SHORT).show()
            }
    }

}

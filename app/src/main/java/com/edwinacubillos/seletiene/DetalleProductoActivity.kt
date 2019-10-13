package com.edwinacubillos.seletiene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edwinacubillos.seletiene.model.Producto
import com.edwinacubillos.seletiene.model.local.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalle_producto.*
import kotlinx.android.synthetic.main.content_detalle_producto.*

class DetalleProductoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val producto = intent?.getSerializableExtra("producto") as Producto

        tv_nombre.text = producto.nombre
        tv_descripcion.text = producto.descripcion
        tv_cantidad.text = producto.cantidad.toString()
        tv_precio.text = producto.precio.toString()
        tv_ubicacion.text = producto.ubicacion
        if (producto.urlFoto != "")
            Picasso.get().load(producto.urlFoto).into(iv_foto)

        bn_favorito.setOnClickListener {
            val repository = Repository()
            repository.insertarProducto(producto)
            bn_favorito.setImageResource(R.drawable.ic_favorite_black_24dp)
        }

    }
}

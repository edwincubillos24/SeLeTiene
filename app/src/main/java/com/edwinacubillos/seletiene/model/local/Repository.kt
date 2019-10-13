package com.edwinacubillos.seletiene.model.local

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.edwinacubillos.seletiene.SeLeTiene
import com.edwinacubillos.seletiene.model.Producto

class Repository {

    var productoDao: ProductoDAO = SeLeTiene.database.ProductoDao()

    fun insertarProducto(producto: Producto){
        object : AsyncTask<Void, Void, Void>(){

            override fun doInBackground(vararg params: Void): Void? {
                productoDao.insertProducto(producto)
                return null
            }
        }.execute()
    }

    fun getProductoFavorito(): LiveData<List<Producto>>{
        return productoDao.getProductosFavoritos()
    }
}
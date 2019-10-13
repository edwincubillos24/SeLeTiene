package com.edwinacubillos.seletiene.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.edwinacubillos.seletiene.model.Producto

@Dao
interface ProductoDAO {

    @Insert
    fun insertProducto(producto: Producto)

    @Query("SELECT * FROM producto_entity")
    fun getProductosFavoritos(): LiveData<List<Producto>>
}
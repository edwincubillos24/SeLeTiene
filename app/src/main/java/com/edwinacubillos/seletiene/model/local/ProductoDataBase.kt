package com.edwinacubillos.seletiene.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edwinacubillos.seletiene.model.Producto

@Database(entities = arrayOf(Producto::class), version = 1)
abstract class ProductoDataBase : RoomDatabase() {
    abstract fun ProductoDao() : ProductoDAO
}
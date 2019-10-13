package com.edwinacubillos.seletiene

import android.app.Application
import androidx.room.Room
import com.edwinacubillos.seletiene.model.local.ProductoDataBase

class SeLeTiene : Application(){

    companion object{
        lateinit var database: ProductoDataBase
    }

    override fun onCreate() {
        super.onCreate()
        SeLeTiene.database = Room.databaseBuilder(
            this,
            ProductoDataBase::class.java,
            "producto_entity"
        ).build()
    }
}
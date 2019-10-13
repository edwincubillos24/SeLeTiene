package com.edwinacubillos.seletiene.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "producto_entity")
class Producto (
    @PrimaryKey
    @NonNull
    var idProd: String, //por que?
    var idUser: String?,
    var nombre: String,
    var descripcion: String,
    var precio: Int,
    var estaDisponible: Boolean,
    var urlFoto: String,
    var longitud: String,
    var latitud: String,
    var cantidad: Int,
    var ubicacion: String
) : Serializable {
    @Ignore
    constructor() : this ("",
        "",
        "",
        "",
        0,
        false,
        "",
        "",
        "",
        0,
        "")
}
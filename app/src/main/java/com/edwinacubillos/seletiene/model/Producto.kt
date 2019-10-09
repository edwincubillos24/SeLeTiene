package com.edwinacubillos.seletiene.model

import java.io.Serializable

class Producto (
    var idProd: String?,
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
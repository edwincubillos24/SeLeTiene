package com.edwinacubillos.seletiene.model

class Usuario(
    var id: String,
    var correo: String?,
    var telefono: String,
    var username: String,
    var urlFoto: String
) {
    constructor() : this ("","","","","")

}
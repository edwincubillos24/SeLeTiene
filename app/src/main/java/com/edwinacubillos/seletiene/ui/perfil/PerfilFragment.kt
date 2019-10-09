package com.edwinacubillos.seletiene.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.edwinacubillos.seletiene.LoginActivity
import com.edwinacubillos.seletiene.R
import com.edwinacubillos.seletiene.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*

class PerfilFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var uid: String
    private lateinit var database: FirebaseDatabase
    private lateinit var myRefUsuarios: DatabaseReference
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_perfil, container, false)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!

        uid = currentUser.uid

        database = FirebaseDatabase.getInstance()
        myRefUsuarios = database.getReference("usuarios")

        cargarUsuario()

        root.bt_actualizar.setOnClickListener {
            val datoActualizar = HashMap<String, Any>()
            datoActualizar["telefono"] = et_telefono.text.toString()
            datoActualizar["username"] = et_nombre.text.toString()

            myRefUsuarios.child(uid).updateChildren(datoActualizar)

            activity!!.onBackPressed()
        }

        root.bt_cerrarSesion.setOnClickListener {
            auth.signOut()
            var intent = Intent(activity!!.applicationContext, LoginActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }

        return root
    }

    private fun cargarUsuario() {

        myRefUsuarios.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    if(snapshot.key == uid){
                        var usuario = snapshot.getValue(Usuario::class.java)
                        root.et_nombre.setText(usuario?.username)
                        root.et_telefono.setText(usuario?.telefono)
                        root.tv_correo.setText(usuario?.correo)
                    }
                }
            }
        })
    }
}
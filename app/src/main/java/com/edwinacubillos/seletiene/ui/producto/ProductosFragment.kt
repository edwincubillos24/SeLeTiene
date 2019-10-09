package com.edwinacubillos.seletiene.ui.producto

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edwinacubillos.seletiene.ProductoNuevoActivity
import com.edwinacubillos.seletiene.ProductosAdapter
import com.edwinacubillos.seletiene.R
import com.edwinacubillos.seletiene.model.Producto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.fragment_productos.view.*

class ProductosFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var productosList = ArrayList<Producto>()
    private lateinit var productosAdapter: ProductosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_productos, container, false)
        // val textView: TextView = root.findViewById(R.id.text_home)

        cargarProductos()
        productosAdapter = ProductosAdapter(productosList)

        root.recyclerView.setHasFixedSize(true)
        root.recyclerView.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            RecyclerView.VERTICAL,
            false
        )
        root.recyclerView.adapter = productosAdapter


        root.bt_nuevoProducto.setOnClickListener {
            goToNuevoProducto()
        }

        homeViewModel.text.observe(this, Observer {
            //     textView.text = it
        })
        return root
    }

    private fun cargarProductos() {
        val database = FirebaseDatabase.getInstance()
        val myRefProducto = database.getReference("productos")

        myRefProducto.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                productosList.clear()
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    val producto: Producto? = snapshot.getValue(Producto::class.java)
                    productosList.add(producto!!)
                }
                productosAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun goToNuevoProducto() {
        val intent = Intent(activity?.applicationContext, ProductoNuevoActivity::class.java)
        startActivity(intent)
    }
}
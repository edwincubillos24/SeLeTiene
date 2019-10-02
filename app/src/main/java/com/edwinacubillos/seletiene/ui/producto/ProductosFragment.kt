package com.edwinacubillos.seletiene.ui.producto

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.edwinacubillos.seletiene.NuevoProductoActivity
import com.edwinacubillos.seletiene.R
import kotlinx.android.synthetic.main.fragment_productos.view.*

class ProductosFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_productos, container, false)
       // val textView: TextView = root.findViewById(R.id.text_home)

        root.bt_nuevoProducto.setOnClickListener {
            goToNuevoProducto()
        }


        homeViewModel.text.observe(this, Observer {
       //     textView.text = it
        })
        return root
    }

    private fun goToNuevoProducto() {
        val intent = Intent(activity?.applicationContext, NuevoProductoActivity::class.java)
        startActivity(intent)
    }
}
package com.edwinacubillos.seletiene.ui.favoritos


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edwinacubillos.seletiene.adapters.ProductosAdapter
import com.edwinacubillos.seletiene.R
import com.edwinacubillos.seletiene.model.Producto
import com.edwinacubillos.seletiene.model.local.Repository
import kotlinx.android.synthetic.main.fragment_favoritos.view.*

/**
 * A simple [Fragment] subclass.
 */
class FavoritosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_favoritos, container, false)

        root.recyclerView.setHasFixedSize(true)
        root.recyclerView.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            RecyclerView.VERTICAL,
            false
        )

        val repository = Repository()
        repository.getProductoFavorito().observe(this, Observer{
            root.recyclerView.adapter =
                ProductosAdapter(it as ArrayList<Producto>)
        })

        return root
    }
}

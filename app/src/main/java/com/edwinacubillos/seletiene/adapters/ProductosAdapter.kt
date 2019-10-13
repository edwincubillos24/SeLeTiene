package com.edwinacubillos.seletiene.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edwinacubillos.seletiene.DetalleProductoActivity
import com.edwinacubillos.seletiene.R
import com.edwinacubillos.seletiene.model.Producto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_item.view.*

class ProductosAdapter(productosList: ArrayList<Producto>) :
    RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder>() {

    private var productosList: ArrayList<Producto> = ArrayList()

    init {
        this.productosList = productosList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.product_item,
                parent,
                false
            )
        return ProductosViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productosList.size
    }

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {
        val producto = productosList[position]
        holder.setProducto(producto)
    }

    class ProductosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var producto: Producto ?= null

        init{
            itemView.setOnClickListener(this)
        }

        fun setProducto(producto: Producto) {
            this.producto = producto
            itemView.tv_precio.text = producto.precio.toString()
            itemView.tv_producto.text = producto.nombre
            if (producto.urlFoto!= "")
            Picasso.get().load(producto.urlFoto).into(itemView.iv_picture)
        }

        override fun onClick(v: View?) {
            val intent = Intent(itemView.context, DetalleProductoActivity::class.java)
            intent.putExtra("producto", producto)
            itemView.context.startActivity(intent)
        }
    }
}
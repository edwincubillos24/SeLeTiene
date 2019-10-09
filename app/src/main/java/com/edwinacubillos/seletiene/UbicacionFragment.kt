package com.edwinacubillos.seletiene

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.edwinacubillos.seletiene.model.Producto
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UbicacionFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
 //   private lateinit var mapView: MapView
    private var productosList = ArrayList<Producto>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_ubicacion, container, false)

        var mapView = childFragmentManager.
            findFragmentById(R.id.mapView) as SupportMapFragment
  /*      mapView = root.findViewById(R.id.mapView)
        try{
            mapView.onCreate(savedInstanceState)
        } catch (e: Exception){
            e.printStackTrace()
        }*/
        mapView.getMapAsync(this)
        return root
    }

  /*  override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }*/

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

     //   mMap.isMyLocationEnabled = true

        cargarProductos()

        val eafit = LatLng(6.199548, -75.57934)
        mMap.addMarker(MarkerOptions().position(eafit).title("Marker in Eafit"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eafit, 15F))
    }

    private fun cargarProductos() {
        val database = FirebaseDatabase.getInstance()
        val myRefProducto = database.getReference("productos")

        myRefProducto.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    val producto: Producto? = snapshot.getValue(Producto::class.java)
                    productosList.add(producto!!)
                    Log.d("latitud: ",producto.latitud.toDouble().toString())
                    Log.d("longitud: ",producto.longitud.toDouble().toString())

                    val marker = LatLng(producto.latitud.toDouble(), producto.longitud.toDouble())
                    mMap.addMarker(MarkerOptions().position(marker).title(producto.nombre))
                }
            }
        })
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                activity!!.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        mMap.isMyLocationEnabled = true
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
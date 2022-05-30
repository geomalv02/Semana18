package com.example.semana18

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map:GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        createFragment()
    }

    // creamos el fragmento del mapa
    private fun createFragment() {
        val mapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // creamos el mapa cargado
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
        RequesLocation()
    }

    // creamos un marcador y una animacion para ir al marcador
    private fun createMarker() {
        val coordinates = LatLng(13.488959578219976, -88.19196206146212)
        val marker:MarkerOptions = MarkerOptions().position(coordinates).title("Universidad Gerardo Barrios")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            4000,
            null
        )
    }

    // Creamos una condicion para saber si tenemos permniso para utilizar la ubicacion.
    private fun PermisoConcedido() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    // creamos la funcion para conseguir la localizacion
    private fun RequesLocation(){
        // nos aseguramos de que el mapa este inicializado
        if(!::map.isInitialized) return
        if(PermisoConcedido()){
            //si esta aceptado
            map.isMyLocationEnabled = true
        }else{
            //no esta aceptado
        }
    }

    //metodo para pedir los permisos
    private fun PedirPermiso(){
        // esto se dara si el usuario rechaza los permisos
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(
                this, "De permiso a la aplicacion en ajustes",
                Toast.LENGTH_SHORT).show()
        }else // esto se dara en el caso que ya se hayan aceptado los permisos{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }

    //funcion para conseguir los permisos en el caso de que el usuario los haya aceptado

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Acepte los permisos para activar la localizacion", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    // comprobamos que los permisos siguen activos despues de cambiar de aplicacion
    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized) return
        if(!PermisoConcedido()){
            map.isMyLocationEnabled == false
            Toast.makeText(this, "Acepte los permisos para activar la localizacion", Toast.LENGTH_SHORT).show()

        }
    }
}

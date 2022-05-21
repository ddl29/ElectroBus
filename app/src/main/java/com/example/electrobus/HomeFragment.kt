package com.example.electrobus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.electrobus.databinding.FragmentHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map:GoogleMap
    private var _binding: FragmentHomeBinding? = null
    private val binding get () = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //createMarker()
        createPolylines()
    }

    private fun createPolylines(){
        val polylineOptions = PolylineOptions()
            .add(LatLng(25.6506566286731,-100.29159307479858))
            .add(LatLng(25.655588997294437,-100.29483318328857))
            .add(LatLng(25.66055984481777, -100.29704332351685))
            .add(LatLng(25.6654724633204, -100.29719352722167))
            .add(LatLng(25.670056101992117, -100.29865264892578))
            .add(LatLng(25.6727056355285, -100.29815912246704))
            .add(LatLng(25.670694316184363, -100.29895305633545))
            .add(LatLng(25.66216517155013, -100.2980089187622))
            .add(LatLng(25.66019235734555, -100.29747247695923))
            .add(LatLng(25.651333632518295, -100.29258012771606))
            .add(LatLng(25.64798726179442, -100.2903699874878))
            .add(LatLng(25.64363490513935, -100.2877950668335))
            .add(LatLng(25.64032700792783, -100.2855634689331))
            .add(LatLng(25.63928238976323, -100.2848768234253))
            .add(LatLng(25.63554877234507, -100.2825379371643))
            .add(LatLng(25.630963807817988, -100.2796196937561))
            .add(LatLng(25.628352040464144, -100.27798891067505))
            .add(LatLng(25.625430662642948, -100.27618646621703))
            .add(LatLng(25.615311695801392, -100.27155160903929))
            .add(LatLng(25.617536781133452, -100.27592897415161))
            .add(LatLng(25.620748570368658, -100.2842116355896))
            .add(LatLng(25.620168132922515, -100.28446912765503))
            .add(LatLng(25.618272017614135, -100.28133630752563))
            .add(LatLng(25.61486667376325, -100.2761435508728))
            .add(LatLng(25.6137057389068, -100.27256011962889))
            .add(LatLng(25.612370649880237, -100.27157306671141))
            .add(LatLng(25.610629206997984, -100.27142286300659))
            .add(LatLng(25.60664314223758, -100.26893377304076))
            .add(LatLng(25.611054895378892, -100.27024269104002))
            .add(LatLng(25.615834110862025, -100.27097225189209))
            .add(LatLng(25.625643480658788, -100.2756929397583))
            .add(LatLng(25.630344727387865, -100.27880430221556))
            .add(LatLng(25.63434934715195, -100.28135776519775))
            .add(LatLng(25.638779422201498, -100.28412580490112))
            .add(LatLng(25.641855229143587, -100.28588533401489))
            .add(LatLng(25.64313195591746, -100.28682947158813))
            .add(LatLng(25.648025948697388, -100.28985500335693))
            .add(LatLng(25.650501884397684, -100.29157161712646))
            .width(5f)


        val polyline = map.addPolyline(polylineOptions)
    }

    private fun createMarker(){
        val coordinates = LatLng(28.043893, -16.539329)
        val marker = MarkerOptions().position(coordinates).title("Mi playa")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates,18f),
            4000,
            null
        )
    }

}
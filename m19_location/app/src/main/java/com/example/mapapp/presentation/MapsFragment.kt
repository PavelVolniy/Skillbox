package com.example.mapapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mapapp.databinding.MapsFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapsFragment : Fragment() {
    private lateinit var map: MapView
    private lateinit var fusedLocation: FusedLocationProviderClient
    private var _viewBinding: MapsFragmentBinding? = null
    private val binding get() = _viewBinding!!
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && it.value == false) permissionGranted = false
                if (!permissionGranted) {
                    Toast.makeText(
                        context,
                        "Permission request denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

    private val locationCallBack = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation.let { location ->
                location?.latitude = 47.208418
                location?.longitude = 39.757041
                location?.let {
                    map.controller.animateTo(
                        GeoPoint(location.latitude, location.longitude),
                        15.5,
                        1000
                    )
                    val myLocation = MyLocationNewOverlay(map)
                    myLocation.enableMyLocation()
                    map.overlays.add(myLocation)
                }
            }
        }
    }

    private fun startLocation() {
        val request = LocationRequest.create()
            .setInterval(1_000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocation.requestLocationUpdates(
            request,
            locationCallBack,
            Looper.getMainLooper()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocation =
            LocationServices.getFusedLocationProviderClient(requireContext())
        if (!allPermissionsGranted()) requestPermissions()
        else startLocation()
        Configuration.getInstance().userAgentValue = BuildConfig.BUILD_TYPE
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = MapsFragmentBinding.inflate(layoutInflater)
        map = binding.map
        map.setTileSource(TileSourceFactory.MAPNIK)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapController = map.controller
        mapController.setZoom(15.5)
        mapController.setCenter(GeoPoint(47.226028, 39.924118))
        val array = ArrayList<OverlayItem>()
        array.add(
            OverlayItem(
                "Закопана рыба",
                "Но это прям совсем не точно!",
                GeoPoint(47.226028, 39.924190)
            )
        )
        var overlay = ItemizedOverlayWithFocus(
            array,
            object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    return false
                }
            },
            requireContext()
        )
        overlay.setFocusItemsOnTap(true)
        map.overlays.add(overlay)
        val compas = CompassOverlay(
            requireContext(),
            InternalCompassOrientationProvider(requireContext()),
            map
        )
        compas.enableCompass()
        map.overlays.add(compas)
        val dm: DisplayMetrics = requireContext().resources.displayMetrics
        val scaleBarOverlay = ScaleBarOverlay(map)
        scaleBarOverlay.setCentred(true)
        val mScaleBarOverlay = ScaleBarOverlay(map)
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10)
        map.overlays.add(scaleBarOverlay)
        val rotationGestureOverlay = RotationGestureOverlay(map)
        rotationGestureOverlay.isEnabled
        map.setMultiTouchControls(true)
        map.overlays.add(rotationGestureOverlay)


    }

    override fun onStop() {
        super.onStop()
        fusedLocation.removeLocationUpdates(locationCallBack)
    }

    private fun requestPermissions() {
        launcher.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        context?.let { thisContext ->
            ContextCompat.checkSelfPermission(
                thisContext, it
            )
        } == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

    companion object {
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.ACCESS_FINE_LOCATION)
                add(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }.toTypedArray()

    }

}
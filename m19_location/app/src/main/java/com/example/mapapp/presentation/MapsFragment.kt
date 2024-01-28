package com.example.mapapp.presentation

import android.Manifest
import android.content.Context
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
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mapapp.App
import com.example.mapapp.databinding.MapsFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.osmdroid.api.IMapController
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
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import javax.inject.Inject

class MapsFragment : Fragment() {
    @Inject
    lateinit var viewModel: MapViewModel
    private lateinit var map: MapView
    private lateinit var fusedLocation: FusedLocationProviderClient
    private var currentGeoPoint: GeoPoint? = null
    private var _viewBinding: MapsFragmentBinding? = null
    private val binding get() = _viewBinding!!
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value) permissionGranted = false
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
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onLocationResult(result: LocationResult) {

            result.lastLocation.let { location ->
//                location?.latitude = 47.208418
//                location?.longitude = 39.757041
                currentGeoPoint = GeoPoint(location!!.latitude, location.longitude)
                map.controller.animateTo(
                    GeoPoint(location.latitude, location.longitude),
                    15.5,
                    1000
                )
                val myLocation = MyLocationNewOverlay(map)
                myLocation.disableFollowLocation()
                map.overlays.add(myLocation)
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map = binding.map
        map.setTileSource(TileSourceFactory.MAPNIK)
        val mapController = map.controller
        mapController.setZoom(15.5)
        val array = ArrayList<OverlayItem>()
        viewModel.listObjets.onEach { objectItem ->
            array.add(
                OverlayItem(
                    objectItem.title,
                    objectItem.descriptions,
                    GeoPoint(objectItem.latitude, objectItem.longitude)
                )
            )
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        mapController.setCenter(GeoPoint(47.204663, 39.765014))
        setSettingsMap(array)
        if (viewModel.isCreated) {
            showPoints(array, mapController)
        }
        binding.myPositionButton.setOnClickListener {
            if (currentGeoPoint != null) {
                mapController.animateTo(currentGeoPoint)
            } else {
                Toast.makeText(
                    requireContext(),
                    "The app cannot get the location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        fusedLocation.removeLocationUpdates(locationCallBack)
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
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

    private fun setSettingsMap(array: ArrayList<OverlayItem>) {
        val overlay = ItemizedOverlayWithFocus(
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
    }

    private fun showPoints(
        array: ArrayList<OverlayItem>,
        mapController: IMapController
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            array.forEach {
                delay(1_000)
                mapController.animateTo(it.point, 15.2, 1000)
            }
            viewModel.created()
        }
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
package es.adriiiprieto.notesapp.presentation.fragment.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import dagger.hilt.android.AndroidEntryPoint
import es.adriiiprieto.notesapp.R
import es.adriiiprieto.notesapp.base.BaseExtraData
import es.adriiiprieto.notesapp.base.BaseFragment
import es.adriiiprieto.notesapp.databinding.FragmentMapsBinding

@AndroidEntryPoint
class MapsFragment : BaseFragment<MapsState, MapsViewModel, FragmentMapsBinding>(), OnMapReadyCallback {

    override val viewModelClass: Class<MapsViewModel> = MapsViewModel::class.java

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMapsBinding = FragmentMapsBinding::inflate

    lateinit var vm: MapsViewModel

    lateinit var mMap: GoogleMap

    override fun setupView(viewModel: MapsViewModel) {
        vm = viewModel

        setupMap()

    }

    override fun onNormal(data: MapsState) {
    }

    override fun onLoading(dataLoading: BaseExtraData?) {
    }

    override fun onError(dataError: Throwable) {
    }

    /**
     * Setup view
     */
    private fun setupMap() {
        (this.childFragmentManager.findFragmentById(R.id.fragmentMapsMyMap) as SupportMapFragment).getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }


    /**
     * Map functions
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

        val mUiSettings = mMap.uiSettings

        mUiSettings.isMapToolbarEnabled = true
        mUiSettings.isCompassEnabled = true
        mUiSettings.setAllGesturesEnabled(true)
        mUiSettings.isMyLocationButtonEnabled = false

        updateLocationUI()

        getDeviceLocation()

        return

        mMap.apply {

            // Add point
            /*val sydney = LatLng(-33.852, 151.211)
            addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney")
            )*/

            // Add line
            /*addPolyline(
                PolylineOptions()
                    .clickable(true)
                    .add(
                        LatLng(-35.016, 143.321),
                        LatLng(-34.747, 145.592),
                        LatLng(-34.364, 147.891),
                        LatLng(-33.501, 150.217),
                        LatLng(-32.306, 149.248),
                        LatLng(-32.491, 147.309)
                    )
            )*/

            // Add polygons
            addPolygon(
                PolygonOptions()
                    .clickable(true)
                    .add(
                        LatLng(-27.457, 153.040),
                        LatLng(-33.852, 151.211),
                        LatLng(-37.813, 144.962),
                        LatLng(-34.928, 138.599)
                    )
            )
        }


        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-23.684, 133.903), 4f))

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener {
            Toast.makeText(requireActivity(), "Línea", Toast.LENGTH_SHORT).show()
        }
        googleMap.setOnPolygonClickListener {
            Toast.makeText(requireActivity(), "Polígono", Toast.LENGTH_SHORT).show()
        }

    }


    companion object {
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1000
    }

    private var locationPermissionGranted = false

    private fun updateLocationUI() {
        try {
            if (locationPermissionGranted) {
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(requireActivity().applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
            updateLocationUI()
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }


    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var lastKnownLocation: Location? = null

    private val defaultLocation = LatLng(-33.8523341, 151.2106085)

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude), 15f))
                        }
                    } else {
                        Log.d("MapsFragment", "Current location is null. Using defaults.")
                        Log.e("MapsFragment", "Exception: %s", task.exception)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))
                        mMap.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

}
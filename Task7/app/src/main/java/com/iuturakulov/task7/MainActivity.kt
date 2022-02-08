package com.iuturakulov.task7

import android.app.Activity
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.*
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError
import android.R.style
import android.content.ContentValues.TAG

import com.yandex.mapkit.map.MapType
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder


class MainActivity : Activity(), UserLocationObjectListener,  Session.SearchListener,
    CameraListener {

    private val MAPKIT_API_KEY = "a6c9e2c0-63e8-49f8-b259-31e3de4806da"
    private var mapView: MapView? = null
    private var userLocationLayer: UserLocationLayer? = null
    private var searchEdit: EditText? = null
    private var searchManager: SearchManager? = null
    private var searchSession: Session? = null

    private fun submitQuery(query: String) {
        searchSession = searchManager?.submit(
            query,
            VisibleRegionUtils.toPolygon(mapView!!.map.visibleRegion),
            SearchOptions(),
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        mapView = findViewById(R.id.mapviewyandex)
        mapView!!.map.isRotateGesturesEnabled = false

        mapView!!.map.move(CameraPosition(Point(55.845753, 37.369867), 14F, 0F, 0F))
        val mapKit = MapKitFactory.getInstance()
        mapKit.createLocationManager().subscribeForLocationUpdates(0.0, 0, 0.0, true, FilteringMode.ON, object : LocationListener {
                override fun onLocationUpdated(location: Location) {
                    Log.d("TagCheck", "LocationUpdated " + location.position.longitude)
                    Log.d("TagCheck", "LocationUpdated " + location.position.latitude)
                }

                override fun onLocationStatusUpdated(locationStatus: LocationStatus) {}
            })
        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)

        mapView = findViewById<View>(R.id.mapviewyandex) as MapView
        mapView!!.map.addCameraListener(this)

        searchEdit = findViewById<View>(R.id.search_edit) as EditText
        searchEdit!!.setOnEditorActionListener(OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                submitQuery(searchEdit!!.text.toString())
            }
            false
        })
        submitQuery(searchEdit!!.text.toString())
        userLocationLayer = mapKit.createUserLocationLayer(mapView!!.mapWindow)
        userLocationLayer!!.isVisible = true
        userLocationLayer!!.isHeadingEnabled = true
        userLocationLayer!!.setObjectListener(this)
        mapKit.createLocationSimulator()
    }

    override fun onStop() {
        mapView!!.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView!!.onStart()
    }

    override fun onObjectAdded(p0: UserLocationView) {
        userLocationLayer!!.setAnchor(
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.5).toFloat()),
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.83).toFloat())
        )
        p0.arrow.setIcon(
            ImageProvider.fromResource(
                this, R.drawable.user_arrow
            )
        )
        val pinIcon = p0.pin.useCompositeIcon()
        pinIcon.setIcon(
            "icon",
            ImageProvider.fromResource(this, R.drawable.icon),
            IconStyle().setAnchor(PointF(0f, 0f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(0f)
                .setScale(1f)
        )
        pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(this, R.drawable.search_result),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
        )
        p0.accuracyCircle.fillColor = Color.BLUE and -0x66000001
    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
    }

    override fun onSearchResponse(response: Response) {
        val mapObjects = mapView!!.map.mapObjects
        mapObjects.clear()
        for (searchResult in response.collection.children) {
            val resultLocation = searchResult.obj!!.geometry[0].point
            if (resultLocation != null) {
                mapObjects.addPlacemark(
                    resultLocation,
                    ImageProvider.fromResource(this, R.drawable.search_result)
                )
            }
        }
    }

    override fun onSearchError(p0: com.yandex.runtime.Error) {
        var errorMessage = "Error!"
        if (p0 is RemoteError) {
            errorMessage = "Remote error!"
        } else if (p0 is NetworkError) {
            errorMessage = "Network error!"
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onCameraPositionChanged(
        p0: Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        p3: Boolean
    ) {
        if (p3) {
            submitQuery(searchEdit?.text.toString())
        }
    }
}
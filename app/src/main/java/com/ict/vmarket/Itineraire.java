package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Button;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;

public class Itineraire extends AppCompatActivity {

    private MapView mapView;
    private MapboxMap map;
    private Button Startbutton;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originlocation;
    private Point originposition;
    private Point destinationposition;
    private Marker destinationmarker;
    private NavigationMapRoute navigationMapRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this,"pk.eyJ1IjoieDMzcyIsImEiOiJja2VpaWpvcXUwa3h3MnJucHNzbHduOTRuIn0.WSuHcVbYxdMZHgeU31jEwQ");
        setContentView(R.layout.activity_itineraire);

        mapView = (MapView) findViewById(R.id.mapView_it);
        mapView.onCreate(savedInstanceState);
        //mapView.getMapAsync(this);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

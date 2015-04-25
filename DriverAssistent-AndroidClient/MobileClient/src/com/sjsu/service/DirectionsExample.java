package com.sjsu.service;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sjsu.directions.Routing;
import com.sjsu.directions.RoutingListener;

public class DirectionsExample extends FragmentActivity implements RoutingListener
{
    protected GoogleMap map;
    protected LatLng start;
    protected LatLng end;
    /**
     * This activity loads a map and then displays the route and pushpins on it.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SupportMapFragment fm = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
        map = fm.getMap();
        Bundle extras = getIntent().getExtras();
        if (extras != null) 
        {
          String locLat = extras.getString("lat");
          String locLong = extras.getString("long");
          String myLat = extras.getString("mylat");
          String myLong = extras.getString("mylong");
        
        System.out.println(locLat +"::::::::::"+ locLong+"::::::::::"+myLat+"::::::::::"+myLong);
        start = new LatLng(Double.valueOf(locLat),Double.valueOf(locLong));
        end = new LatLng(Double.valueOf(myLat),Double.valueOf(myLong));
        CameraUpdate center=CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom=  CameraUpdateFactory.zoomTo(15);

        map.moveCamera(center);
        map.animateCamera(zoom);

        
        }
        Routing routing = new Routing(Routing.TravelMode.DRIVING);
        routing.registerListener(this);
        routing.execute(start, end);
    }


    @Override
    public void onRoutingFailure() {
      // The Routing request failed
    }

    @Override
    public void onRoutingStart() {
      // The Routing Request starts
    }

    @Override
    public void onRoutingSuccess(PolylineOptions mPolyOptions) {
      PolylineOptions polyoptions = new PolylineOptions();
      polyoptions.color(Color.BLUE);
      polyoptions.width(10);
      polyoptions.addAll(mPolyOptions.getPoints());
      map.addPolyline(polyoptions);

      // Start marker
      MarkerOptions options = new MarkerOptions();
      options.position(start);
      options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
      map.addMarker(options);

      // End marker
      options = new MarkerOptions();
      options.position(end);
      options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));  
      map.addMarker(options);
    }
}

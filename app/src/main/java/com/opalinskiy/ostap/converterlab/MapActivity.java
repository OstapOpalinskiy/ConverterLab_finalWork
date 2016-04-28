package com.opalinskiy.ostap.converterlab;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.opalinskiy.ostap.converterlab.abstractActivities.AbstractMapActivity;
import com.opalinskiy.ostap.converterlab.utils.MapLoader;
import com.opalinskiy.ostap.converterlab.utils.connectUtils.ConnectionDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapActivity extends AbstractMapActivity {
    private GoogleMap map;
    private MapLoader locationLoader;
    private ConnectionDetector connectionDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionDetector = new ConnectionDetector(this);
       if(connectionDetector.isConnected()){
           if (isGoogleMapsAvailable()) {
               setContentView(R.layout.activity_map);

               ((MapFragment) getFragmentManager().findFragmentById(R.id.google_map)).getMapAsync(new OnMapReadyCallback() {
                   @Override
                   public void onMapReady(GoogleMap googleMap) {
                       map = googleMap;
                       map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                       String address = getIntent().getStringExtra("address");
                       String city = getIntent().getStringExtra("city");

                       ActionBar ab =  getSupportActionBar();
                       ab.setTitle(city);
                       ab.setSubtitle(address);
                       ab.setDisplayHomeAsUpEnabled(true);

                       locationLoader = new MapLoader();
                       locationLoader.execute(city, address);
                       try {
                           LatLng latLng = locationLoader.get();
                           if(!(latLng.latitude == 0.0 && latLng.longitude == 0.0)){
                               map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLoader.get(), locationLoader.getZoom()));
                           }else{
                               showAlertDialog(R.string.no_interent_title, R.string.no_internet_msg );
                           }
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       } catch (ExecutionException e) {
                           e.printStackTrace();
                       }
                   }
               });
           } else {
               showAlertDialog(R.string.no_map, R.string.no_map_msg );
           }
       }else{
           showAlertDialog(R.string.no_interent_title, R.string.no_internet_msg );
       }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog(int title, int message) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(getString(title));
        ad.setMessage(getString(message));
        ad.setCancelable(false);
        ad.setNegativeButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = ad.create();
        alert.show();
    }
}
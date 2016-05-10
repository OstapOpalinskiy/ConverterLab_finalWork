package com.opalinskiy.ostap.converterlab;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.opalinskiy.ostap.converterlab.abstractActivities.AbstractMapActivity;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.utils.MapLoader;
import com.opalinskiy.ostap.converterlab.utils.connectUtils.ConnectionDetector;

public class MapActivity extends AbstractMapActivity implements LoaderManager.LoaderCallbacks<LatLng> {
    private GoogleMap map;
    private ConnectionDetector connectionDetector;
    private String city;
    private String address;
    private MapLoader mapLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isConnected()) {
            if (isGoogleMapsAvailable()) {
                setContentView(R.layout.activity_map);

                ((MapFragment) getFragmentManager().findFragmentById(R.id.google_map)).getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        map = googleMap;
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                        address = getIntent().getStringExtra("address");
                        city = getIntent().getStringExtra("city");

                        getLoaderManager().initLoader(Constants.MAP_LOADER_ID, null, MapActivity.this);
                        mapLoader.forceLoad();

                        ActionBar ab = getSupportActionBar();
                        ab.setTitle(city);
                        ab.setSubtitle(address);
                        ab.setDisplayHomeAsUpEnabled(true);
                    }
                });
            } else {
                showAlertDialog(R.string.no_map, R.string.no_map_msg);
            }
        } else {
            showAlertDialog(R.string.no_interent_title, R.string.no_internet_msg);
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

    @Override
    public Loader<LatLng> onCreateLoader(int id, Bundle args) {
        Log.d(Constants.LOG_TAG, "onCreate MapLoader");
        mapLoader = new MapLoader(this, city, address);
        return mapLoader;
    }

    @Override
    public void onLoadFinished(Loader<LatLng> loader, LatLng data) {
        Log.d(Constants.LOG_TAG, "onLoadFinished MapLoader");
        if (!(data.latitude == 0.0 && data.longitude == 0.0)) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(data, mapLoader.getZoom()));
        } else {
            showAlertDialog(R.string.no_interent_title, R.string.no_internet_msg);
        }
    }

    @Override
    public void onLoaderReset(Loader<LatLng> loader) {

    }
}
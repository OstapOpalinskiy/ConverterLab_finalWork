package com.opalinskiy.ostap.converterlab;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.opalinskiy.ostap.converterlab.abstractActivities.AbstractActionActivity;
import com.opalinskiy.ostap.converterlab.adapters.OrganisationsAdapter;
import com.opalinskiy.ostap.converterlab.api.Api;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.database.AsyncDbReader;
import com.opalinskiy.ostap.converterlab.interfaces.ConnectCallback;
import com.opalinskiy.ostap.converterlab.model.Organisation;
import com.opalinskiy.ostap.converterlab.model.DataResponse;
import com.opalinskiy.ostap.converterlab.database.DbManager;
import com.opalinskiy.ostap.converterlab.receivers.AlarmReceiver;
import com.opalinskiy.ostap.converterlab.services.LoaderService;
import com.opalinskiy.ostap.converterlab.utils.connectUtils.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AbstractActionActivity implements SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<List<Organisation>> {

    private List<Organisation> organisations;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DbManager dbManager;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Snackbar snackbar;
    private Loader<List<Organisation>> loader;
    private ProgressDialog ringProgressDialog;
    private ConnectionDetector connectionDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        startAlarmReceiver();
        loadDataFromServer();
        startRingProgress();
        updateNotification("Loading data...");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(Constants.LOG_TAG, "onTextChange");
        String criteria = newText.toLowerCase();
        List<Organisation> filteredList = new ArrayList();
        for (int i = 0; i < organisations.size(); i++) {
            if (organisations.get(i).getCity().toLowerCase().contains(criteria)
                    || organisations.get(i).getRegion().toLowerCase().contains(criteria)
                    || organisations.get(i).getTitle().toLowerCase().contains(criteria)) {
                filteredList.add(organisations.get(i));
            }
        }
        showList(filteredList);
        return false;
    }

    private void loadDataFromServer() {
        Api.getDataResponse(new ConnectCallback() {

            @Override
            public void onProgress(long percentage) {
                String message = "Progress:" + percentage + "%";
                updateNotification(message);
                snackbar.setText(message);
                snackbar.show();
            }

            @Override
            public void onSuccess(Object object) {
                Log.d(Constants.LOG_TAG, "On success");
                DataResponse dataResponse = (DataResponse) object;
                organisations = dataResponse.getOrganisations();
                showList(organisations);
                snackbar.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                ringProgressDialog.dismiss();
                notificationManager.cancel(0);
            }

            @Override
            public void onFailure() {
                Log.d(Constants.LOG_TAG, "onFailure in activity=");
                loader.forceLoad();
                notificationManager.cancel(0);
            }
        });
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_MA);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        prepareNotification();
        dbManager = new DbManager(this);
        dbManager.open();
        snackbar = Snackbar
                .make(swipeRefreshLayout, "", Snackbar.LENGTH_INDEFINITE);
        connectionDetector = new ConnectionDetector(this);
        getLoaderManager().initLoader(Constants.LOADER_ID, null, MainActivity.this);
    }

    private void startLoaderService() {
        Intent intent = new Intent(this, LoaderService.class);
        startService(intent);
    }

    private void refreshData() {
        loadDataFromServer();
    }

    private void showList(List<Organisation> list) {
        OrganisationsAdapter adapter = new OrganisationsAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void startAlarmReceiver() {
        Log.d(Constants.LOG_TAG, "starting alarm!!!");
        Intent alarm = new Intent(this, AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(), Constants.THIRTY_MINUTES, pendingIntent);
        } else {
            startLoaderService();
        }
    }

    private void prepareNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this)
                .setContentText("")
                .setContentTitle("Loading...")
                .setSmallIcon(R.drawable.ic_link)
                .setOngoing(false);
    }

    private void updateNotification(String text) {
        builder.setContentText(text);
        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
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
    public Loader<List<Organisation>> onCreateLoader(int id, Bundle args) {
        Log.d(Constants.LOG_TAG, "onCreateLoader");
        if (loader == null) {
            loader = new AsyncDbReader(this, dbManager);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Organisation>> loader, List<Organisation> data) {
        Log.d(Constants.LOG_TAG, "loaded list size:" + data.size());
        organisations = data;
        ringProgressDialog.dismiss();
        if (organisations.size() == 0) {
            showAlertDialog(R.string.no_data, R.string.no_data_msg);
        }
        snackbar.dismiss();
        showList(organisations);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<List<Organisation>> loader) {

    }

    private void startRingProgress() {
        ringProgressDialog = ProgressDialog.show(MainActivity.this,
                getString(R.string.loading), getString(R.string.loading_msg), true);
        ringProgressDialog.setCancelable(true);
    }
}


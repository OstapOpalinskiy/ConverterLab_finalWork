package com.opalinskiy.ostap.converterlab.database;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.model.Organisation;

import java.util.List;

public class AsyncDbReader extends AsyncTaskLoader<List<Organisation>> {
    List<Organisation> organisations;
    DbManager dbManager;

    public AsyncDbReader(Context context, DbManager dbManager) {
        super(context);
        this.dbManager = dbManager;
    }

    @Override
    public List<Organisation> loadInBackground() {
        Log.d(Constants.LOG_TAG, "load in background");
        organisations = dbManager.readListOfOrganisationsFromDB();
        dbManager.setRatesForList(organisations);
        return organisations;
    }
}

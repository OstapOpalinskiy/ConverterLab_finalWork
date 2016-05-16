package com.opalinskiy.ostap.converterlab.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.database.DbManager;
import com.opalinskiy.ostap.converterlab.model.Organisation;


import java.util.List;

public class AsyncDbReader extends AsyncTaskLoader<List<Organisation>> {
    private List<Organisation> organisations;
    private DbManager dbManager;

    public AsyncDbReader(Context context, DbManager dbManager) {
        super(context);
        this.dbManager = dbManager;
    }

    @Override
    public List<Organisation> loadInBackground() {
        organisations = dbManager.readListOfOrganisationsFromDB();
        dbManager.setRatesForList(organisations);
        return organisations;
    }
}

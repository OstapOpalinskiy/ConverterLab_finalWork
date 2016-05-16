package com.opalinskiy.ostap.converterlab;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.opalinskiy.ostap.converterlab.abstractActivities.AbstractActionActivity;
import com.opalinskiy.ostap.converterlab.adapters.DetailsAdapter;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.fragments.ShareFragment;
import com.opalinskiy.ostap.converterlab.model.Currency;
import com.opalinskiy.ostap.converterlab.model.Organisation;

import java.util.LinkedList;
import java.util.List;

public class DetailActivity extends AbstractActionActivity {

    private Organisation organisation;
    private FloatingActionsMenu floatingMenu;
    private FloatingActionButton buttonMap;
    private FloatingActionButton buttonLink;
    private FloatingActionButton buttonCall;
    private boolean isMenuOpened;
    private FrameLayout semiTransparentFrame;
    private ShareFragment dialog;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        setToolbar();
    }

    private void setToolbar() {
        ActionBar ab =  getSupportActionBar();
        ab.setTitle(organisation.getTitle());
        ab.setSubtitle(organisation.getCity());
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void init() {

        organisation = (Organisation) getIntent().getSerializableExtra(Constants.ORG_SERIALISE);
        floatingMenu = (FloatingActionsMenu) findViewById(R.id.floating_menu);
        buttonMap = (FloatingActionButton) findViewById(R.id.item_map);
        buttonLink = (FloatingActionButton) findViewById(R.id.item_link);
        buttonCall = (FloatingActionButton) findViewById(R.id.item_call);
        semiTransparentFrame = (FrameLayout) findViewById(R.id.fl_semi_transparent);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_DA);

        DetailsAdapter adapter = new DetailsAdapter(this, organisation,
                organisation.getCurrencies().getCurrencyList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        setMenuListeners();
    }

    private void setMenuListeners() {
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowMap(organisation);
            }
        });

        buttonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenLink(organisation);
            }
        });

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallNumber(organisation);
            }
        });

        floatingMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                isMenuOpened = true;
                semiTransparentFrame.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                isMenuOpened = false;
                semiTransparentFrame.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_share:
                showImageInDialog();
                break;
            case android.R.id.home:
                onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }

    private void showImageInDialog() {
        dialog = ShareFragment.newInstance(organisation);
        dialog.show(DetailActivity.this.getFragmentManager(), Constants.DIALOG_FRAGMENT_TAG);
    }
}

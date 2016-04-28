package com.opalinskiy.ostap.converterlab.customView;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opalinskiy.ostap.converterlab.R;
import com.opalinskiy.ostap.converterlab.model.Organisation;

/**
 * Created by Evronot on 26.04.2016.
 */
public class ShareImageTitle extends LinearLayout {
    private Organisation organisation;
    private TextView tvTitle;
    private TextView tvRegion;
    private TextView tvCity;

    public ShareImageTitle(Context context, Organisation organisation) {
        super(context);
        this.organisation = organisation;
      init();
        setText();
    }

    private void setText() {
        tvTitle.setText(organisation.getTitle());
        tvRegion.setText(organisation.getRegion());
        tvCity.setText(organisation.getCity());
    }

    private void init() {
        inflate(getContext(), R.layout.share_image_title, this);
        tvTitle = (TextView) findViewById(R.id.tv_title_SIT);
        tvRegion = (TextView) findViewById(R.id.tv_region_SIT);
        tvCity = (TextView) findViewById(R.id.tv_city_SIT);
    }



}

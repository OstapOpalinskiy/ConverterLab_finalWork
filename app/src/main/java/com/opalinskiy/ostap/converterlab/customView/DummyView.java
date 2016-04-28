package com.opalinskiy.ostap.converterlab.customView;

import android.content.Context;
import android.widget.LinearLayout;

import com.opalinskiy.ostap.converterlab.R;

/**
 * Created by Evronot on 28.04.2016.
 */
public class DummyView extends LinearLayout {
    public DummyView(Context context) {
        super(context);
        inflate(getContext(), R.layout.dummy_view, this);
    }
}

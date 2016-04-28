package com.opalinskiy.ostap.converterlab.customView;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opalinskiy.ostap.converterlab.R;
import com.opalinskiy.ostap.converterlab.model.Currency;
import com.opalinskiy.ostap.converterlab.model.Organisation;

import java.util.List;

/**
 * Created by Evronot on 26.04.2016.
 */
public class ShareImageBody extends LinearLayout {
    TextView currencyName;
    TextView askBid;
    Currency currency;

    public ShareImageBody(Context context, Currency currency) {
        super(context);
        this.currency = currency;
        init();
        setText();
    }

    private void init() {
        inflate(getContext(), R.layout.share_image_body, this);
        currencyName = (TextView) findViewById(R.id.tv_currency_name_SIB);
        askBid = (TextView) findViewById(R.id.tv_askBid_SIB);
    }

    private void setText() {
        currencyName.setText(currency.getIdCurrency());
        askBid.setText(currency.getAsk().substring(0, 5) + "/" + currency.getBid().substring(0,5));
    }
}

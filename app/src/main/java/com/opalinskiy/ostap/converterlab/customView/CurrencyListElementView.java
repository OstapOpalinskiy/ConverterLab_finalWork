package com.opalinskiy.ostap.converterlab.customView;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opalinskiy.ostap.converterlab.R;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.model.Currency;

/**
 * Created by Evronot on 24.04.2016.
 */
public class CurrencyListElementView extends RelativeLayout {
    private TextView tvCurrencyName;
    private TextView tvAsk;
    private TextView tvBid;
    private ImageView ivAsk;
    private ImageView ivBid;

    public CurrencyListElementView(Context context) {
        super(context);
        init();
    }

    public void setViews(Currency currency) {

        tvCurrencyName.setText(currency.getIdCurrency());
        tvAsk.setText(currency.getAsk());
        tvBid.setText(currency.getBid());

        if (currency.getChangeAsk().equals(Constants.INCREASE_KEY)) {
            tvAsk.setTextColor(getResources().getColor(R.color.courseColorGreen));
            ivAsk.setImageDrawable(getResources().getDrawable(R.drawable.ic_green_arrow_up));
        } else {
            tvAsk.setTextColor(getResources().getColor(R.color.currencyColor));
            ivAsk.setImageDrawable(getResources().getDrawable(R.drawable.ic_red_arrow_down));
        }

        if (currency.getChangeBid().equals(Constants.INCREASE_KEY)) {
            tvBid.setTextColor(getResources().getColor(R.color.courseColorGreen));
            ivBid.setImageDrawable(getResources().getDrawable(R.drawable.ic_green_arrow_up));
        } else {
            tvBid.setTextColor(getResources().getColor(R.color.currencyColor));
            ivBid.setImageDrawable(getResources().getDrawable(R.drawable.ic_red_arrow_down));
        }
    }

    private void init() {
        inflate(getContext(), R.layout.currency_list_element, this);
        tvCurrencyName = (TextView) findViewById(R.id.tv_currency_name_CLE);
        tvAsk = (TextView) findViewById(R.id.tv_ask_CLE);
        tvBid = (TextView) findViewById(R.id.tv_bid_CLE);
        ivAsk = (ImageView) findViewById(R.id.iv_ask_CLE);
        ivBid = (ImageView) findViewById(R.id.iv_bid_CLE);
    }
}

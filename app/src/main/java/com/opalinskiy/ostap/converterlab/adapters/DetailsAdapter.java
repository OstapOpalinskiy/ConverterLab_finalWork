package com.opalinskiy.ostap.converterlab.adapters;


import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.opalinskiy.ostap.converterlab.R;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.model.Currency;
import com.opalinskiy.ostap.converterlab.model.Organisation;

import java.util.LinkedList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {

    private Organisation organisation;
    private List<Currency> newList;
    private Context context;


    public DetailsAdapter(Context context, Organisation organisation, List<Currency> currencyList) {
        this.organisation = organisation;
        this.context = context;
        prepareList(currencyList);
    }

// creates copy of currency list with two dummy objects in the beginning of the list
    private void prepareList(List<Currency> currencyList) {
        newList = new LinkedList<Currency>();
        for(Currency c: currencyList){
            newList.add(c);
        }
        Log.d("TAG", "new list before: " + newList);
        newList.add(0, new Currency());
        newList.add(1, new Currency());
        Log.d("TAG", "new list after: " + newList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        Log.d("TAG", "onCreateViewHolder(), item type = " + itemType);
        View itemView;
        switch (itemType) {
            case 0:
                itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.org_info_layout, viewGroup, false);
                return new OrgInfoHolder(itemView);
            case 1:
                itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.table_title_layout, viewGroup, false);
                return new TableTitleHolder(itemView);
            default:
                itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.currency_list_element, viewGroup, false);
                return new ItemHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        Log.d("TAG", "onBindViewHolder(), i = " + i);
        switch (i) {
            case 0:
                OrgInfoHolder orgInfoHolder = (OrgInfoHolder) viewHolder;
                orgInfoHolder.tvTitle.setText(organisation.getTitle());
                orgInfoHolder.tvLink.setText(organisation.getLink());
                orgInfoHolder.tvRegion.setText(organisation.getRegion());
                orgInfoHolder.tvCity.setText(organisation.getCity());
                orgInfoHolder.tvAddress.setText(organisation.getAddress());
                orgInfoHolder.tvPhone.setText(organisation.getPhone());
                break;
            case 1:
                break;
            default:
                Currency currency = newList.get(i);
                Resources r = context.getResources();
                ItemHolder itemHolder = (ItemHolder) viewHolder;
                itemHolder.tvCurrencyName.setText(currency.getIdCurrency());
                itemHolder.tvAsk.setText(currency.getAsk());
                itemHolder.tvBid.setText(currency.getBid());

                if (currency.getChangeAsk().equals(Constants.INCREASE_KEY)) {
                    itemHolder.tvAsk.setTextColor(r.getColor(R.color.courseColorGreen));
                    itemHolder.ivAsk.setImageDrawable(r.getDrawable(R.drawable.ic_green_arrow_up));
                } else {
                    itemHolder.tvAsk.setTextColor(r.getColor(R.color.currencyColor));
                    itemHolder.ivAsk.setImageDrawable(r.getDrawable(R.drawable.ic_red_arrow_down));
                }

                if (currency.getChangeBid().equals(Constants.INCREASE_KEY)) {
                    itemHolder.tvBid.setTextColor(r.getColor(R.color.courseColorGreen));
                    itemHolder.ivBid.setImageDrawable(r.getDrawable(R.drawable.ic_green_arrow_up));
                } else {
                    itemHolder.tvBid.setTextColor(r.getColor(R.color.currencyColor));
                    itemHolder.ivBid.setImageDrawable(r.getDrawable(R.drawable.ic_red_arrow_down));
                }

                if(i == 2){
                    itemHolder.ivDivider.setImageResource(0);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View v) {
            super(v);
        }
    }

    class OrgInfoHolder extends MyViewHolder {
        private TextView tvTitle;
        private TextView tvLink;
        private TextView tvAddress;
        private TextView tvCity;
        private TextView tvRegion;
        private TextView tvPhone;

        public OrgInfoHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_OIL);
            tvLink = (TextView) itemView.findViewById(R.id.tv_link_OIL);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address_OIL);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city_OIL);
            tvRegion = (TextView) itemView.findViewById(R.id.tv_region_OIL);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone_OIL);
        }
    }

    class TableTitleHolder extends MyViewHolder {

        public TableTitleHolder(View itemView) {
            super(itemView);

        }
    }

    class ItemHolder extends MyViewHolder {
        private TextView tvCurrencyName;
        private TextView tvAsk;
        private TextView tvBid;
        private ImageView ivAsk;
        private ImageView ivBid;
        private ImageView ivDivider;

        public ItemHolder(View itemView) {
            super(itemView);
            tvCurrencyName = (TextView) itemView.findViewById(R.id.tv_currency_name_CLE);
            tvAsk = (TextView) itemView.findViewById(R.id.tv_ask_CLE);
            tvBid = (TextView) itemView.findViewById(R.id.tv_bid_CLE);
            ivAsk = (ImageView) itemView.findViewById(R.id.iv_ask_CLE);
            ivBid = (ImageView) itemView.findViewById(R.id.iv_bid_CLE);
            ivDivider = (ImageView) itemView.findViewById(R.id.divider_CLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

package com.opalinskiy.ostap.converterlab.adapters;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.opalinskiy.ostap.converterlab.abstractActivities.AbstractActionActivity;
import com.opalinskiy.ostap.converterlab.R;
import com.opalinskiy.ostap.converterlab.model.Organisation;

import java.util.List;

public class OrganisationsAdapter extends RecyclerView.Adapter<OrganisationsAdapter.MyViewHolder> implements Filterable {
    private final AbstractActionActivity handler;
    private List<Organisation> organisations;
    private Organisation organisation;


    public OrganisationsAdapter(AbstractActionActivity handler, List<Organisation> contactList) {
        this.handler = handler;
        this.organisations = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_element_layout, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {

        organisation = organisations.get(i);

        viewHolder.tvTitle.setText(organisation.getTitle());
        viewHolder.tvRegion.setText(organisation.getRegion());
        if (!organisation.getRegion().equals(organisation.getCity())) {
            viewHolder.tvCity.setText(organisation.getCity());
        }
        viewHolder.tvPhone.setText("Тел.: " + organisation.getPhone());
        viewHolder.tvAddress.setText("Адрес : " + organisation.getAddress());

    }

    @Override
    public int getItemCount() {
        return organisations == null ? 0 : organisations.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements TabLayout.OnTabSelectedListener {

        private TextView tvTitle;
        private TextView tvRegion;
        private TextView tvCity;
        private TextView tvPhone;
        private TextView tvAddress;
        private TabLayout tabLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.txtOrgTitle_IL);
            tvRegion = (TextView) itemView.findViewById(R.id.txtRegion_IL);
            tvCity = (TextView) itemView.findViewById(R.id.txtCity_IL);
            tvPhone = (TextView) itemView.findViewById(R.id.txtPhone_IL);
            tvAddress = (TextView) itemView.findViewById(R.id.txtAddress_IL);
            tabLayout = (TabLayout) itemView.findViewById(R.id.tabsLayout_IL);

            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_link));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_map));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_phone));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_next));

            tabLayout.setOnTabSelectedListener(this);
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            checkTabSelected(tab, organisations.get(getAdapterPosition()));
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            checkTabSelected(tab, organisations.get(getAdapterPosition()));
        }
    }

    private void checkTabSelected(TabLayout.Tab tab, Organisation organisation) {
        switch (tab.getPosition()) {
            case 0:
                handler.onOpenLink(organisation);
                break;
            case 1:
                handler.onShowMap(organisation);
                break;
            case 2:
                handler.onCallNumber(organisation);
                break;
            case 3:
                handler.onShowDetails(organisation);
                break;
        }
    }
}
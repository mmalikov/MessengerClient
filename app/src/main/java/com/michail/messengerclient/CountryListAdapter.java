package com.michail.messengerclient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.TreeMap;

/**
 * Created by mmalykov on 8/10/15.
 */
public class CountryListAdapter extends BaseAdapter {

    private TreeMap<String, CountryCodes> mTreeMap;
    private LayoutInflater mInflater;

    private String[] stringKeys;

    public CountryListAdapter(Context mContext, TreeMap<String, CountryCodes> mTreeMap) {
        this.mTreeMap = mTreeMap;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mTreeMap.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = mInflater.inflate(R.layout.country_item, null);

        TextView countryName = (TextView) view.findViewById(R.id.countryName);
        TextView phoneCode = (TextView) view.findViewById(R.id.phoneCode);

        Object[] keys = mTreeMap.keySet().toArray();
        stringKeys = convertToStringArray(keys);

        countryName.setText(stringKeys[position]);
        phoneCode.setText(mTreeMap.get(stringKeys[position]).getPhoneCode());

        return view;
    }

    @Override
    public Object getItem(int position) {
        return mTreeMap.get(stringKeys[position]);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private String[] convertToStringArray(Object[] objArray) {
        String[] strArray = new String[objArray.length];

        for (int i = 0; i < objArray.length; i++) {
            strArray[i] = new String(objArray[i].toString());
        }

        return strArray;
    }
}

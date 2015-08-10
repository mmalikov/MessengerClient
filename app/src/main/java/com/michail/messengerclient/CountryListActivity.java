package com.michail.messengerclient;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * Created by mmalykov on 8/7/15.
 */
public class CountryListActivity extends ActionBarActivity {

    private ActionBar actionBar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_list_layout);

        actionBar = getSupportActionBar();
        listView = (ListView) findViewById(R.id.coutriesListView);

        actionBar.setTitle(getResources().getString(R.string.choose_country));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar)));

        /* SET CUSTOM ADAPTER FOR LIST VIEW*/
        CountriesHolder countries = CountriesHolder.getInstance(getApplicationContext());
        CountryListAdapter mAdapter = new CountryListAdapter(getApplicationContext(), countries.getNameCodesList());
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String countryName = (String) ((TextView) view.findViewById(R.id.countryName)).getText();
                String phoneCode = (String) ((TextView) view.findViewById(R.id.phoneCode)).getText();

                Intent result = new Intent();
                result.putExtra(CountriesHolder.ATTRIBUTE_NAME, countryName);
                result.putExtra(CountriesHolder.ATTRIBUTE_PHONE_CODE, phoneCode);

                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
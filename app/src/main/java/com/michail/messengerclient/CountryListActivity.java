package com.michail.messengerclient;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by mmalykov on 8/7/15.
 */
public class CountryListActivity extends ActionBarActivity {

    private ActionBar mActionBar;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_list_layout);

        mActionBar = getSupportActionBar();
        mListView = (ListView) findViewById(R.id.coutriesListView);

        mActionBar.setTitle(getResources().getString(R.string.choose_country));
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar)));

        CountriesHolder countries = CountriesHolder.getInstance(getApplicationContext());
        CountryListAdapter mAdapter = new CountryListAdapter(getApplicationContext(), countries.getNameCodesList());
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
package com.michail.messengerclient;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mmalykov on 8/7/15.
 */
public class CountryListActivity extends ActionBarActivity {

    ActionBar actionBar;
    ListView listView;
    public ArrayList<HashMap<String, String>> name_phoneCode_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_list_layout);

        actionBar = getSupportActionBar();

        CountriesHolder countryList = CountriesHolder.getInstance(getApplicationContext());
        name_phoneCode_list = countryList.name_phoneCode_list;

        listView = (ListView) findViewById(R.id.coutriesListView);


        actionBar.setTitle("Choose a country");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5b95c2")));


        SimpleAdapter mAdapter = new SimpleAdapter(CountryListActivity.this, name_phoneCode_list, R.layout.country_item,
                new String[]{countryList.ATTRIBUTE_NAME, countryList.ATTRIBUTE_PHONE_CODE}, new int[]{R.id.countryName, R.id.countryCode});

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String countryName = (String) ((TextView) view.findViewById(R.id.countryName)).getText();
                String phoneCode = (String) ((TextView) view.findViewById(R.id.countryCode)).getText();

                Intent result = new Intent();
                result.putExtra(CountriesHolder.ATTRIBUTE_NAME, countryName);
                result.putExtra(CountriesHolder.ATTRIBUTE_PHONE_CODE, phoneCode);

                setResult(RESULT_OK, result);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_country_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_back) {
//            return true;
//            Toast.makeText(getApplicationContext(), "BackBtn Pressed", Toast.LENGTH_SHORT).show();
//        }

        return super.onOptionsItemSelected(item);
    }


}
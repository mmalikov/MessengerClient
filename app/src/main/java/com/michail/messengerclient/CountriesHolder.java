package com.michail.messengerclient;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;

/**
 * Created by mmalykov on 8/8/15.
 */

public class CountriesHolder {

    private static CountriesHolder instance;

    public static final String ATTRIBUTE_NAME = "NAME";
    public static final String ATTRIBUTE_PHONE_CODE = "PHONE_CODE";
    public static final String ATTRIBUTE_COUNTRY_CODE = "COUNTRY_CODE";
    public static final String PLUS = "+";

    //Get name by phone code
    private HashMap<String, String> phoneCodeNameList;

    //Get phone code by name
    //Get country code by name
    private TreeMap<String, CountryCodes> nameCodesList;

    private CountriesHolder(Context context) {
        nameCodesList = new TreeMap<>();
        phoneCodeNameList = new HashMap<>();

        try {
            InputStream stream = context.getAssets().open("countries.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] countryInfo = line.split(";");
                String phoneCode = PLUS + countryInfo[0];

                Locale locale = new Locale(Locale.getDefault().getDisplayLanguage(),countryInfo[1]);
                String countryName = locale.getDisplayCountry();

                CountryCodes codes = new CountryCodes(phoneCode, countryInfo[1]);
                nameCodesList.put(countryName, codes);

                phoneCodeNameList.put(phoneCode, countryName);
            }
            reader.close();
            stream.close();
        } catch (Exception e) {
            Log.e("MessageClient", "FILE NOT READ");
        }

    }

    public static synchronized CountriesHolder getInstance(Context context) {
        if (instance == null) {
            instance = new CountriesHolder(context);
        }
        return instance;
    }

    public String getName(Context context, String phoneCode) {
        String result  = phoneCodeNameList.get(phoneCode);
        if(result == null) result = context.getResources().getString(R.string.wrong_code);

        return result;
    }

    public String getCountryCode(String name) {
        return nameCodesList.get(name).getCountryCode();
    }

    public TreeMap<String, CountryCodes> getNameCodesList() {
        return nameCodesList;
    }
}

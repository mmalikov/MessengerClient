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

                CountryCodes codes = new CountryCodes(phoneCode, countryInfo[1]);
                nameCodesList.put(countryInfo[2], codes);

                phoneCodeNameList.put(phoneCode, countryInfo[2]);
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

    public String getName(String phoneCode) {
        return phoneCodeNameList.get(phoneCode);
    }

    public String getCountryCode(String name) {
        return nameCodesList.get(name).getCountryCode();
    }

    public TreeMap<String, CountryCodes> getNameCodesList() {
        return nameCodesList;
    }
}

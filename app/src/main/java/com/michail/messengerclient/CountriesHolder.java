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

/**
 * Created by mmalykov on 8/8/15.
 */

public class CountriesHolder {

    private static CountriesHolder instance;

    public static final String ATTRIBUTE_NAME = "NAME";
    public static final String ATTRIBUTE_PHONE_CODE = "PHONE_CODE";
    public static final String ATTRIBUTE_COUNTRY_CODE = "COUNTRY_CODE";

    public ArrayList<HashMap<String, String>> name_phoneCode_list;
    public ArrayList<HashMap<String, String>> name_countryCode_list;

    private CountriesHolder(Context context) {
        try {
            InputStream stream = context.getAssets().open("countries.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;

            name_phoneCode_list = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] args = line.split(";");

                HashMap<String, String> mHashMap = new HashMap<>();
                mHashMap.put(ATTRIBUTE_NAME, args[2]);
                mHashMap.put(ATTRIBUTE_PHONE_CODE, "+" + args[0]);

                name_phoneCode_list.add(mHashMap);
            }

            reader.close();
            stream.close();
        } catch (Exception e) {
            Log.e("Message Client", "FILE NOT READ");
        }

        try {
            InputStream stream = context.getAssets().open("countries.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;

             name_countryCode_list = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] args = line.split(";");

                HashMap<String, String> mHashMap = new HashMap<>();
                mHashMap.put(ATTRIBUTE_NAME, args[2]);
                mHashMap.put(ATTRIBUTE_COUNTRY_CODE, "+" + args[1]);

                name_countryCode_list.add(mHashMap);
            }

            reader.close();
            stream.close();
        } catch (Exception e) {
            Log.e("Message Client", "FILE NOT READ");
        }

        Collections.sort(name_phoneCode_list, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                return lhs.get(ATTRIBUTE_NAME).compareTo(rhs.get(ATTRIBUTE_NAME));
            }
        });

        Collections.sort(name_countryCode_list, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                return lhs.get(ATTRIBUTE_NAME).compareTo(rhs.get(ATTRIBUTE_NAME));
            }
        });
    }

    public static synchronized CountriesHolder getInstance(Context context) {
        if(instance == null){
            instance = new CountriesHolder(context);
        }

        return instance;
    }

    public String getName(String code) {
        for (HashMap<String, String> country : name_phoneCode_list) {
            if (country.containsValue(code)) {
                for (String key : country.keySet()) {
                    if (country.get(key).equals(code)) {
                        return country.get(ATTRIBUTE_NAME);
                    }
                }
            }
        }

        return "Wrong code of country";
    }

    public String getCode(String name) {
        for (HashMap<String, String> country : name_phoneCode_list) {
            if (country.containsKey(name)) {
                return country.get(ATTRIBUTE_PHONE_CODE);
            }
        }

        return "Wrong code of country";
    }

    public String getCountryCode(String name){
        for (HashMap<String,String> country: name_countryCode_list){
            if(country.containsValue(name)){
                return country.get(ATTRIBUTE_COUNTRY_CODE);
            }
        }

        return "ERROR";
    }

}

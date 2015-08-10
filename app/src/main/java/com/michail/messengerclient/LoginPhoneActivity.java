package com.michail.messengerclient;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

/**
 * Created by Michail on 05.08.2015.
 */
public class LoginPhoneActivity extends ActionBarActivity {

    private ActionBar actionBar;
    private Button chooseCountryBtn;
    private EditText phoneCodeField;
    private EditText phoneNumberField;

    private boolean isChanged;

    public static final String ATTRIBUTE_PHONE_NUMBER = "PHONE_NUMBER";
    public static final String ATTRIBUTE_PHONE_CODE = "PHONE_CODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_phone_layout);

        actionBar = getSupportActionBar();
        phoneCodeField = (EditText) findViewById(R.id.phoneCode);
        chooseCountryBtn = (Button) findViewById(R.id.countryBtn);
        phoneNumberField = (EditText) findViewById(R.id.phoneNumber);

        actionBar.setTitle(getResources().getString(R.string.login_phone_action_bar_title));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar)));

        chooseCountryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPhoneActivity.this, CountryListActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        /*

        */
        TextWatcher phoneCodeFildTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = phoneCodeField.getText().toString();

                if (text.equals(CountriesHolder.PLUS)) {
                    chooseCountryBtn.setText(getResources().getString(R.string.choose_country));// DON`T WORK
                }

                if (isChanged) {
                    isChanged = false;
                } else {
                    CountriesHolder countriesHolder = CountriesHolder.getInstance(getApplicationContext());

                    String phoneCode = phoneCodeField.getText().toString();
                    chooseCountryBtn.setText(countriesHolder.getName(phoneCode));
                }
            }
        };
        phoneCodeField.addTextChangedListener(phoneCodeFildTextWatcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        isChanged = true;

        chooseCountryBtn.setText(data.getStringExtra(CountriesHolder.ATTRIBUTE_NAME));
        phoneCodeField.setText(data.getStringExtra(CountriesHolder.ATTRIBUTE_PHONE_CODE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login_phone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_check) {

            String ph = phoneCodeField.getText().toString() + phoneNumberField.getText().toString();
            String cn = chooseCountryBtn.getText().toString();

            if (isValidNumber(ph, cn)) {
                Intent intent = new Intent(LoginPhoneActivity.this, ActivationCodeActivity.class);

                intent.putExtra(ATTRIBUTE_PHONE_NUMBER, phoneNumberField.getText().toString());
                intent.putExtra(ATTRIBUTE_PHONE_CODE, phoneCodeField.getText().toString());

                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid_number), Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isValidNumber(String number, String countryName) {
        Phonenumber.PhoneNumber numberProto = null;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        CountriesHolder countriesHolder = CountriesHolder.getInstance(getApplicationContext());

        String countryCode = countriesHolder.getCountryCode(countryName);

        try {
            numberProto = phoneUtil.parse(number, countryCode);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        return phoneUtil.isValidNumber(numberProto);
    }
}

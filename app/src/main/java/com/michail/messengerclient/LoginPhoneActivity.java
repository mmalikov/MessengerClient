package com.michail.messengerclient;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
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

    ActionBar actionBar;
    Button chooseCountryBtn;
    EditText phoneCode;
    EditText phoneNumber;

    boolean isChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_phone_layout);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Your phone");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5b95c2")));

        phoneCode = (EditText) findViewById(R.id.countryCode);

        chooseCountryBtn = (Button) findViewById(R.id.countryBtn);
        chooseCountryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPhoneActivity.this, CountryListActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        phoneCode.addTextChangedListener(new TextWatcher() {
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
                String text = phoneCode.getText().toString();
                if (text.equals("+")) {
                    chooseCountryBtn.setText("Choose a country");
                }

                if (isChanged) {
                    isChanged = false;
                } else {
                    CountriesHolder countriesList = CountriesHolder.getInstance(getApplicationContext());

                    String code = phoneCode.getText().toString();
                    chooseCountryBtn.setText(countriesList.getName(code));

//                  isChanged = false;
                }

            }
        });

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);

        phoneNumber.addTextChangedListener(new TextWatcher() {
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
                //format number!!!
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        isChanged = true;

        chooseCountryBtn.setText(data.getStringExtra(CountriesHolder.ATTRIBUTE_NAME));
        phoneCode.setText(data.getStringExtra(CountriesHolder.ATTRIBUTE_PHONE_CODE));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_phone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_check) {

            String ph = phoneCode.getText().toString() + phoneNumber.getText().toString();
            String cn = chooseCountryBtn.getText().toString();

            if (isValidNumber(ph, cn)) {
                Intent intent = new Intent(LoginPhoneActivity.this, ActivationCodeActivity.class);
                intent.putExtra("PHONE", phoneNumber.getText().toString());
                intent.putExtra("CODE", phoneCode.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Enter valid phone number", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isValidNumber(String number, String countryName) {
//+13522134496
        Phonenumber.PhoneNumber NumberProto = null;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        CountriesHolder c = CountriesHolder.getInstance(getApplicationContext());

        String countryCode = c.getCountryCode(countryName);

        try {
            NumberProto = phoneUtil.parse(number, countryCode);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        boolean isValid = phoneUtil.isValidNumber(NumberProto); // returns true or false

        return isValid;
    }
}

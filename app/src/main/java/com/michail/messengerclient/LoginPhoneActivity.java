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

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

/**
 * Created by Michail on 05.08.2015.
 */
public class LoginPhoneActivity extends ActionBarActivity {

    private ActionBar mActionBar;
    private Button mChooseCountryBtn;
    private EditText mPhoneCodeField;
    private EditText mPhoneNumberField;

    private boolean isChanged;

    public static final String ATTRIBUTE_PHONE_NUMBER = "PHONE_NUMBER";
    public static final String ATTRIBUTE_PHONE_CODE = "PHONE_CODE";

    private final int MINIMUM_NUMBER_LENGTH = 5;

    private TextWatcher phoneCodeFieldTextWatcher = new TextWatcher() {
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
            String text = mPhoneCodeField.getText().toString();

            if (text.equals(CountriesHolder.PLUS)) {
                mChooseCountryBtn.setText(getResources().getString(R.string.choose_country));
                return;
            }
            // Needed for prevent updating text when country was chosen from list
            if (isChanged) {
                isChanged = false;
            } else {
                CountriesHolder countriesHolder = CountriesHolder.getInstance(getApplicationContext());

                String phoneCode = mPhoneCodeField.getText().toString();
                mChooseCountryBtn.setText(countriesHolder.getName(getApplicationContext(), phoneCode));
            }
        }
    };

    public static final String TAG = LoginPhoneActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_phone_layout);

        mActionBar = getSupportActionBar();
        mPhoneCodeField = (EditText) findViewById(R.id.phoneCode);
        mChooseCountryBtn = (Button) findViewById(R.id.countryBtn);
        mPhoneNumberField = (EditText) findViewById(R.id.phoneNumber);

        mActionBar.setTitle(getResources().getString(R.string.login_phone_action_bar_title));
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar)));

        mChooseCountryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPhoneActivity.this, CountryListActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        mPhoneCodeField.addTextChangedListener(phoneCodeFieldTextWatcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        isChanged = true;

        mChooseCountryBtn.setText(data.getStringExtra(CountriesHolder.ATTRIBUTE_NAME));
        mPhoneCodeField.setText(data.getStringExtra(CountriesHolder.ATTRIBUTE_PHONE_CODE));
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
        Log.i(TAG,"CHECK BUTTON PRESSED");

            String pCode = mPhoneCodeField.getText().toString();
            String pNumber = mPhoneNumberField.getText().toString();
            String countryName = mChooseCountryBtn.getText().toString();

            String fullNumber = pCode + pNumber;

            if (pCode.equals(null)
                    || pCode.equals(CountriesHolder.PLUS)
                    || pNumber.equals(null)
                    || countryName.equals(getResources().getString(R.string.wrong_code))
                    || countryName.equals(getResources().getString(R.string.choose_country))) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_fill_fields), Toast.LENGTH_SHORT).show();

                return super.onOptionsItemSelected(item);
            } else if (isValidNumber(fullNumber, countryName)) {
                sendRequestActivationCode(fullNumber);
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid_number), Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isValidNumber(String number, String countryName) {
        if (countryName.equals(null) || number.length() <= MINIMUM_NUMBER_LENGTH) return false;

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

    private void sendRequestActivationCode(String phoneNumber) {
        Client client = ((MessengerApplication) getApplication()).getClient();

        client.send(new TdApi.AuthSetPhoneNumber(phoneNumber), new ResultHandler() {
            @Override
            public void onResponse(TdApi.TLObject object, TdApi.Error error) {
//                Log.i(TAG, object.toString());
                if (error != null) {
                    Log.i(TAG, error.toString());
                    LoginPhoneActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.i(TAG, "START ACTIVATION CODE");

                    Intent intent = new Intent(LoginPhoneActivity.this, ActivationCodeActivity.class);

                    intent.putExtra(ATTRIBUTE_PHONE_NUMBER, mPhoneNumberField.getText().toString());
                    intent.putExtra(ATTRIBUTE_PHONE_CODE, mPhoneCodeField.getText().toString());

                    startActivity(intent);
                }
            }
        });
    }
}

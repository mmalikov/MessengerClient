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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

/**
 * Created by mmalykov on 8/9/15.
 */
public class ActivationCodeActivity extends ActionBarActivity {

    private ActionBar mActionBar;
    private TextView mExplainText;
    private EditText mVerificatonCode;
    private final String TAG = ActivationCodeActivity.class.getSimpleName();

    private TextWatcher mTextWatcher = new TextWatcher() {
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
            if (s.length() != 5) {
                Log.i(TAG, "length short");
                Toast.makeText(getApplicationContext(), "Short code", Toast.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "setCode called");
                setCode(s.toString());
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activation_code_layout);

        mActionBar = getSupportActionBar();
        mExplainText = (TextView) findViewById(R.id.explainText);
        mVerificatonCode = (EditText) findViewById(R.id.activationCode);

        mActionBar.setTitle(getResources().getString(R.string.activation_code_action_bar_title));
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar)));

        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra(LoginPhoneActivity.ATTRIBUTE_PHONE_NUMBER);
        String phoneCode = intent.getStringExtra(LoginPhoneActivity.ATTRIBUTE_PHONE_CODE);

        String text = getApplicationContext().getString(R.string.activation_code_explain) + " " + phoneCode + " " + phoneNumber;
        mExplainText.setText(text);

        mVerificatonCode.addTextChangedListener(mTextWatcher);
    }

    private void setCode(String activationCode) {
        Client client = ((MessengerApplication) getApplication()).getClient();

        client.send(new TdApi.AuthSetCode(activationCode), new ResultHandler() {
            @Override
            public void onResponse(TdApi.TLObject object, TdApi.Error error) {
                if (error != null) {
                    ActivationCodeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "AuthState FAIL", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if (object.getConstructor() == TdApi.AuthStateOk.CONSTRUCTOR) {
                        // ok, go to conversation screen
                        ActivationCodeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "AuthState OK", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (object.getConstructor() == TdApi.AuthStateWaitSetName.CONSTRUCTOR) {
                        // wait set name
                        ActivationCodeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "AuthState WaitSetName", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
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
            Toast.makeText(getApplicationContext(), "CHECK Btn PRESSED", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}



package com.michail.messengerclient;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mmalykov on 8/9/15.
 */
public class ActivationCodeActivity extends ActionBarActivity {

    ActionBar actionBar;
    TextView explainText;
    EditText activationCode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activation_code_layout);

        actionBar = getSupportActionBar();
        explainText = (TextView) findViewById(R.id.explainText);
        activationCode = (EditText) findViewById(R.id.activationCode);

        actionBar.setTitle("Activation code");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5b95c2")));

        Intent intent = getIntent();

        String phoneNumber = intent.getStringExtra("PHONE");
        String countryCode = intent.getStringExtra("CODE");

        String text = getApplicationContext().getString(R.string.activation_code_explain) + " " + countryCode + " " + phoneNumber;

        explainText.setText(text);
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
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}


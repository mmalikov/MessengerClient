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
import android.widget.Toast;

/**
 * Created by mmalykov on 8/9/15.
 */
public class ActivationCodeActivity extends ActionBarActivity {

    private ActionBar actionBar;
    private TextView explainText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activation_code_layout);

        actionBar = getSupportActionBar();
        explainText = (TextView) findViewById(R.id.explainText);

        actionBar.setTitle(getResources().getString(R.string.activation_code_action_bar_title));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar)));

        Intent intent = getIntent();

        String phoneNumber = intent.getStringExtra(LoginPhoneActivity.ATTRIBUTE_PHONE_NUMBER);
        String phoneCode = intent.getStringExtra(LoginPhoneActivity.ATTRIBUTE_PHONE_CODE);

        String text = getApplicationContext().getString(R.string.activation_code_explain) + " " + phoneCode + " " + phoneNumber;
        explainText.setText(text);
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


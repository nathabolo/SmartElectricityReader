package com.example.android.electricreader;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.N;

public class OctActivity extends AppCompatActivity implements View.OnClickListener {

    //UI References/Global objects declared here

    private TextView selcetDate;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oct);


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void findViewsById() {
        selcetDate = (TextView) findViewById(R.id.selectDay);
        selcetDate.setInputType(InputType.TYPE_NULL);
        selcetDate.requestFocus();

    }

    private void setDateTimeField() {
        selcetDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                selcetDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }


    public void onClick(View view) {
        if(view == selcetDate) {
            fromDatePickerDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second_navigation_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), UserInputActivity.class);
        startActivityForResult(myIntent, 0);

        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            logout();
        }

        //When Settings action item is clicked
        else if (id == R.id.nav_settings) {
            //Create Intent for Settings Activity
            Intent settingsIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            //Start Contact Intent
            startActivity(settingsIntent);

            return true;
        }

        //When My Account action item is clicked
        else {
            if (id == R.id.nav_account) {
                //Create Intent for Account Activity
                Intent settingdIntent = new Intent(Settings.ACTION_ADD_ACCOUNT);
                //Start Contact Intent
                startActivity(settingdIntent);
                return true;
            }
        }
        return true;

    }

    //Log users out from their devices not firebase

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(OctActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

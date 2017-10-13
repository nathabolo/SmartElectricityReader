package com.example.android.electricreader;


import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UserInputActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    //UI References/Global objects declared here

    //Initialize objects
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESULT_PICK_CONTACT = 1111;
    private TextView selcetDate;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView contact;
    private TextView textViewName;
    private TextView camera;
    private ImageButton btn_reading;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView navList;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private ArrayList<NavigationDrawer> navArray;
    private NavDrawerListAdapter navDrawerListAdapter;
    private NavigationDrawer navigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navList = (ListView) findViewById(R.id.navigationList);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        findViewsById();

        setDateTimeField();


        //Anyline licence

        String anylineLicenceKey = getString(R.string.anyline_licence);

        //Create a list of months in an array
        navArray = new ArrayList<>();
        navigationDrawer = new NavigationDrawer(R.string.months);
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.january, "January");
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.february, "February");
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.march, "March");
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.april, "April");
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.may1, "May");
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.june, "June");
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.july, "July");
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.august, "August");
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.september, "September");
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.october, "October");
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.november, "November");
        navArray.add(navigationDrawer);
        navigationDrawer = new NavigationDrawer(R.drawable.december, "December");

        navArray.add(navigationDrawer);
        navList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        //Create an array adapter
        navDrawerListAdapter = new NavDrawerListAdapter(this, navArray);
        navList.setAdapter(navDrawerListAdapter);
        navList.setOnItemClickListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        fragmentManager = getSupportFragmentManager();


        //Find the id of edit text contact

        contact = (TextView) findViewById(R.id.contact);
        textViewName = (TextView) findViewById(R.id.name);
        camera = (TextView) findViewById(R.id.currentReading);

        //Find the id's of edit text and button in when a camera button is clicked

        btn_reading = (ImageButton) findViewById(R.id.btn_reading);
        btn_reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

    }

    public void sendNotification(View view) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);

//Create the intent that’ll fire when the user taps the notification//

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ekurhuleni.gov.za/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.drawable.ic_payment_white_48dp);
        mBuilder.setContentTitle("Open Urgently!!!");
        mBuilder.setContentText("Make Payments before the 1st of next months");
        mBuilder.setPriority(Notification.PRIORITY_HIGH);

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }

    private void findViewsById() {
        selcetDate = (TextView) findViewById(R.id.selectDate);
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

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }


    public void onClick(View view) {
        if (view == selcetDate) {
            fromDatePickerDialog.show();
        }
    }

    private void loadSelection(int i) {

        switch (i) {

            case 1:
                //Load and initialize my current fragment

                FirstFragment firstFragment = new FirstFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, firstFragment);
                fragmentTransaction.commit();
                Intent intentJan = new Intent(UserInputActivity.this, JanActivity.class);
                startActivity(intentJan);
                break;

            case 2:

                //Load and initialize my current fragment

                SecondFragment secondFragment = new SecondFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, secondFragment);
                fragmentTransaction.commit();
                Intent intentFeb = new Intent(UserInputActivity.this, FebActivity.class);
                startActivity(intentFeb);

                break;

            case 3:

                //Load and initialize my current fragment

                ThirdFragment thirdFragment = new ThirdFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, thirdFragment);
                fragmentTransaction.commit();
                Intent intentMarch = new Intent(UserInputActivity.this, MarchActivity.class);
                startActivity(intentMarch);

                break;

            case 4:

                //Load and initialize my current fragment

                FourthFragment fourthFragment = new FourthFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, fourthFragment);
                fragmentTransaction.commit();
                Intent intentApril = new Intent(UserInputActivity.this, AprilActivity.class);
                startActivity(intentApril);

                break;

            case 5:

                //Load and initialize my current fragment

                FifthFragment fifthFragment = new FifthFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, fifthFragment);
                fragmentTransaction.commit();
                Intent intentMay = new Intent(UserInputActivity.this, MayActivity.class);
                startActivity(intentMay);

                break;

            case 6:

                //Load and initialize my current fragment

                SixthFragment sixthFragment = new SixthFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, sixthFragment);
                fragmentTransaction.commit();
                Intent intentJune = new Intent(UserInputActivity.this, JuneActivity.class);
                startActivity(intentJune);

                break;

            case 7:

                //Load and initialize my current fragment

                SeventhFragment seventhFragment = new SeventhFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, seventhFragment);
                fragmentTransaction.commit();
                Intent intentJuly = new Intent(UserInputActivity.this, JulyActivity.class);
                startActivity(intentJuly);

                break;

            case 8:

                //Load and initialize my current fragment

                EithFragment eithFragment = new EithFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, eithFragment);
                fragmentTransaction.commit();
                Intent intentAug = new Intent(UserInputActivity.this, AugustActivity.class);
                startActivity(intentAug);

                break;

            case 9:

                //Load and initialize my current fragment

                NinethFragment ninethFragment = new NinethFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, ninethFragment);
                fragmentTransaction.commit();
                Intent intentSep = new Intent(UserInputActivity.this, SepActivity.class);
                startActivity(intentSep);

                break;

            case 10:

                //Load and initialize my current fragment

                TenthFragment tenthFragment = new TenthFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, tenthFragment);
                fragmentTransaction.commit();
                Intent intentOct = new Intent(UserInputActivity.this, OctActivity.class);
                startActivity(intentOct);

                break;

            case 11:

                //Load and initialize my current fragment

                EleventhFragment eleventhFragment = new EleventhFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, eleventhFragment);
                fragmentTransaction.commit();
                Intent intentNov = new Intent(UserInputActivity.this, NovActivity.class);
                startActivity(intentNov);

                break;

            case 12:

                //Load and initialize my current fragment

                TwelvethFragment twelvethFragment = new TwelvethFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameHolder, twelvethFragment);
                fragmentTransaction.commit();
                Intent intentDec = new Intent(UserInputActivity.this, DecActivity.class);
                startActivity(intentDec);

                break;
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second_navigation_menu, menu);
        return true;

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //Determine if the drawer is closed or opened.  Open drawer or close drawer
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {

            if (mDrawerLayout.isDrawerOpen(navList)) {
                mDrawerLayout.closeDrawer(navList);
            } else {
                mDrawerLayout.openDrawer(navList);
            }
        }

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

        //When My account action item is clicked
        else if (id == R.id.nav_account) {
            //Create Intent for Account Activity
            Intent accoountIntent = new Intent(Settings.ACTION_ADD_ACCOUNT);
            //Start Contact Intent
            startActivity(accoountIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    //Log users out from their devices not firebase

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(UserInputActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    public void pickContact(View v) {
        Intent contactPickerItent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerItent, RESULT_PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                btn_reading.setImageBitmap(imageBitmap);
            }

        }
    }

    /**
     * Query the Uri and read contact details. Handle the picked contact data.
     *
     * @param data
     */
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phone = null;
            String name = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            // column index of the contact name
            name = cursor.getString(nameIndex);
            // column index of the contact phone
            phone = cursor.getString(phoneIndex);
            // Set the value to the editText
            textViewName.setText(name);
            // Set the value to the editText
            contact.setText(phone + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        loadSelection(position);
        mDrawerLayout.closeDrawer(navList);

    }
}
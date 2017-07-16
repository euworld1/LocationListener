//Version 2


package com.example.euworld.udemylocation3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;




public class MainActivity extends AppCompatActivity { //extends just means attached or adds on to the class. Implement is for interfaces. Inheritance (related to and shares assets)

    private Button sb;
    private Button reset;
    private Button b; //declaration of a class variable? Object. This has to be an object that loads into memory. Intro to the players. These are instance variables. Fields
    private TextView t;//this is an instance variable assignment of t and b
    private LocationManager locationManager; //you call this guy
    private LocationListener listener; // minion

    //////////////////
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {//onCreate builds the GUI and to custom configure
        super.onCreate(savedInstanceState); //this line has something to do with AppCompatActivity extension
        setContentView(R.layout.activity_main); //in AppCompatActivity; R.layout.activity_main = view in setContentView(View view)


        t = (EditText) findViewById(R.id.editText);// the () is "casting"
        b = (Button) findViewById(R.id.activateButton);
        sb = (Button) findViewById(R.id.stopListen);
        reset = (Button) findViewById(R.id.reset);


        //Geocoder gcd = new Geocoder(context, Locale.getDefault());
        //List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); //creating an object = locationManager (blueprint is LocationManager) Initializing
        listener = new LocationListener() {  //constructor


            @Override
            public void onLocationChanged(Location location) {

                //calling the methods
                Log.i("Status", " " +  location.getAltitude());
                t.append("");
                t.append("\n" + location.getLatitude() +
                        " " + location.getLongitude() +
                        " " + location.getAltitude() +
                        " " + location.getSpeed());
                Log.i("Status", "\n" + location.getLatitude() +
                        " " + location.getLongitude() +
                        " " + location.getAltitude());



//                if (addresses.size() > 0)
//                {
//                    Log.i("Status", addresses.get(0).getLocality());
//                }
//                else
//                {
//                    Log.i("Status" ,"Nigga Pleaze");
//                }






            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.i("Status", "onStatusChanged");


            }

            @Override
            public void onProviderEnabled(String s) {


            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        // end of newlocationlistener
        touch_button();//calls function at the end
        // End of onCreate
    }
    //////////////////////////////////////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("Status", "onPermissionResults");

        switch (requestCode) {
            case 10:
                touch_button();
                break;
            default:
                break;
        }
    }
    /////////////////////////////////////////
    private void touch_button() {

        Log.i("Status", "touchButton()");
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("Status", "first if");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.i("Status", "second if");
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            Log.i("Status", "jumping out of loop[");
            return; //jumps out of the loop

        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        b.setOnClickListener(new View.OnClickListener() { //single instance of OnClickListener "interclass"

            @Override
            public void onClick(View view) {
                Log.i("Status", "setting onclick listener");
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 1000, 0, listener); //just listening every second to your location. via GPS
                Log.i("Status", "GPS Status is being updated");



            }





        });
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.removeUpdates(listener);
                Log.i("Status", "Stops updating listener");
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.setText("");
            }
        });







    }
/////////////////////////////////
}




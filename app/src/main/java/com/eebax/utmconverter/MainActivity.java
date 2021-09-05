package com.eebax.utmconverter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Mohamed Gafar on 04/09/21.
 */

public class MainActivity extends AppCompatActivity {

    EditText latitude, longitude;
    Button convertToUTM;

    EditText easting, northing, zone, latZone;
    Button convertToDecimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);

        easting = findViewById(R.id.easting);
        northing = findViewById(R.id.northing);
        zone = findViewById(R.id.zone);
        latZone = findViewById(R.id.lat_zone);

        convertToUTM = findViewById(R.id.convert_decimal);
        convertToDecimal = findViewById(R.id.convert_utm);
    }

    public void convertDecimal(View view){

        // Validating input fields
        boolean error = false;
        if(latitude.getText().toString().isEmpty())
        {
            error = true;
            latitude.setError("Please set a latitude");
        }
        if(longitude.getText().toString().isEmpty())
        {
            error = true;
            longitude.setError("Please set a longitude");
        }
        if(!error) {

            double lat = Double.parseDouble(latitude.getText().toString());
            double lon = Double.parseDouble(longitude.getText().toString());

            UTM utm = new UTM(new WGS84(lat, lon));

            easting.setText(String.valueOf(utm.getEasting()));
            northing.setText(String.valueOf(utm.getNorthing()));
            zone.setText(String.valueOf(utm.getZone()));
            latZone.setText(String.valueOf(utm.getLetter()));
        }
    }

    public void convertUTM(View view){

        // Validating input fields
        boolean error = false;
        if(easting.getText().toString().isEmpty())
        {
            error = true;
            easting.setError("Please set easting value");
        }
        if(northing.getText().toString().isEmpty())
        {
            error = true;
            northing.setError("Please set northing value");
        }
        if(zone.getText().toString().isEmpty())
        {
            error = true;
            zone.setError("Please set a longitude zone");
        }
        if(latZone.getText().toString().isEmpty())
        {
            error = true;
            latZone.setError("Please set a latitude zone");
        }
        if(!error) {

            double e = Double.parseDouble(easting.getText().toString());
            double n = Double.parseDouble(northing.getText().toString());
            int z = Integer.parseInt(zone.getText().toString());
            char lz = latZone.getText().toString().charAt(0);

            WGS84 wgs84 = new WGS84(new UTM(z, lz, e, n));

            latitude.setText(String.valueOf(wgs84.getLatitude()));
            longitude.setText(String.valueOf(wgs84.getLongitude()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("About");
                alertDialog.setMessage("Developed by Mohamed Gafar \n wadjaafar@gmail.com");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}

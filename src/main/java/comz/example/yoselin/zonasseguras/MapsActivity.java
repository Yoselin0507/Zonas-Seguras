package comz.example.yoselin.zonasseguras;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    //Location location;
    Double latitud = 20.107952, longitud = -98.376794;
    Button Guarda, Save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final EditText fec = (EditText) findViewById(R.id.fecha);
        final EditText hora = (EditText) findViewById(R.id.hora);
        final EditText desc = (EditText) findViewById(R.id.desc);
        Save = (Button) findViewById(R.id.save);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (latitud == 20.107952 && longitud == -98.376794) {
                    AlertDialog.Builder aler = new AlertDialog.Builder(MapsActivity.this);
                    aler.setMessage("Porfavor actualice su ubicación").setNegativeButton("Aceptar", null).create().show();

                } else {
                    String fecha = fec.getText().toString();
                    String hor = hora.getText().toString();
                    String descu = desc.getText().toString();
                    String longit = longitud.toString();
                    String latit = latitud.toString();
                    if (fecha.equals("") || hora.equals("") || descu.equals("")) {
                        AlertDialog.Builder aler = new AlertDialog.Builder(MapsActivity.this);
                        aler.setMessage("Porfavor llene todos los campos").setNegativeButton("Aceptar", null).create().show();

                    } else {
                        Response.Listener<String> answer = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject j = new JSONObject(response);
                                    boolean very;

                                    if (j.getBoolean("success")) very = true;
                                    else very = false;
                                    if (very == true) {
                                        Intent in = new Intent(MapsActivity.this, VerMapa.class);
                                        MapsActivity.this.startActivity(in);
                                    } else {
                                        AlertDialog.Builder aler = new AlertDialog.Builder(MapsActivity.this);
                                        aler.setMessage("Funciona porfavor :(").setNegativeButton("Intentalo de nuevo", null).create().show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        MapsRegistro MP = new MapsRegistro(fecha, hor, descu, latit, longit, answer);
                        RequestQueue cola = Volley.newRequestQueue(MapsActivity.this);
                        cola.add(MP);
                    }

                }
            }
        });
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Guarda = (Button) findViewById(R.id.button3);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (status == ConnectionResult.SUCCESS) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (MapsActivity) getApplicationContext(), 10);
            dialog.show();
        }


        Guarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                locationManager = (LocationManager) MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);
                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        longitud = location.getLongitude();
                        latitud = location.getLatitude();

                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        UiSettings uiSettings = mMap.getUiSettings();
                        uiSettings.setZoomControlsEnabled(true);

                        LatLng sydney = new LatLng(latitud, longitud);
                        mMap.addMarker(new MarkerOptions().position(sydney).title("Estoy Aqui"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        float zoomlevel = 16;
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomlevel));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };
                int Check = ContextCompat.checkSelfPermission(MapsActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


            }
        });

        int Check = ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);



        if(Check == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },1);
            }
        }




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitud, longitud);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}



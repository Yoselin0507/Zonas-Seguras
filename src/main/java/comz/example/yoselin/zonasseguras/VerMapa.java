package comz.example.yoselin.zonasseguras;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

public class VerMapa extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    Button Ver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mapa);
        Ver = (Button)findViewById(R.id.ver);

        Ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        response = response.replace("][",",");
                        if (response.length() > 0)
                        try {
                            JSONArray jsonRespuesta = new JSONArray(response);
                            Log.i("sizejson", ""+jsonRespuesta.length());
                            for (int i = 0; i < jsonRespuesta.length(); i+=5){
                                String fecha = jsonRespuesta.getString(i);
                                String hora = jsonRespuesta.getString(i + 1);
                                String desc = jsonRespuesta.getString(i + 2);
                                String latitud = jsonRespuesta.getString(i + 3);
                                String longitud = jsonRespuesta.getString(i + 4);

                                Double lat = Double.parseDouble(latitud);
                                Double log = Double.parseDouble(longitud);

                                LatLng sydney = new LatLng(lat, log);
                                mMap.addMarker(new MarkerOptions().position(sydney).title("fecha: " +fecha + " hora: " + hora+ " descripcion: " + desc));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                            }
                            /*boolean ok = jsonRespuesta.getBoolean("success");
                            if (ok == true) {
                               String cadena = response;
                               String  [] separa = cadena.split("\\{");
                               for (int i = 1; i < separa.length; i++){
                                   String fecha = jsonRespuesta.getString("fecha");
                                   String hora = jsonRespuesta.getString("hora");
                                   String desc = jsonRespuesta.getString("descripcion");
                                   String latitud = jsonRespuesta.getString("latitud");
                                   String longitud = jsonRespuesta.getString("longitud");

                                   Double lat = Double.parseDouble(latitud);
                                   Double log = Double.parseDouble(longitud);

                                   LatLng sydney = new LatLng(lat, log);
                                   mMap.addMarker(new MarkerOptions().position(sydney).title(separa[i]));
                                   mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                   //Login.this.finish();
                               }

                            } else {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(VerMapa.this);
                                alerta.setMessage("Fallo en el Login")
                                        .setNegativeButton("Reintentar", null)
                                        .create()
                                        .show();
                            }*/
                        } catch (JSONException e) {
                            e.getMessage();
                            AlertDialog.Builder alerta = new AlertDialog.Builder(VerMapa.this);
                            alerta.setMessage("Fallo en el Login " + e+" " + response)
                                    .setNegativeButton("Reintentar", null)
                                    .create()
                                    .show();
                        }
                    }
                };
                VerInfo VI = new VerInfo( respuesta);
                RequestQueue cola = Volley.newRequestQueue(VerMapa.this);
                cola.add(VI);
            }
        });


        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (status == ConnectionResult.SUCCESS) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (MapsActivity) getApplicationContext(), 10);
            dialog.show();
        }


    }
        //Button Ver = (Button)findViewById(R.id.ver);


    @Override
    public void onMapReady(GoogleMap googleMap) {
         mMap = googleMap;



        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(0.0, 0.0);
        //mMap.addMarker(new MarkerOptions().position(sydney).title());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    }


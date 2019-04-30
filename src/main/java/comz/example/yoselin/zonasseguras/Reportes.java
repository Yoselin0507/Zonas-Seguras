package comz.example.yoselin.zonasseguras;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Reportes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        final EditText nombreT     = (EditText)findViewById(R.id.editText9);
        final EditText usuarioT    = (EditText)findViewById(R.id.editText8);
        final EditText claveT      = (EditText)findViewById(R.id.editText10);
        Button btn_Registro = (Button)findViewById(R.id.button);
        //btn_Regresar = (Button)findViewById(R.id.btn_Regresar);
        //btn_Regresar.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View v) {
        //onBackPressed();
        // Intent i = new Intent(Registro.this, Login.class);
        //startActivity(i);
        //}
        //});
        btn_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre   = nombreT.getText().toString();
                String usuario  = usuarioT.getText().toString();
                String clave    = claveT.getText().toString();

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonRespuesta = new JSONObject(response);
                            AlertDialog.Builder lerta = new AlertDialog.Builder(Reportes.this);
                            lerta.setMessage("Fallo en el Registro")
                                    .setNegativeButton("Reintentar", null)
                                    .create()
                                    .show();
                            boolean ok = jsonRespuesta.getBoolean("success");
                            if(ok == true){
                                Intent i = new Intent(Reportes.this, Login.class);
                                Reportes.this.startActivity(i);
                                //Registro.this.finish();
                            }else {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Reportes.this);
                                alerta.setMessage("Fallo en el Registro")
                                        .setNegativeButton("Reintentar", null)
                                        .create()
                                        .show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };
                MapsRegistro r = new MapsRegistro(nombre,usuario,clave,"", "", respuesta);
                RequestQueue cola = Volley.newRequestQueue(Reportes.this);
                cola.add(r);
            }
        });

    }

}

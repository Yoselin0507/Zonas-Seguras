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

public class Registro extends AppCompatActivity {
    Button btn_Regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        final EditText nombreT     = (EditText)findViewById(R.id.nombre_Registro);
        final EditText usuarioT    = (EditText)findViewById(R.id.usuario_Registro);
        final EditText claveT      = (EditText)findViewById(R.id.clave_Registro);
        final EditText claveT2      = (EditText)findViewById(R.id.clave_Registro2);
        final EditText edadT       = (EditText)findViewById(R.id.edad_Registro);
        Button btn_Registro = (Button)findViewById(R.id.btn_Registro);
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
                String nombre = nombreT.getText().toString();
                String usuario = usuarioT.getText().toString();
                String clave = claveT.getText().toString();
                String clave2 = claveT2.getText().toString();
                int edad = 0;
                try{
                    edad = Integer.parseInt(edadT.getText().toString());
                }catch(Exception ex){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                    alerta.setMessage("Poe favor rellene todos los campos")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                }

                if (nombre.equals("") || usuario.equals("") || clave.equals("")) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                    alerta.setMessage("Por favor rellene todos los campos")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                } else {
                    if (clave.equals(clave2)) {
                        Response.Listener<String> respuesta = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonRespuesta = new JSONObject(response);
                                    boolean ok = jsonRespuesta.getBoolean("success");
                                    if (ok == true) {
                                        Intent i = new Intent(Registro.this, Login.class);
                                        Registro.this.startActivity(i);
                                        //Registro.this.finish();
                                    } else {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                                        alerta.setMessage("Fallo en el Registro")
                                                .setNegativeButton("Reintentar", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.getMessage();
                                }
                            }
                        };
                        RegistroRequest r = new RegistroRequest(nombre, usuario, clave, edad, respuesta);
                        RequestQueue cola = Volley.newRequestQueue(Registro.this);
                        cola.add(r);
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                        alerta.setMessage("Las contraseñas no coinciden")
                                .setNegativeButton("Reintentar", null)
                                .create()
                                .show();
                    }
                }
            }
        });
    }
}

package comz.example.yoselin.zonasseguras;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registro = (TextView)findViewById(R.id.registro_Login);
        Button btn_Login = (Button)findViewById(R.id.btn_Login);
        final EditText usuarioT = (EditText)findViewById(R.id.usuario_Login);
        final EditText claveT = (EditText)findViewById(R.id.clave_Login);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(Login.this, Registro.class);
                Login.this.startActivity(registro);
                //finish();
            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usuario = usuarioT.getText().toString();
                final String clave = claveT.getText().toString();
                if (usuario.equals("") || clave.equals("")) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                    alerta.setMessage("Porfavor llene todos los campos")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                } else {
                    final Response.Listener<String> respuesta = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonRespuesta = null;
                                try {
                                    jsonRespuesta = new JSONObject(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                boolean ok = jsonRespuesta.getBoolean("success");
                                if (ok == true) {
                                    String nombre = jsonRespuesta.getString("nombre");
                                    int edad = jsonRespuesta.getInt("edad");
                                    Intent bienvenido = new Intent(Login.this, Bienvenido.class);
                                    bienvenido.putExtra("nombre", nombre);
                                    bienvenido.putExtra("edad", edad);

                                    Login.this.startActivity(bienvenido);
                                    //Login.this.finish();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                    alerta.setMessage("Fallo en el Login")
                                            .setNegativeButton("Reintentar", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    };
                    LoginRequest r = new LoginRequest(usuario, clave, respuesta);
                    RequestQueue cola = Volley.newRequestQueue(Login.this);
                    cola.add(r);
                }
            }
        });
    }
}

package comz.example.yoselin.zonasseguras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Bienvenido extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
        final TextView mensaje = (TextView)findViewById(R.id.mensaje);
        Intent i = this.getIntent();
        String nombre = i.getStringExtra("nombre");
        int edad = i.getIntExtra("edad", -1);
        mensaje.setText(mensaje.getText()+" "+nombre+"\r\nSu edad: "+edad+"");
    }
    public void Reportes (View view){
        Intent intent = new Intent(Bienvenido.this, MapsActivity.class);
        startActivity(intent);
    }
    public void Ver(View V){
        Intent intent = new Intent(Bienvenido.this, VerMapa.class);
        startActivity(intent);
    }
}

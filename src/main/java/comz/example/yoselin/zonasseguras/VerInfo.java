package comz.example.yoselin.zonasseguras;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class VerInfo extends StringRequest {

    private static final String ruta = "https://zonas.000webhostapp.com/consultarReserva.php";

    public VerInfo(Response.Listener<String> listener) {
        super(ruta, listener, null);
    }
    //protected Map<String, String> getParams(){return parametros;}

}
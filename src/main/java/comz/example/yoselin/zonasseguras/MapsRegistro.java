package comz.example.yoselin.zonasseguras;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MapsRegistro extends StringRequest {
    private static final String ruta = "https://zonas.000webhostapp.com/Reportes.php";
    private Map<String, String> parametros;

    public MapsRegistro(String fecha, String hora, String descripcion, String lat, String lon, Response.Listener<String> listener) {
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("fecha", fecha + "");
        parametros.put("hora", hora + "");
        parametros.put("descripcion", descripcion + "");
        parametros.put("latitud", lat + "");
        parametros.put("longitud", lon + "");
    }
    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}

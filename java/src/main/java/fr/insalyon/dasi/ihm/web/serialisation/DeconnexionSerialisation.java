package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeconnexionSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        if((Boolean) request.getAttribute("success")){
            container.addProperty("success", "true");
        }
        else{
            container.addProperty("success", "false");
        }
        
        sendJson(response, container);
    }
    
}

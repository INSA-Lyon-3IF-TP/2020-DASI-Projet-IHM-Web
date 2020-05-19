package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Consultation;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemarrerConsultationSerialisation extends Serialisation{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        Consultation consultation = (Consultation) request.getAttribute("consultation");
        
        if(consultation != null && consultation.getHeureDebut() != null){
            container.addProperty("success", Boolean.TRUE);
        }else{
            container.addProperty("success", Boolean.FALSE);
        }
        
        sendJson(response, container);
    }
    
}

package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InfosConsultationSerialisation extends Serialisation{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Employe employe = (Employe)request.getAttribute("employe");
        
        JsonObject container = new JsonObject();

        Boolean connexion = (employe != null);
        container.addProperty("connexion", connexion);
        
        Consultation consultation = (Consultation) request.getAttribute("consultation");
        
        if(consultation != null){
            JsonObject consultationJson = new JsonObject();
            
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            consultationJson.addProperty("heureDemande",simpleDateFormat.format(consultation.getHeureDemande()));
            consultationJson.addProperty("idClient",consultation.getClient().getId().toString());
            consultationJson.addProperty("idMedium",consultation.getMedium().getId().toString());
            
            container.add("consultation", consultationJson);
        }
         
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
    
}

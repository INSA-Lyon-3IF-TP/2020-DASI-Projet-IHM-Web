/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Astrologue;
import fr.insalyon.dasi.metier.modele.Cartomancien;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Spirite;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fabien
 */
public class GetConsultationEnCoursSerialisation extends Serialisation{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Employe employe = (Employe)request.getAttribute("employe");
        
        JsonObject container = new JsonObject();

        Boolean connexion = (employe != null);
        container.addProperty("connexion", connexion);
        
        Consultation consultation = (Consultation) request.getAttribute("consultation");
        
        JsonObject consultationJson = new JsonObject();
        if(consultation != null && employe!=null){  
            consultationJson.addProperty("commentaire",consultation.getCommentaire());
            container.addProperty("mediumDenomination",consultation.getMedium().getDenomination());
            if (consultation.getMedium() instanceof Astrologue) {
                container.addProperty("mediumType", "Astrologue");
            } else if (consultation.getMedium() instanceof Cartomancien) {
                container.addProperty("mediumType", "Cartomancien");
            } else if (consultation.getMedium() instanceof Spirite) {
                container.addProperty("mediumType", "Spirite");
            }
            container.addProperty("mediumPresentation",consultation.getMedium().getPresentation());
        } else
        {
            consultationJson.addProperty("commentaire","Pas de consultation en cours");
        }
        container.add("consultation", consultationJson);

        sendJson(response,container);
    }
    
}

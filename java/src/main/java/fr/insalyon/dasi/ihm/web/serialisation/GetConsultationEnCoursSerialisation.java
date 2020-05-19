/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
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
        } else
        {
            consultationJson.addProperty("commentaire","Pas de consultation en cours");
        }
        container.add("consultation", consultationJson);
        
        sendJson(response,container);
    }
    
}

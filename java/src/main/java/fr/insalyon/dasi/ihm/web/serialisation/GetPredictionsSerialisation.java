/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fabien
 */
public class GetPredictionsSerialisation extends Serialisation{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Employe employe = (Employe)request.getAttribute("employe");
        
        JsonObject container = new JsonObject();

        Boolean connexion = (employe != null);
        container.addProperty("connexion", connexion);
        
        String predictionAmour = (String) request.getAttribute("predictionAmour");
        String predictionSante = (String) request.getAttribute("predictionSante");
        String predictionTravail = (String) request.getAttribute("predictionTravail");
        
        if(employe != null){  
            container.addProperty("predictionAmour",predictionAmour);
            container.addProperty("predictionSante",predictionSante);
            container.addProperty("predictionTravail",predictionTravail);
        }
        
        sendJson(response,container);
    }
    
}

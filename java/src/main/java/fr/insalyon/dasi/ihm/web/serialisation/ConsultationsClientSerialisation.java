/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Astrologue;
import fr.insalyon.dasi.metier.modele.Cartomancien;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Spirite;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fabien
 */
public class ConsultationsClientSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {        
        
        Client client = (Client)request.getAttribute("client");
        JsonObject container = new JsonObject();
        
        Boolean connexion = (client != null); //Si jamais on a un probleme avec la connexion du client on garde l'attribut
        container.addProperty("connexion", connexion);

        if (client != null) {
            JsonArray jsonConsultations = new JsonArray();
            List<Consultation> consultations = client.getConsultations();
            
            for (Consultation consultation : consultations) {
                JsonObject jsonConsultation = new JsonObject();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy"); 
                String strDate;
                if(consultation.getHeureDemande() != null)
                {
                    strDate = dateFormat.format(consultation.getHeureDemande()); 
                    jsonConsultation.addProperty("heureDemande", strDate);
                } else {
                    jsonConsultation.addProperty("heureDemande", "---");
                }
                if(consultation.getHeureDebut() != null)
                {
                    strDate = dateFormat.format(consultation.getHeureDebut());
                    jsonConsultation.addProperty("heureDebut", strDate);
                } else {
                    jsonConsultation.addProperty("heureDebut", "---");
                }
                if(consultation.getHeureFin() != null)
                {
                    strDate = dateFormat.format(consultation.getHeureFin());
                    jsonConsultation.addProperty("heureFin", strDate);
                } else {
                    jsonConsultation.addProperty("heureFin", "---");
                }
                jsonConsultation.addProperty("mediumDenomination", consultation.getMedium().getDenomination());
                if(consultation.getMedium() instanceof Astrologue){
                    jsonConsultation.addProperty("mediumType", "Astrologue");
                }
                else if(consultation.getMedium() instanceof Cartomancien){
                    jsonConsultation.addProperty("mediumType", "Cartomancien");
                }
                else if(consultation.getMedium() instanceof Spirite){
                    jsonConsultation.addProperty("mediumType", "Spirite");
                }
                jsonConsultations.add(jsonConsultation);
            }
            
            container.add("consultations", jsonConsultations);
        }

        sendJson(response,container);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Astrologue;
import fr.insalyon.dasi.metier.modele.Cartomancien;
import fr.insalyon.dasi.metier.modele.Genre;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.modele.Spirite;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nicolas
 */
public class ListerMediumsSerialisation extends Serialisation{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Medium> mediums = (List<Medium>)request.getAttribute("mediums");
        
        JsonObject container = new JsonObject();

        if (!mediums.isEmpty()) {
            JsonArray astrologues = new JsonArray();
            JsonArray cartomanciens = new JsonArray();
            JsonArray spirites = new JsonArray();
            
            for(Medium medium : mediums){
                JsonObject mediumJSON = new JsonObject();
                mediumJSON.addProperty("denomination",medium.getDenomination());
                String genre = (medium.getGenre().equals(Genre.Masculin) ? "M" : "F");
                mediumJSON.addProperty("genre", genre);
                mediumJSON.addProperty("presentation",medium.getPresentation());
                
                if(medium instanceof Astrologue){
                    Astrologue astrologue = (Astrologue) medium;
                    mediumJSON.addProperty("formation", astrologue.getFormation());
                    mediumJSON.addProperty("promo", astrologue.getPromo());
                    astrologues.add(mediumJSON);
                }
                else if(medium instanceof Cartomancien){
                    cartomanciens.add(mediumJSON);
                }
                else if(medium instanceof Spirite){
                    Spirite spirite = (Spirite) medium;
                    mediumJSON.addProperty("support", spirite.getSupport());
                    spirites.add(mediumJSON);
                }
                
            }

            container.add("astrologues", astrologues);
            container.add("cartomanciens", cartomanciens);
            container.add("spirites", spirites);
        }

        sendJson(response,container);
    }
    
}

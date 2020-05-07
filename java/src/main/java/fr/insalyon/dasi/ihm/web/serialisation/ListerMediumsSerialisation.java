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
import fr.insalyon.dasi.metier.modele.Genre;
import fr.insalyon.dasi.metier.modele.Medium;
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
            JsonArray jsonMediums = new JsonArray();
            
            for(Medium medium : mediums){
                JsonObject mediumJSON = new JsonObject();
                mediumJSON.addProperty("denomination",medium.getDenomination());
                String genre = (medium.getGenre().equals(Genre.Masculin) ? "Masculin" : "Féminin");
                mediumJSON.addProperty("genre", genre);
                mediumJSON.addProperty("presentation",medium.getPresentation());
                
                jsonMediums.add(mediumJSON);
            }

            container.add("mediums", jsonMediums);
        }

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
    
}

package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetStatisticsSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Employe employe = (Employe) request.getAttribute("employe");

        JsonObject container = new JsonObject();

        Boolean connexion = (employe != null);
        container.addProperty("connexion", connexion);
        if (employe != null) {
            container.addProperty("occupe", employe.getEstOccupe() ? Boolean.TRUE : Boolean.FALSE);
        
            List<Medium> orderedMediums = (List<Medium>) request.getAttribute("orderedmediums");
            JsonObject dataTop5 = topFiveMediums(orderedMediums);
            container.add("dataTop5", dataTop5);
        
            JsonObject dataAllMediums = allMediums(orderedMediums);
            container.add("dataAllMediums", dataAllMediums);
            
            Map<Employe, Long> nombreClientsParEmploye = (Map<Employe, Long>) request.getAttribute("nombreClientsParEmploye");
            if(nombreClientsParEmploye != null){
                JsonObject dataRepartitionClients = repartitionClients(nombreClientsParEmploye);
                container.add("dataRepartitionClients", dataRepartitionClients);
            }
            
        }
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

    public JsonObject topFiveMediums(List<Medium> orderedMediums){
        JsonObject dataTop5 = new JsonObject();
        JsonArray datasets = new JsonArray();
        JsonObject dataObj = new JsonObject();
        JsonArray data = new JsonArray();
        JsonArray labels = new JsonArray();

        int i = 0;
        for(; i < 5 && i < orderedMediums.size(); ++i){
            Medium medium = orderedMediums.get(i);
            if(medium.getConsultations() == null){
                data.add(0);
            }
            else{
                data.add(medium.getConsultations().size());
            }
            labels.add(medium.getDenomination());
        }

        dataObj.add("data", data);
        datasets.add(dataObj);
        dataTop5.add("datasets", datasets);
        dataTop5.add("labels", labels);


        /*
        dataTop5 = {
            datasets: [{
                data: [10, 20, 30]
            }],

            // These labels appear in the legend and in the tooltips when hovering different arcs
            labels: [
                'Red',
                'Yellow',
                'Blue'
            ]
        };
         */
        return dataTop5;
    }
    
    public JsonObject repartitionClients(Map<Employe, Long> nombreClientsParEmploye){
        JsonObject dataRepartitionClients = new JsonObject();
        JsonArray datasets = new JsonArray();
        JsonObject dataObj = new JsonObject();
        JsonArray data = new JsonArray();
        JsonArray labels = new JsonArray();

        for (Map.Entry mapentry : nombreClientsParEmploye.entrySet()) {
            System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
        
            Employe employe = (Employe) mapentry.getKey();
            
            data.add(mapentry.getValue().toString());
            labels.add(employe.getPrenom() + " " + employe.getNom());
        }

        dataObj.add("data", data);
        datasets.add(dataObj);
        dataRepartitionClients.add("datasets", datasets);
        dataRepartitionClients.add("labels", labels);
        
        System.out.println(dataRepartitionClients);
        
        return dataRepartitionClients;
    }
    
    public JsonObject allMediums(List<Medium> orderedMediums){
        JsonObject dataAllMediums = new JsonObject();
        JsonArray datasets = new JsonArray();
        JsonObject dataObj = new JsonObject();
        JsonArray data = new JsonArray();
        JsonArray labels = new JsonArray();

        for(Medium medium : orderedMediums){
            if(medium.getConsultations() == null){
                data.add(0);
            }
            else{
                data.add(medium.getConsultations().size());
            }
            labels.add(medium.getDenomination());
        }

        dataObj.add("data", data);
        datasets.add(dataObj);
        dataAllMediums.add("datasets", datasets);
        dataAllMediums.add("labels", labels);
        
        System.out.println(dataAllMediums);
        
        return dataAllMediums;        
    }
}

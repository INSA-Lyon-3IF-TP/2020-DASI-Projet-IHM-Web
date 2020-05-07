package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Employe;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nicolas
 */
public class ProfilEmployeSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Employe employe = (Employe)request.getAttribute("employe");

        JsonObject container = new JsonObject();

        Boolean connexion = (employe != null);
        container.addProperty("connexion", connexion);

//        if (employe != null) {
//            JsonObject jsonClient = new JsonObject();
//            jsonClient.addProperty("id", employe.getId());
//            jsonClient.addProperty("nom", employe.getNom());
//            jsonClient.addProperty("prenom", employe.getPrenom());
//            jsonClient.addProperty("mail", employe.getMail());
//
//            container.add("employe", jsonClient);
//        }

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}

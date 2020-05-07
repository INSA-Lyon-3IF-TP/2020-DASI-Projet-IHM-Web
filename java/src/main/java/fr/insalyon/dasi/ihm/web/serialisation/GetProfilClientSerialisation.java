/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Client;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fabien
 */
public class GetProfilClientSerialisation extends Serialisation{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = (Client)request.getAttribute("client");
        
        JsonObject container = new JsonObject();

        Boolean connexion = (client != null); //Si jamais on a un probleme avec la connexion du client on garde l'attribut
        container.addProperty("connexion", connexion);

        if (client != null) {
            JsonObject jsonClient = new JsonObject();
            JsonObject jsonProfilAstral = new JsonObject();
            jsonClient.addProperty("id", client.getId());
            jsonClient.addProperty("nom", client.getNom());
            jsonClient.addProperty("prenom", client.getPrenom());
            jsonClient.addProperty("civilite", client.getCivilite());
            jsonClient.addProperty("dateDeNaissance", client.getDateDeNaissance().toString());
            jsonClient.addProperty("mail", client.getMail());
            jsonClient.addProperty("telephone", client.getTelephone());
            jsonClient.addProperty("adresse", client.getAdresse());
            
            jsonProfilAstral.addProperty("signeZodiaque", client.getProfilAstral().getSigneZodiaque());
            jsonProfilAstral.addProperty("signeChinois", client.getProfilAstral().getSigneChinois());
            jsonProfilAstral.addProperty("couleur", client.getProfilAstral().getCouleur());
            jsonProfilAstral.addProperty("animal", client.getProfilAstral().getAnimal());

            container.add("client", jsonClient);
            container.add("profilAstral", jsonProfilAstral);
        }

        sendJson(response,container);
    }
    
}

package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Astrologue;
import fr.insalyon.dasi.metier.modele.Cartomancien;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Spirite;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InfosConsultationSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Employe employe = (Employe) request.getAttribute("employe");
        Client client = (Client) request.getAttribute("client");
        Consultation consultation = (Consultation) request.getAttribute("consultation");
        List<Consultation> historiqueClient = (List<Consultation>) request.getAttribute("historiqueClient");

        
//        System.out.println(employe);
//        System.out.println(client);
//        System.out.println(consultation);
//        System.out.println(historiqueClient);
        
        JsonObject container = new JsonObject();

        Boolean connexion = (employe != null);
        container.addProperty("connexion", connexion);


        if (consultation != null) {
            if (client != null) {
                JsonObject jsonClient = new JsonObject();
                JsonObject jsonProfilAstral = new JsonObject();
                jsonClient.addProperty("id", client.getId());
                jsonClient.addProperty("nom", client.getNom());
                jsonClient.addProperty("prenom", client.getPrenom());
                jsonClient.addProperty("civilite", client.getCivilite());
                DateFormat dateNaissanceFormat = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = dateNaissanceFormat.format(client.getDateDeNaissance());
                jsonClient.addProperty("dateDeNaissance", strDate);
                jsonClient.addProperty("mail", client.getMail());
                jsonClient.addProperty("telephone", client.getTelephone());
                jsonClient.addProperty("adresse", client.getAdresse());

                jsonProfilAstral.addProperty("signeZodiaque", client.getProfilAstral().getSigneZodiaque());
                jsonProfilAstral.addProperty("signeChinois", client.getProfilAstral().getSigneChinois());
                jsonProfilAstral.addProperty("couleur", client.getProfilAstral().getCouleur());
                jsonProfilAstral.addProperty("animal", client.getProfilAstral().getAnimal());

                container.add("client", jsonClient);
                container.add("profilAstral", jsonProfilAstral);

                if (historiqueClient != null) {
                    JsonArray historiqueClientJson = new JsonArray();

                    DateFormat dateHeureFormat = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
                    for (Consultation cons : historiqueClient) {
                        JsonObject jsonConsultation = new JsonObject();
                        if (cons.getHeureDemande() != null) {
                            strDate = dateHeureFormat.format(cons.getHeureDemande());
                            jsonConsultation.addProperty("heureDemande", strDate);
                        } else {
                            jsonConsultation.addProperty("heureDemande", "---");
                        }
                        if (cons.getHeureDebut() != null) {
                            strDate = dateHeureFormat.format(cons.getHeureDebut());
                            jsonConsultation.addProperty("heureDebut", strDate);
                        } else {
                            jsonConsultation.addProperty("heureDebut", "---");
                        }
                        if (cons.getHeureFin() != null) {
                            strDate = dateHeureFormat.format(cons.getHeureFin());
                            jsonConsultation.addProperty("heureFin", strDate);
                        } else {
                            jsonConsultation.addProperty("heureFin", "---");
                        }
                        if (cons.getCommentaire() != null) {
                            jsonConsultation.addProperty("commentaire", cons.getCommentaire());
                        } else {
                            jsonConsultation.addProperty("commmentaire", "---");
                        }
                        if (cons.getEmploye() != null) {
                            jsonConsultation.addProperty("employe", cons.getEmploye().getPrenom() + " " + cons.getEmploye().getNom());
                        } else {
                            jsonConsultation.addProperty("employe", "---");
                        }
                        jsonConsultation.addProperty("mediumDenomination", cons.getMedium().getDenomination());
                        if (cons.getMedium() instanceof Astrologue) {
                            jsonConsultation.addProperty("mediumType", "Astrologue");
                        } else if (cons.getMedium() instanceof Cartomancien) {
                            jsonConsultation.addProperty("mediumType", "Cartomancien");
                        } else if (cons.getMedium() instanceof Spirite) {
                            jsonConsultation.addProperty("mediumType", "Spirite");
                        }
                        historiqueClientJson.add(jsonConsultation);
                    }
                    container.add("historiqueClient", historiqueClientJson);
                }
                
                container.add("client", jsonClient);
                container.add("profilAstral", jsonProfilAstral);
                container.addProperty("mediumDenomination",consultation.getMedium().getDenomination());
                if (consultation.getMedium() instanceof Astrologue) {
                    container.addProperty("mediumType", "Astrologue");
                } else if (consultation.getMedium() instanceof Cartomancien) {
                    container.addProperty("mediumType", "Cartomancien");
                } else if (consultation.getMedium() instanceof Spirite) {
                    container.addProperty("mediumType", "Spirite");
                }
                container.addProperty("mediumPresentation",consultation.getMedium().getPresentation());
            }
        }

        sendJson(response, container);
    }

}

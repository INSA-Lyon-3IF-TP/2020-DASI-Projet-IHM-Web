/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.service.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fabien
 */
public class InscrireClientAction extends Action{

    @Override
    public void executer(HttpServletRequest request) {
        Long idClient = null;
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String mail = request.getParameter("mail");
        String dateString = request.getParameter("date");
        String civilite = request.getParameter("civilite");
        String tel = request.getParameter("tel");
        String adresse = request.getParameter("adresse");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");
        
        if(nom.isEmpty() 
            || prenom.isEmpty()
            || mail.isEmpty()
            || dateString.isEmpty()
            || civilite.isEmpty()
            || tel.isEmpty()
            || adresse.isEmpty()
            || password.isEmpty()
            || !passwordConfirmation.equals(password)) // Vérification côté serveur
        {
            request.setAttribute("idClient", idClient);
            return; 
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        }
        catch (ParseException ex) {
            // Erreur
        }

        Service service = new Service();
        Client client = new Client(nom, prenom, civilite, date, tel, adresse, mail, password);
        idClient = service.inscrireClient(client);

        request.setAttribute("idClient", idClient);
    }
    
}

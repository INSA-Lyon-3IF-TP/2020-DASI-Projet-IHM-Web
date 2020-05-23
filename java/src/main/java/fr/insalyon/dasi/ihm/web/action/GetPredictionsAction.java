/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fabien
 */
public class GetPredictionsAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long idEmploye = (Long)session.getAttribute("idEmploye");
        String noteAmourS = request.getParameter("noteAmour");
        String noteSanteS = request.getParameter("noteSante");
        String noteTravailS = request.getParameter("noteTravail");

        int noteAmour = Integer.parseInt(noteAmourS);
        int noteSante = Integer.parseInt(noteSanteS);
        int noteTravail = Integer.parseInt(noteTravailS);
        if(noteAmour <1 || noteAmour > 4
           || noteSante <1 || noteSante > 4
           || noteTravail <1 || noteTravail > 4)
        {
            System.out.println("Erreur predictions");
            noteAmour = 1;
            noteSante = 1;
            noteTravail = 1;
        }
        Service service = new Service();
        Consultation consultation = null;
        Client client = null;
        Employe employe = null;
        if(idEmploye != null){
            employe = service.rechercherEmployeParId(idEmploye);
            
            if(employe.getEstOccupe()){
                List<Consultation> consultations = employe.getConsultations();
                consultation = consultations.get(consultations.size()-1);
                client = consultation.getClient();
            } else {
                client = service.rechercherClientParId(new Long(1)); //pour les tests
            }
        }
        
        List<String> predictions = service.getPredictions(noteAmour, noteSante, noteTravail, client);
        request.setAttribute("predictionAmour", predictions.get(0));
        request.setAttribute("predictionSante", predictions.get(1));
        request.setAttribute("predictionTravail", predictions.get(2));
        request.setAttribute("employe", employe);
    }
    
}

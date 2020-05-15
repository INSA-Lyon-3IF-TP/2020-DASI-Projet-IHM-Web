package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;

public class PrendreRendezVousAction extends Action{

    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();
        Medium medium = service.rechercherMediumParId(Long.valueOf(request.getParameter("idMedium")));
        Client client = service.rechercherClientParId((Long) request.getSession().getAttribute("idClient"));
        
        Consultation consultation = service.prendreRendezVous(client, medium);
        
        if(consultation != null){
            request.setAttribute("success", Boolean.TRUE);
        }
        else{
            request.setAttribute("success", Boolean.FALSE);
        }
    }
    
}

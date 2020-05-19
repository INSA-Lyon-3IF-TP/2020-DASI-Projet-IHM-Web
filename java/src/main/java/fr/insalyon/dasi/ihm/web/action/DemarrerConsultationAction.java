package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DemarrerConsultationAction extends Action{

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long idEmploye = (Long) session.getAttribute("idEmploye");
        Employe employe = null;
        Consultation consultation = null;
        Service service = new Service();
        
        if(idEmploye != null){
            employe = service.rechercherEmployeParId(idEmploye);
            
            List<Consultation> consultations = employe.getConsultations();
            if(consultations.size() > 0){
                Collections.sort(consultations, (Consultation consultation1, Consultation consultation2) -> consultation1.getHeureDebut().compareTo(consultation2.getHeureDebut()));

                consultation = consultations.get(0);
                service.debuterConsultation(consultation);
            }            
        }    
        request.setAttribute("consultation", consultation);    
    }    
}

package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetConsultationEnAttenteAction extends Action{

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long idEmploye = (Long) session.getAttribute("idEmploye");
        Consultation consultation = null;
        Employe employe = null;
        if(idEmploye != null){
            Service service = new Service();
            employe = service.rechercherEmployeParId(idEmploye);
            
            if(employe.getEstOccupe()){
                List<Consultation> consultations = employe.getConsultations();
                consultation = consultations.get(consultations.size()-1);
                System.out.println(consultation);
            }
        }
        request.setAttribute("consultation", consultation);
        request.setAttribute("employe", employe);
    }
    
}

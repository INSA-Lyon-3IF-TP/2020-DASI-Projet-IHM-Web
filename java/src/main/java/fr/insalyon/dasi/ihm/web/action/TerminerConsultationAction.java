/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

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
public class TerminerConsultationAction extends Action{

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long idEmploye = (Long) session.getAttribute("idEmploye");
        String commentaire = request.getParameter("commentaire");
        if(commentaire==null)
        {
            commentaire = "";
        }

        Employe employe = null;
        Consultation consultation = null;
        if(idEmploye != null){
            Service service = new Service();
            employe = service.rechercherEmployeParId(idEmploye);
            
            if(employe.getEstOccupe()){
                List<Consultation> consultations = employe.getConsultations();
                consultation = consultations.get(consultations.size()-1);
            }
            
            consultation.setCommentaire(commentaire);
            service.terminerConsultation(consultation);
        }
        request.setAttribute("employe", employe);
    }
    
}

package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetStatisticsAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long idEmploye = (Long) session.getAttribute("idEmploye");
        Employe employe = null;
        if(idEmploye != null){
            Service service = new Service();
            employe = service.rechercherEmployeParId(idEmploye);
        }
        request.setAttribute("employe", employe);
    }
    
}

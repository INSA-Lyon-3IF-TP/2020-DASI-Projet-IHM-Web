package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.service.Service;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
            
            List<Medium> mediums = service.listerMediums();
            if(mediums != null){
                mediums.sort((Medium medium1, Medium medium2) -> {
                   return medium2.getConsultations().size() - medium1.getConsultations().size(); 
                });

                request.setAttribute("orderedmediums", mediums);
            }
            
            Map<Employe, Long> nombreClientsParEmploye = service.repartitionClientsParEmploye();
            request.setAttribute("nombreClientsParEmploye", nombreClientsParEmploye);

        }
        request.setAttribute("employe", employe);
    }
    
}

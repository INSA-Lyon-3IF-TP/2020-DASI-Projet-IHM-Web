package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class ListMediumsAction extends Action{

    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();
        
        List<Medium> mediums = (List<Medium>) service.listerMediums();
        
        request.setAttribute("mediums", mediums);
        
        HttpSession session = request.getSession();
        Long idClient = (Long)session.getAttribute("idClient");
        
        if(idClient!=null)
        {
            request.setAttribute("connected", Boolean.TRUE);
        }
        else{
            request.setAttribute("connected", Boolean.FALSE);
        }
    }
    
}

package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nicolas
 */
public class AuthentifierEmployeAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Service service = new Service();
        Employe employe = service.authentifierEmploye(login, password);
        
        request.setAttribute("employe", employe);
        
        // Gestion de la Session: ici, enregistrer l'ID du Employe authentifié
        HttpSession session = request.getSession();
        if (employe != null) {
            session.setAttribute("idEmploye", employe.getId());
        }
        else {
            session.removeAttribute("idEmploye");
        }
    }
    
}

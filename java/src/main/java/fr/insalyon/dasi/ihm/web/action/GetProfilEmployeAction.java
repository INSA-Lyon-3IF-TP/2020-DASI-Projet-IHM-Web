/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fabien
 */
public class GetProfilEmployeAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long idEmploye = (Long)session.getAttribute("idEmploye");
        
        if(idEmploye!=null)
        {
            Service service = new Service();
            Employe employe = service.rechercherEmployeParId(idEmploye);
            request.setAttribute("employe", employe);
        } else {
            request.setAttribute("employe", null);
        }
    }
    
}

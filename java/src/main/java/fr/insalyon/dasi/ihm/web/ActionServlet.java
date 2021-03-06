package fr.insalyon.dasi.ihm.web;

import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.ihm.web.action.Action;
import fr.insalyon.dasi.ihm.web.action.AuthentifierClientAction;
import fr.insalyon.dasi.ihm.web.action.AuthentifierEmployeAction;
import fr.insalyon.dasi.ihm.web.action.ConsultationsClientAction;
import fr.insalyon.dasi.ihm.web.action.DeconnexionAction;
import fr.insalyon.dasi.ihm.web.action.DemarrerConsultationAction;
import fr.insalyon.dasi.ihm.web.action.GetConsultationEnAttenteAction;
import fr.insalyon.dasi.ihm.web.action.GetConsultationEnCoursAction;
import fr.insalyon.dasi.ihm.web.action.GetPredictionsAction;
import fr.insalyon.dasi.ihm.web.action.GetStatisticsAction;
import fr.insalyon.dasi.ihm.web.serialisation.GetStatisticsSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.InfosConsultationSerialisation;
import fr.insalyon.dasi.ihm.web.action.GetProfilClientAction;
import fr.insalyon.dasi.ihm.web.action.GetProfilEmployeAction;
import fr.insalyon.dasi.ihm.web.action.InscrireClientAction;
import fr.insalyon.dasi.ihm.web.action.ListMediumsAction;
import fr.insalyon.dasi.ihm.web.action.PrendreRendezVousAction;
import fr.insalyon.dasi.ihm.web.action.TerminerConsultationAction;
import fr.insalyon.dasi.ihm.web.serialisation.ConsultationsClientSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.DeconnexionSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.DemarrerConsultationSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.GetConsultationEnCoursSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.GetPredictionsSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.GetProfilClientSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.GetProfilEmployeSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.InscrireClientSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.ListerMediumsSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.PrendreRendezVousSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.ProfilClientSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.ProfilEmployeSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.Serialisation;
import fr.insalyon.dasi.ihm.web.serialisation.TerminerConsultationSerialisation;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DASI Team
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");

        String todo = request.getParameter("todo");

        Action action = null;
        Serialisation serialisation = null;

        if (todo != null) {
            switch (todo) {
                case "connecter":
                    action = new AuthentifierClientAction();
                    serialisation = new ProfilClientSerialisation();
                    break;
                case "connecterEmploye":
                    action = new AuthentifierEmployeAction();
                    serialisation = new ProfilEmployeSerialisation();
                    break;
                case "listerMediums":
                    action = new ListMediumsAction();
                    serialisation = new ListerMediumsSerialisation();
                    break;
                case "getConsultationEnAttente":
                    action = new GetConsultationEnAttenteAction();
                    serialisation = new InfosConsultationSerialisation();
                    break;
                case "getStatistics":
                    action = new GetStatisticsAction();
                    serialisation = new GetStatisticsSerialisation();
                    break;
                case "inscription":
                    action = new InscrireClientAction();
                    serialisation = new InscrireClientSerialisation();
                    break;
                case "profilClient":
                    action = new GetProfilClientAction();
                    serialisation = new GetProfilClientSerialisation();
                    break;
                case "historiqueConsultationClient":
                    action = new ConsultationsClientAction();
                    serialisation = new ConsultationsClientSerialisation();
                    break;
                case "prendreRDV":
                    action = new PrendreRendezVousAction();
                    serialisation = new PrendreRendezVousSerialisation();
                    break;
                case "profilEmploye":
                    action = new GetProfilEmployeAction();
                    serialisation = new GetProfilEmployeSerialisation();
                    break;
                case "demarrerConsultation":
                    action = new DemarrerConsultationAction();
                    serialisation = new DemarrerConsultationSerialisation();
                    break;
                case "getConsultationEnCours":
                    action = new GetConsultationEnCoursAction();
                    serialisation = new GetConsultationEnCoursSerialisation();
                    break;
                case "getPredictions":
                    action = new GetPredictionsAction();
                    serialisation = new GetPredictionsSerialisation();
                    break; 
                case "logout":
                    action = new DeconnexionAction();
                    serialisation = new DeconnexionSerialisation();
                    break; 
                case "terminerConsultation":
                    action = new TerminerConsultationAction();
                    serialisation = new TerminerConsultationSerialisation();
                    break;
                case "...":
                    break;
            }
        }
        
        if (action != null) {
            action.executer(request);
            serialisation.serialiser(request, response);
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erreur dans les paramètres de la requête");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

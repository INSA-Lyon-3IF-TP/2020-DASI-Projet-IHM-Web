package fr.insalyon.dasi.ihm.web.action;

import javax.servlet.http.HttpServletRequest;

public class DeconnexionAction extends Action{

    @Override
    public void executer(HttpServletRequest request) {
        try{
            request.getSession(false).invalidate();
            request.setAttribute("success", Boolean.TRUE);
        }
        catch(Exception e){
            e.printStackTrace();
            request.setAttribute("success", Boolean.FALSE);
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.action;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Medium;
import static metier.service.Service.*;

/**
 *
 * @author fgelus
 */
public class ChoisirVoyanceAction extends Action{

    @Override
    public boolean executer(HttpServletRequest request) {
        Long idClient=(Long)request.getAttribute("idClient");
        Client c=trouverClient(idClient);
        String idMedium=(String)request.getParameter("idMedium");
        Long idMediumLong=Long.parseLong(idMedium);
        System.out.println("id:"+idMedium);
        Medium m=trouverMedium(idMediumLong);
        boolean demandeAcceptee=demanderVoyance(c,m);
        System.out.println("demande acceptée:"+demandeAcceptee);
        request.setAttribute("accepte",demandeAcceptee);
        
        return true;
    }


}

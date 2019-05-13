/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.DemandeDeVoyance;
import metier.service.Service;


/**
 *
 * @author fgelus
 */
public class ListeDemandeAction extends Action{

    @Override
    public boolean executer(HttpServletRequest request) {
       
        long id = Long.parseLong((String)request.getAttribute("idClient"));
        List<DemandeDeVoyance> demandes = Service.getHistoriqueDemandes(Service.trouverClient(id));
        request.setAttribute("demandes",demandes);
        return true;
       
    }
    
}

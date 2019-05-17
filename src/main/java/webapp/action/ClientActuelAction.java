/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.DemandeDeVoyance;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.service.Service;

/**
 *
 * @author fgelus
 */
public class ClientActuelAction extends Action{

    @Override
    public boolean executer(HttpServletRequest request) {
        
        long id = (Long)request.getAttribute("idEmploye");
        Employe e = Service.trouverEmploye(id);
        DemandeDeVoyance demande = Service.chercherDemandeVoyance(e);
        System.out.println("demande"+demande);
        request.setAttribute("demande", demande);
        if(demande!=null)
        {
            Client c = demande.getClient();
            List<String> predictions = Service.genererPredictions(c);
            request.setAttribute("predictions", predictions);
        }
        return true;
        
    }
    
}

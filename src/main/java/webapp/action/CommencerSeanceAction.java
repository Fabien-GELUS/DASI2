/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.action;

import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.DemandeDeVoyance;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.service.Service;
import static metier.service.Service.demanderVoyance;
import static metier.service.Service.trouverClient;
import static metier.service.Service.trouverMedium;

/**
 *
 * @author fgelus
 */
public class CommencerSeanceAction extends Action{


    @Override
    public boolean executer(HttpServletRequest request) {
        long id = (Long)request.getAttribute("idEmploye");
        Employe e = Service.trouverEmploye(id);
        DemandeDeVoyance demande = Service.chercherDemandeVoyance(e);
        Service.debuterVoyance(demande);
        return true;
    }
}

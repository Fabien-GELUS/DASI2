/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.action;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.DemandeDeVoyance;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.service.Service;


/**
 *
 * @author fgelus
 */
public class TableauDeBordAction extends Action{

    @Override
    public boolean executer(HttpServletRequest request) {
        
        List<Medium> mediums = Service.getNBVoyanceParMediumDesc();
        List<Employe> employes = Service.getNBClientsParEmployeDesc();
        request.setAttribute("mediums",mediums);
        request.setAttribute("employes",employes);
        return true;
        
    }
    
}

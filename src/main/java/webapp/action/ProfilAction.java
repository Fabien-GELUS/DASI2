/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.action;


import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.service.Service;

/**
 *
 * @author fgelus
 */
public class ProfilAction extends Action{

    @Override
    public boolean executer(HttpServletRequest request) {
        
        Long idClient=(Long)request.getAttribute("idClient");
        Client c=Service.trouverClient(idClient);
        
        request.setAttribute("client", c);
        
        return true;
        
    }
    
}

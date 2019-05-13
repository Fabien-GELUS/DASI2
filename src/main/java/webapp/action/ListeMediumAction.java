/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Medium;
import metier.service.Service;

/**
 *
 * @author fgelus
 */
public class ListeMediumAction extends Action{

    @Override
    public boolean executer(HttpServletRequest request) {
        System.out.println("mediums  ");
        List<Medium> mediums = Service.trouverTousLesMedium();
        request.setAttribute("mediums", mediums);
        
        System.out.println("mediums = "+mediums);
        return true;
        
    }
    
}

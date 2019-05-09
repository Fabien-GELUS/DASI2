/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Medium;
import metier.service.Service;
import static vue.Test.afficherData;
import static vue.Test.lireIntegerEvo;

/**
 *
 * @author fgelus
 */
public class ListeMediumAction extends Action{

    @Override
    public boolean executer(HttpServletRequest request) {
       
        List<Medium> mediums = Service.trouverTousLesMedium();
        request.setAttribute("medium", mediums);
        return true;
        /*String[] legende = {"Id","Nom","Talent","Descriptif",};
        int[] size = {4,20,12,80};
        String[][] data = new String[mediums.size()][4];
        for(int i=0;i<mediums.size();i++)
        {
            data[i][0] = mediums.get(i).getId().toString();
            data[i][1] = mediums.get(i).getNom();
            data[i][2] = mediums.get(i).getType();
            data[i][3] = mediums.get(i).getDescriptif();
        }
        afficherData(legende,size,data);
        
        System.out.println("");
        System.out.println("Tapez l'Id de celui avec qui vous souhaitez prendre rendez-vous");
        System.out.println("(Ou 0 pour quitter)");
        Integer input = -1;
        while(!(input>=0 && input <= mediums.size()))
        {
            input = lireIntegerEvo("");
            if(!(input>=0 && input <= mediums.size()))
            {
                System.out.println("/!\\ Erreur de saisie - Nombre entre 0 et "+mediums.size()+" attendu /!\\");
            }
        }*/
    }
    
}

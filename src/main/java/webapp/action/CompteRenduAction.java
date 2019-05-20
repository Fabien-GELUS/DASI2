/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import metier.modele.DemandeDeVoyance;
import metier.modele.Employe;
import metier.service.Service;

/**
 *
 * @author fgelus
 */
public class CompteRenduAction extends Action{
 private final static SimpleDateFormat HORODATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy"); // à HH:mm:ss
 
    @Override
    public boolean executer(HttpServletRequest request) {
        long id = (Long)request.getAttribute("idEmploye");
        Employe e = Service.trouverEmploye(id);
        DemandeDeVoyance demande = Service.chercherDemandeVoyance(e);
        
        String heure = request.getParameter("heure");
        System.out.println(heure);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        
        String dateheure = HORODATE_FORMAT.format(date);
        dateheure=dateheure+" "+heure;        
        
        try {
            date = sdf.parse(dateheure);
        } catch (ParseException ex) {
            System.out.println("Erreur date de naissance");
        }
        
        String commentaire = request.getParameter("commentaire");
        System.out.println(commentaire);
        Service.faireCRVoyance(demande,date,commentaire);
        return true;
    }
    
}

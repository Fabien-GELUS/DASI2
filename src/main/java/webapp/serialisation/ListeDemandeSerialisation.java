/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.serialisation;

import java.util.Date;
import java.text.SimpleDateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.DemandeDeVoyance;


/**
 *
 * @author fgelus
 */
public class ListeDemandeSerialisation extends Serialisation {
    private final static SimpleDateFormat HORODATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy"); // à HH:mm:ss
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject jsonContainer=new JsonObject();
        JsonArray jsonArrayDemandes=new JsonArray();
        List<DemandeDeVoyance> demandes=(List<DemandeDeVoyance>)request.getAttribute("demandes");
        for(DemandeDeVoyance demande:demandes){
            JsonObject jsonDemande=new JsonObject();
            jsonDemande.addProperty("datedebut", HORODATE_FORMAT.format(demande.getDate_demande()));
            
            jsonDemande.addProperty("statut", demande.getAccepte());
            jsonDemande.addProperty("nom", demande.getMedium().getNom());
            Date datefin = demande.getDate_fin();
            if(datefin!=null){
                jsonDemande.addProperty("datefin", HORODATE_FORMAT.format(demande.getDate_fin()));
            }
            jsonArrayDemandes.add(jsonDemande);
            
        }
        jsonContainer.add("demandes",jsonArrayDemandes);
        PrintWriter out=getWriterWithJsonHeader(response);
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(jsonContainer,out);  
    }
}

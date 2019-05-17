/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Astrologue;
import metier.modele.Client;
import metier.modele.DemandeDeVoyance;
import metier.modele.Medium;
import metier.modele.Voyant;

/**
 *
 * @author fgelus
 */
public class ClientActuelSerialisation extends Serialisation {
    
private final static SimpleDateFormat HORODATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy"); // à HH:mm:ss
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject jsonContainer=new JsonObject();
        DemandeDeVoyance demande =(DemandeDeVoyance)request.getAttribute("demande");
        if(demande!=null){
            jsonContainer.addProperty("haveDemande", "true");
            jsonContainer.addProperty("etape",demande.getEtape());
            List<String> predictions =(List<String>)request.getAttribute("predictions");
            Medium medium = demande.getMedium();
            Client client = demande.getClient();
            
            JsonObject jsonMedium=new JsonObject();
            jsonMedium.addProperty("id",medium.getId());
            jsonMedium.addProperty("nom", medium.getNom());
            jsonMedium.addProperty("descriptif", medium.getDescriptif());
            jsonMedium.addProperty("type", medium.getType());
            if(medium.getType().equals("Voyant")){
                jsonMedium.addProperty("specialite", ((Voyant)medium).getSpecialite());
            }else if(medium.getType().equals("Astrologue")){
                jsonMedium.addProperty("formation", ((Astrologue)medium).getFormation());
                jsonMedium.addProperty("promotion", ((Astrologue)medium).getPromotion());
            }
            jsonContainer.add("medium",jsonMedium);


            JsonObject jsonClient=new JsonObject();
            jsonClient.addProperty("Civilite", client.getCivilite());
            jsonClient.addProperty("Nom", client.getNom());
            jsonClient.addProperty("Prenom", client.getPrenom());
            jsonClient.addProperty("Naissance", HORODATE_FORMAT.format(client.getDateNaissance()));
            jsonClient.addProperty("signeZodiaque",client.getSigneZodiaque());
            jsonClient.addProperty("signeChinois", client.getSigneChinois());
            jsonClient.addProperty("couleur", client.getCouleurPorteBonheur());
            jsonClient.addProperty("animal", client.getAnimalTotem());
            jsonContainer.add("client",jsonClient);

            JsonArray jsonArrayDemandes=new JsonArray();
            List<DemandeDeVoyance> demandes=(List<DemandeDeVoyance>)client.getHistorique();
            for(DemandeDeVoyance d:demandes){
                JsonObject jsonDemande=new JsonObject();
                jsonDemande.addProperty("datedebut", HORODATE_FORMAT.format(d.getDate_demande()));

                jsonDemande.addProperty("statut", d.getAccepte());
                jsonDemande.addProperty("nom", d.getMedium().getNom());
                Date datefin = d.getDate_fin();
                if(datefin!=null){
                    jsonDemande.addProperty("datefin", HORODATE_FORMAT.format(d.getDate_fin()));
                }
                jsonDemande.addProperty("commentaire", d.getCommentaire());
                jsonArrayDemandes.add(jsonDemande);

            }
            jsonContainer.add("historique",jsonArrayDemandes);

            JsonArray jsonArrayPrediction=new JsonArray();
            String[] libelle = {"Amour","Santé","Travail"};
            for(int i=0;i<3;i++)
            {
                JsonObject jsonPrediction=new JsonObject();
                jsonPrediction.addProperty(libelle[i], predictions.get(i));
                jsonArrayPrediction.add(jsonPrediction);
            }
            jsonContainer.add("predictions",jsonArrayPrediction);
        }else{
            jsonContainer.addProperty("haveDemande", "false");
        }
        PrintWriter out=getWriterWithJsonHeader(response);
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        //gson.toJson(jsonContainer,out);  
        String json=gson.toJson(jsonContainer);
        out.print(json);
    }
}

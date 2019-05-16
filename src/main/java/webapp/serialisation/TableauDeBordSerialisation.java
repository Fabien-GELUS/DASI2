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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Employe;
import metier.modele.Medium;

/**
 *
 * @author fgelus
 */
public class TableauDeBordSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject jsonContainer=new JsonObject();
        JsonArray jsonArrayMedium=new JsonArray();
        List<Medium> mediums=(List<Medium>)request.getAttribute("mediums");
        List<Employe> employes=(List<Employe>)request.getAttribute("employes");
        for(Medium m:mediums){
            JsonObject jsonMedium=new JsonObject();
            jsonMedium.addProperty("nom", m.getNom());
            jsonMedium.addProperty("nombrevoyance", m.getNbAffectations());
            jsonArrayMedium.add(jsonMedium);
        }
        jsonContainer.add("mediums",jsonArrayMedium);
        
        JsonArray jsonArrayEmploye=new JsonArray();
        for(Employe e:employes){
            JsonObject jsonEmploye=new JsonObject();
            jsonEmploye.addProperty("nom", e.getNom());
            jsonEmploye.addProperty("nombreclient",e.getNbAffectations());
            jsonArrayEmploye.add(jsonEmploye);
        }
        jsonContainer.add("employes",jsonArrayEmploye);
        
        PrintWriter out=getWriterWithJsonHeader(response);
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(jsonContainer,out);  
    }
}

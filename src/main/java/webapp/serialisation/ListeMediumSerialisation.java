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
import metier.modele.Astrologue;
import metier.modele.Medium;
import metier.modele.Voyant;
import static metier.service.Service.trouverTousLesMedium;

/**
 *
 * @author fgelus
 */
public class ListeMediumSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject jsonContainer=new JsonObject();
        JsonArray jsonArrayMediums=new JsonArray();
        List<Medium> mediums=(List<Medium>)request.getAttribute("mediums");
        for(Medium medium:mediums){
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
            jsonArrayMediums.add(jsonMedium);
            
        }
        jsonContainer.add("mediums",jsonArrayMediums);
        PrintWriter out=getWriterWithJsonHeader(response);
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        //gson.toJson(jsonContainer,out);  
        String json=gson.toJson(jsonContainer);
        out.print(json);
    }
}

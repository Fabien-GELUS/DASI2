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
import metier.modele.Client;

/**
 *
 * @author fgelus
 */
public class ProfilSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
      

        Client c =(Client)request.getAttribute("client");
        
        JsonObject jsonClient=new JsonObject();
        jsonClient.addProperty("signeZodiaque",c.getSigneZodiaque());
        jsonClient.addProperty("signeChinois", c.getSigneChinois());
        jsonClient.addProperty("couleur", c.getCouleurPorteBonheur());
        jsonClient.addProperty("animal", c.getAnimalTotem());
            
        PrintWriter out=getWriterWithJsonHeader(response);
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(jsonClient,out);  

    }
}

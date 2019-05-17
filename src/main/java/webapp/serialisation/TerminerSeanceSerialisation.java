/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fgelus
 */
public class TerminerSeanceSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject jsonDemande=new JsonObject();
        jsonDemande.addProperty("messageRenvoye", "Séance débutée");
        PrintWriter out=getWriterWithJsonHeader(response);
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        //gson.toJson(jsonContainer,out);  
        String json=gson.toJson(jsonDemande);
        out.print(json);
    }
    
}

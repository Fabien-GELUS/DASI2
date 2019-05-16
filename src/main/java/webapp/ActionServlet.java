package webapp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Employe;
import metier.service.Service;
import webapp.action.Action;
import webapp.action.ChoisirVoyanceAction;
import webapp.action.ListeDemandeAction;
import webapp.action.ListeMediumAction;
import webapp.action.ProfilAction;
import webapp.serialisation.ChoisirVoyanceSerialisation;
import webapp.serialisation.ListeDemandeSerialisation;
import webapp.serialisation.ListeMediumSerialisation;
import webapp.serialisation.ProfilSerialisation;
import webapp.serialisation.Serialisation;

/**
 *
 * @author mzhang
 */
@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

@Override
  public void init() throws ServletException {
    super.init();
    JpaUtil.init();
  }

  @Override
  public void destroy() {
    JpaUtil.destroy();
    super.destroy();
  }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        String todo = request.getParameter("todo");
        System.out.println(todo);
            if ("connecterclient".equals(todo)){
                
                
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                
                session.setAttribute("utilisateur", login);
                response.setContentType("text/html;charset=UTF-8");
        
                PrintWriter out = response.getWriter();
                
                
                Client clientActu = Service.trouverClient(login,password);
               
                if(clientActu != null)
                {
                   out.println("{\"connexion\":true,\"message\":\"Ok\"}");
                   session.setAttribute("idClient", clientActu.getId());
                }
                else
                {
                    out.println("{\"connexion\":false,\"message\":\"Pas de Client\"}");
                }
            }else if ("connecteremploye".equals(todo)){

                String login = request.getParameter("login");
                String password = request.getParameter("password");
                
                session.setAttribute("utilisateur", login);
                response.setContentType("text/html;charset=UTF-8");
        
                PrintWriter out = response.getWriter();
                
                
                Employe employeActu = Service.trouverEmploye(login,password);
               
                if(employeActu != null)
                {
                   out.println("{\"connexion\":true,\"message\":\"Ok\"}");
                   session.setAttribute("idEmploye", employeActu.getId());
                }
                else
                {
                    out.println("{\"connexion\":false,\"message\":\"Pas d'Employe\"}");
                }
            }else if ("inscrire".equals(todo)){

                 String civ = request.getParameter("civilite");
                 String nom = request.getParameter("nom");
                 String prenom = request.getParameter("prenom");
                 String datenaiss =  request.getParameter("naissance");
                 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                 Date date = new Date();
                try {
                    date = sdf.parse(datenaiss);
                } catch (ParseException ex) {
                    System.out.println("Erreur date de naissance");
                }
                String adresse =  request.getParameter("adresse");
                String code =  request.getParameter("code");
                String ville =  request.getParameter("ville");
                String adressecomplete = adresse+" "+code+" "+ville;
                String numtel = request.getParameter("tel");
                String email = request.getParameter("mail");
                String mdp = request.getParameter("mdp");
                String mdpconf = request.getParameter("mdpconf");
                String cgu = request.getParameter("cgu");
                
                PrintWriter out = response.getWriter();
                
                if(!mdp.equals(mdpconf) )
                {
                    out.println("{\"inscription\":false,\"message\":\"Mdp différents\"}");
                }
                else if(!cgu.equals("true")){
                    out.println("{\"inscription\":false,\"message\":\"Veuillez accepter les cgu\"}");
                }
                else{
                    Client nouveau = new Client(civ,nom,prenom,date,adressecomplete,numtel,email,mdp);
                    if(Service.inscrireClient(nouveau))
                    {
                        out.println("{\"inscription\":true,\"message\":\"Ok\"}");
                    }
                    else
                    {
                        out.println("{\"inscription\":false,\"message\":\"Erreur lors de l'inscription\"}");
                    }
                }
                
            
            }else{
                String user=(String) session.getAttribute("utilisateur");
                System.out.println("user :"+user);
                if(user==null){
                    System.out.println("Forbidden(No User)");
                }else{
                    Action action = null;
                    Serialisation serialisation = null;
                    Long id;
                    System.out.println("switch");
                    switch(todo){
                        case "voyance":
                            System.out.println("dans voyance");
                            action = new ListeMediumAction();
                            serialisation = new ListeMediumSerialisation();
                            
                            break;
                        case "choisirVoyance":
                            System.out.println("dans choisirVoyance");
                            id =(Long) session.getAttribute("idClient");
                            request.setAttribute("idClient",id);
                            action = new ChoisirVoyanceAction();
                            serialisation = new ChoisirVoyanceSerialisation();
                            break;
                        case "historique":
                            System.out.println("dans choisirVoyance");
                            id =(Long)  session.getAttribute("idClient");
                            request.setAttribute("idClient",id);
                            action = new ListeDemandeAction();
                            serialisation = new ListeDemandeSerialisation();
                            break;
                        case "deconnexion":
                            session.setAttribute("utilisateur", null);
                            PrintWriter out = response.getWriter();
                            out.println("{\"deconnexion\":\"ok\"}");
                            break;
                        case "profil":
                            System.out.println("dans choisirVoyance");
                            id =(Long)  session.getAttribute("idClient");
                            request.setAttribute("idClient",id);
                            action = new ProfilAction();
                            serialisation = new ProfilSerialisation();
                            break;
                            
                    }
                    
                    if(action==null){
                        response.sendError(400,"Bad Request (Wrong TODO Parameter)");
                        
                    }else{
                        boolean actionStatus = action.executer(request);
                        if(serialisation!=null){
                            serialisation.serialiser(request,response);
                        }
                    }
                    
                }
            }
    }   
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

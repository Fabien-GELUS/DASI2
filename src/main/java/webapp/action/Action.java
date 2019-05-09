/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.action;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author fgelus
 */
public abstract class Action {
     public abstract boolean executer(HttpServletRequest request);
     
}

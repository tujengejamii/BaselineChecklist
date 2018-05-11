/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checklist;

import database.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Geofrey Nyabuto
 */
public class loadRemainingMOH extends HttpServlet {
HttpSession session;
String data,selected,county;
int counter;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            session=request.getSession();
           dbConn conn = new dbConn();
          selected=request.getParameter("selected");
          county=request.getParameter("county");
          counter=0; 
//          county="1";
//          selected=",";
          System.out.println("county :  "+county+"----selected="+selected);
          data="<option value=\"\">Add MOH Staff</option>";
          String getAllMOH="SELECT * FROM moh_staff WHERE county_id='"+county+"'";
          conn.rs=conn.st.executeQuery(getAllMOH);
          while(conn.rs.next()){
              if(selected.contains(","+conn.rs.getString(1)+",")){
                  
              }
              else{
              data+="<option value=\""+conn.rs.getString(1)+"\">"+conn.rs.getString(2)+" "+conn.rs.getString(3)+" "+conn.rs.getString(4)+"</option>";
              counter++;
              }
          }
          
          if(counter==0){
              data="<option value=\"\">No MOH Staff</option>";
          }
            out.println(data);
        } finally {
            out.close();
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
    try {
        processRequest(request, response);
    } catch (SQLException ex) {
        Logger.getLogger(loadRemainingMOH.class.getName()).log(Level.SEVERE, null, ex);
    }
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
    try {
        processRequest(request, response);
    } catch (SQLException ex) {
        Logger.getLogger(loadRemainingMOH.class.getName()).log(Level.SEVERE, null, ex);
    }
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

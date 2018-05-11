/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loaders;

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
 * @author Nyabuto Geofrey
 */
public class loadFaciAPHIA extends HttpServlet {
HttpSession session;
String aphiastaff,fname,mname,lname,fullname,selectedAPHIA,sessID;
String county="";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
         session=request.getSession();
         dbConn conn = new dbConn();
         sessID="";
         selectedAPHIA="";
         if(session.getAttribute("aphia_staff")!=null){
             selectedAPHIA=session.getAttribute("aphia_staff").toString();
         }
         
          System.out.println("selectedAPHIA====================================="+selectedAPHIA);
          
         if(request.getParameter("district_id")!=null && !request.getParameter("district_id").equals("")){
         sessID= request.getParameter("district_id");   
         }
         else{
         if(session.getAttribute("selectedDistrict")!=null){
         sessID=session.getAttribute("selectedDistrict").toString();
         }
         }
         aphiastaff="<option value=\"\">APHIA Staff</option>";
         if(!sessID.equals("")){   
         
         String getCounty="SELECT county.county_id "
                 + "FROM  district  JOIN county ON district.county_id=county.county_id WHERE district.district_id='"+sessID+"'"
                 + "";
         conn.rs=conn.st.executeQuery(getCounty);
         if(conn.rs.next()){
          county=conn.rs.getString(1);
         }
//             System.out.println("county>>>>>>>>>>>>>>>>>>>>>>>>"+county);
          String get_aphiastaff="SELECT * FROM aphia_staff WHERE county_id='"+county+"' ORDER BY fname,mname,lname";
             System.out.println("APHIASTAFF_QUERY__"+get_aphiastaff);
            conn.rs=conn.st.executeQuery(get_aphiastaff);
            while(conn.rs.next()){
                fullname="";
             fname=conn.rs.getString("fname");      
             mname=conn.rs.getString("mname");   
             lname=conn.rs.getString("lname");   
             fullname=fname+" "+mname+" "+lname;
            if(selectedAPHIA.equals(conn.rs.getString(1))){
         aphiastaff+="<option value=\""+conn.rs.getString(1) +"\" selected>"+fullname+"</option>";        
            }
            else{
            aphiastaff+="<option value=\""+conn.rs.getString(1) +"\">"+fullname+"</option>";    
            }  
            }
             if(conn.rs!=null){
            conn.rs.close();
            }
if(conn.st!=null){
            conn.st.close();
            }
         
         }
         
            System.out.println("moh staffs : "+aphiastaff);
            out.println(aphiastaff);
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
        Logger.getLogger(loadFaciAPHIA.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(loadFaciAPHIA.class.getName()).log(Level.SEVERE, null, ex);
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

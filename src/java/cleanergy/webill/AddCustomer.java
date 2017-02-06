/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author itsme
 */
public class AddCustomer extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String userID = request.getParameter("userID");
            String secret = request.getParameter("secret");
            String givenName = request.getParameter("givenName");
            String surName = request.getParameter("surName");
            String address = request.getParameter("address");
            String eMail = request.getParameter("eMail");
            String meterID = request.getParameter("meterID");
            if((userID != null) && (!userID.equals("")) &&
                        (givenName != null) && (!givenName.equals("")) &&
                        (surName != null) && (!surName.equals("")) &&
                        (secret != null) && (!secret.equals("")) &&
                        (address != null) && (!address.equals("")) &&
                        (eMail != null) && (!eMail.equals("")) &&
                        (meterID != null) && (!meterID.equals(""))){
                String addCustomerQuery = "insert into Users values ("
                            + "'" + userID + "', "
                            + "'" + givenName + "', "
                            + "'" + surName + "', "
                            + "'" + secret + "', "
                            + "'" + address + "', "
                            + "'" + eMail + "', "
                            + "'" + meterID + "');";
                int result = Validate.sqlConnection.createStatement().
                            executeUpdate(addCustomerQuery); 
            }
            
        } catch (SQLException ex) {
            System.out.println("Error in AddCustomer. " + ex.getMessage());
        }
        response.sendRedirect("listusers");
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

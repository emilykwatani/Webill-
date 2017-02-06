/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ekwatani
 */
public class JavaMySQLSelect extends HttpServlet {
      private static Connection sqlConnection = null;


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
      // our SQL SELECT query. 
      // if you only need a few columns, specify them by name instead of using "*"
      String query = "SELECT * FROM users";

      // create the java statement
      Statement st = JavaMySQLSelect.conn.createStatement();
      
      // execute the query, and get a java resultset
      ResultSet rs = st.executeQuery(query);
      
      // iterate through the java resultset
      while(rs.next())
      {
        String userID = rs.getString("userID");
        String givenName = rs.getString("givenName");
        String surName = rs.getString("surName");
        String secret = rs.getString("secret");
        String address = rs.getString("address");
        String email = rs.getString("email");
        String meterID = rs.getString("meterID");
        
       
        // print the results
        System.out.format("%s, %s, %s, %s, %s,%s, %s\n", userID, givenName, surName, secret, address, email, meterID);
      }
      st.close();
        } catch (SQLException ex) {
            Logger.getLogger(JavaMySQLSelect.class.getName()).log(Level.SEVERE, null, ex);
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
  @Override
        public void init() throws ServletException{
    super.init();
    if(sqlConnection == null){
      System.out.println("Establishing new connection to the database...");
      try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost/WeBill?" +
                                   "user=webill&password=itsme&useSSL=false");
        System.out.println("The connection was successfully established.");
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
        System.err.println("Error in establishing the connection to the database! Details follow.");
   
     System.err.println(ex.getMessage());
      }
    }
  }
            private static class conn {

        private static Statement createStatement() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public conn() {
        }
    }
}

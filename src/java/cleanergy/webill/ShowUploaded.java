/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill;

import static cleanergy.webill.Validate.sqlConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
 * @author itsme This Servlet will show the file that the user has uploaded or
 * send him/her to the fileUpload.html page
 */
public class ShowUploaded extends HttpServlet {

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
            //The full path to the file which has been uploaded should have been
            //saved in the session. Get it.
            HttpSession session = request.getSession(false);
            String imageFilePath = (String) session.getAttribute("imageFilePath");
            String userTableName = ((String) session.getAttribute("userID")).replace("-","_");
            String userTableQuery = "select * from " + userTableName +";";
            String UserTableQuery = null;
            ResultSet files = sqlConnection.createStatement().
                    executeQuery(UserTableQuery);
            
            out.println("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "    <head>\n"
                        + "        <title>WeBill file upload page</title>\n"
                        + "        <meta charset=\"UTF-8\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <table>\n"
                        + "            <tr>\n"
                        + "                <td><h1>Cleanergy</h1></td>\n"
                        + "                <td>Clean Energy for Good Life</td>\n"
                        + "            </tr>\n"
                        + "        </table>\n"
                        + "        <hr />\n"
                        + "        <table>\n"
                        + "            <tr>\n"
                        + "                <td><h1>" + imageFilePath.substring(imageFilePath.lastIndexOf("_") + 1)
                        + "</h1></td>\n"
                        + "                <td><a href=\"GPSReader\">Read GPS information of the file.</a></td>\n"
                        + "                <td><a href=\"QrReader\">Read the information from the QR code in the file.</a></td>\n"
                        +"                <td><a href=\"Logout\">Logout.</a></td>\n"
                        + "            </tr>\n"
                        + "        </table>\n"
                        + "    </body>\n"
                        + "</html>");
        } catch (SQLException ex) {
            Logger.getLogger(ShowUploaded.class.getName()).log(Level.SEVERE, null, ex);
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
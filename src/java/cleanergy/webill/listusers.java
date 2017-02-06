package cleanergy.webill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author itsme
 */
public class listusers extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>WeBill</title>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<table>\n"
                        + "<tr>\n"
                        + "    <td><h1>Cleanergy</h1></td>\n"
                        + "    <td>Clean Energy for Good Life</td>\n"
                        + "</tr>\n"
                        + "</table>\n"
                        + "<hr />\n");
            String allUsersQuery = "select * from Users;";
            ResultSet allUsers = Validate.sqlConnection.createStatement().
                        executeQuery(allUsersQuery);
            out.println("<table border=\"1\">\n"
                        + "<tr>\n"
                        + "<th>User ID</th><th>Given Name</th><th>Sur Name</th><th>Address</th>"
                        + "<th>E-mail</th><th>Meter ID</th>"
                        + "</tr>\n");
            while (allUsers.next()) {
                out.println(
                            "<tr>\n"
                            + "<td>" + allUsers.getString("userID") + "</td>\n"
                            + "<td>" + allUsers.getString("givenName") + "</td>\n"
                            + "<td>" + allUsers.getString("SurName") + "</td>\n"
                            + "<td>" + allUsers.getString("address") + "</td>\n"
                            + "<td>" + allUsers.getString("email") + "</td>\n"
                            + "<td>" + allUsers.getString("meterID") + "</td>\n"
                            + "</tr>");
            }
            out.println("</table>\n");
            out.println("<a href=\"newuser.html\">Add new customer</a>\n");
            out.println("<a href=\"Logout\">Log out</a>\n");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            System.out.println("Error in ListCustomers. " + ex.getMessage());
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

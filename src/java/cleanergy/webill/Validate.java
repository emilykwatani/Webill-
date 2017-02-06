package cleanergy.webill;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Muhammad Wannous
 */
public class Validate extends HttpServlet {
  public static Connection sqlConnection = null;

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
      HttpSession session = request.getSession();
    try (PrintWriter out = response.getWriter()) {
      //Get the parameters from the input request
      //We expect 3 parameters: userID, passWord, and userRole
      String userID = request.getParameter("UserID");
      String secret = request.getParameter("passWord");
      String role = request.getParameter("role");
      byte[] md5SecretBytes = MessageDigest.getInstance("MD5").digest(secret.getBytes());
      String md5Secret;
      StringBuilder strBuilder = new StringBuilder();
      String tmpStr;
      for (int i = 0; i < md5SecretBytes.length; i++) {
        tmpStr = Integer.toHexString(0xFF & md5SecretBytes[i]);
        if (tmpStr.length() == 1) {
          strBuilder.append('0');
        }
        strBuilder.append(tmpStr);
      }
      md5Secret = strBuilder.toString();
                  //Now we can check the credentials in the db.
            if (!userID.isEmpty() && !secret.isEmpty() && !role.isEmpty()) {
                String idSecretMatchQuery;
              //  if ("Customer".equals(role)){
               //  String idSecretMatchQuery = "select * from Users where userID='"
                 //           + userID + "' and secret='" + md5Secret + "';";
                //ResultSet matchingUser = sqlConnection.createStatement().
                  //          executeQuery(idSecretMatchQuery);
               // if (matchingUser.next()) {
                    //Here we found a matching user in the DB
                    //Save the given name in the session.
                 //   session.setAttribute("userName", matchingUser.getString("givenName"));
                    //Redirect the browser to fileUplaod.html
                   // response.sendRedirect("fileupload.html");
                    //double latitude = Double.parseDouble(matchingUser.getString("meterlat"));
                   // double longitude = Double.parseDouble(matchingUser.getString("meterlong"));
                //    String meterID = matchingUser.getString("meterID");
                  //  session.setAttribute("meterLatitude", latitude);
                   // session.setAttribute("meterLongitude", longitude);
                    //session.setAttribute("meterID", meterID);
                //}} 
           //   else if ("Admin".equals(role)){
             //       String idSecretMatchQuery = "select * from Admin where userID='"
               //             + userID + "' and secret='" + md5Secret + "';";
          //      ResultSet matchingUser = sqlConnection.createStatement().
            //                executeQuery(idSecretMatchQuery);
              //  if (matchingUser.next()) {
                    //Here we found a matching user in the DB
                    //Save the given name in the session.
                //    session.setAttribute("userName", matchingUser.getString("userID"));
                    //Redirect the browser to fileUplaod.html
                  //  response.sendRedirect("list.html");
                //}}else {
                    //If we reach here, then the credentials provided in home.html
                    //page have no match in the DB.
                    //Send the user back to home.html
                  //  response.sendRedirect("home.html");

     // if (!userID.isEmpty() && !secret.isEmpty() && !role.isEmpty()) {
       // String idSecretMatchQuery = "select * from Users where userID='" + userID + "' and secret='"
         //       + md5Secret + "';";
        //ResultSet matchingUser = sqlConnection.createStatement().executeQuery(idSecretMatchQuery);
         //if (matchingUser.next()){
                    //Here we found a matching user in the DB
                    //Save the given name in the session.
           //         session.setAttribute("userName", matchingUser.getString("givenName"));
                    //Redirect the browser to fileUplaod.html
             //       response.sendRedirect("fileupload.html");
               // } else {
                    //If we reach here, then the credentials provided in home.html
                    //page have no match in the DB.
                    //Send the user back to home.html
                 //   response.sendRedirect("home.html");
                //}
            //}
    //}catch (N/oSuchAlgorithmException | SQLException ex) {
      //System.err.println("Error in the Servlet");
      //System.err.println(ex.getMessage());
    //}
  //}
  if (role.equals("Customer")) {
                    idSecretMatchQuery = "select * from Users where userID='"
                                + userID + "' and secret='" + md5Secret + "';";
                } else {
                    idSecretMatchQuery = "select * from Admin where userID='"
                                + userID + "' and secret='" + md5Secret + "';";
                }
                ResultSet matchingUser = sqlConnection.createStatement().
                            executeQuery(idSecretMatchQuery);
                if (matchingUser.next()) {
                    //Here we found a matching user in the DB
                    //Save the given name in the session.
                    //session.setAttribute("userName", matchingUser.getString("givenName"));
                    //Redirect the browser to fileUplaod.html
                    if (role.equals("Customer")) {
                        response.sendRedirect("fileupload.html");
                        double latitude = Double.parseDouble(matchingUser.getString("meterLat"));
                        double longitude = Double.parseDouble(matchingUser.getString("meterLong"));
                        String meterID = matchingUser.getString("meterID");
                        session.setAttribute("meterLatitude", latitude);
                        session.setAttribute("meterLongitude", longitude);
                        session.setAttribute("meterID", meterID);
                        session.setAttribute("userID", userID);
                        
                    } else {
                        response.sendRedirect("listusers");
                    }
                } else {
                    //If we reach here, then the credentials provided in home.html
                    //page have no match in the DB.
                    //Send the user back to home.html
                    response.sendRedirect("home.html");
                }
            }
        } catch (NoSuchAlgorithmException | SQLException ex) {
            System.err.println("Error in the Servlet:" + ex.getMessage());
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

}

package cleanergy.webill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author itsme
 */
@MultipartConfig
public class uploadFile extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        try (PrintWriter out = response.getWriter()) {
            String userID = (String)session.getAttribute("userID");
            String userTableName = userID.replace("-","_");
            //Get the file from the request. It is a multipart file.
            final Part filePart = request.getPart("imageFile");
            OutputStream outFile;
            InputStream fileContent;
            //Get the file name
            final String partHeader = filePart.getHeader("content-disposition");
            String fileName = "";
            for (String content : partHeader.split(";")) {
                if (content.trim().startsWith("filename")) {
                    fileName = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                    break;
                }
            }
            //We will save the file in the temporary folder of the system.
            //We will add _ at the beginning of the file name.
            try {
                File imageFile = new File(System.getProperty("java.io.tmpdir") + "/_"
                                + fileName);
                outFile = new FileOutputStream(imageFile);
                fileContent = filePart.getInputStream();
                int read;
                final byte[] bytes = new byte[1024];
                while ((read = fileContent.read(bytes)) != -1) {
                    outFile.write(bytes, 0, read);
                }
                fileContent.close();
                outFile.close();
                String sqlQuery = "insert into" + userTableName + "values('"
                        + LocalDateTime.now().getMonthValue() + "','12345','" 
                        + "00000', '_" + fileName + "','" + "Bill_" +LocalDateTime.now().getMonthValue() + ".pdf");";"
                                int result = Validate.sqlConnection.createStatement().
                                        executeUpdate(sqlQuery);
                session.setAttribute("imageFilePath", imageFile.getCanonicalPath());
                response.sendRedirect("ShowUploaded");
            } catch (FileNotFoundException ex) {
                System.err.println("Error in the Servlet uploadFile: " + ex.getMessage());
            } catch (SQLException ex) {  
                Logger.getLogger(uploadFile.class.getName()).log(Level.SEVERE, null, ex);
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
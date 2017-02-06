package cleanergy.webill;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author itsme
 */
public class QrReader extends HttpServlet {

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
        String imageFilePath = (String) session.getAttribute("imageFilePath");
        String meterID = (String)session.getAttribute("meterID");
        PrintWriter out = response.getWriter();
        try {
            //Now we can re-open the image file to check the QR code
            File file = new File(imageFilePath);
            BufferedImage image;
            BinaryBitmap bitmap;
            Result result;
            image = ImageIO.read(file);
            if (null == image) {
                out.println("No bitmap detected. Probably no QR code included!");
                out.println("<br><a href=\"ShowUploaded\">ShowUploaded</a>");
            } else {
                int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
                RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
                bitmap = new BinaryBitmap(new HybridBinarizer(source));
                MultiFormatReader reader = new MultiFormatReader();
                result = reader.decode(bitmap);
                //out.println("The QR code reads:" + result.getText());
                if(meterID.equals(result.getText())){
                    out.println("The meter ID is OK");
                } else{
                    out.println("You are trying to cheat.");
                }
                out.println("<br><a href=\"ShowUploaded\">ShowUploaded</a>");
            }
        } catch (IOException | NotFoundException ex) {
            System.err.println("Error in the Servlet QrReader: " + ex.getMessage());
            out.println("There was a problem in analyzing the image you uploaded.");
            out.println("<br><a href=\"ShowUploaded\">ShowUploaded</a>");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;

/**
 *
 * @author itsme
 */
public class GPSReader extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            if ((imageFilePath != null) && !imageFilePath.isEmpty()) {
                //Reopen the file to check GPS information.
                File imageFile = new File(imageFilePath);
                //The GPs data is saved as MetaData. We need to ceck if it exists.
                if ((Sanselan.getMetadata(imageFile) != null) || 
                            (Sanselan.getMetadata(imageFile) instanceof IImageMetadata)  ) {
                    //Check if we can convert it to JpegImageMetadata
                    final IImageMetadata metadata = (IImageMetadata) Sanselan.getMetadata(imageFile);
                    //Check if we can convert it to JpegImageMetadata
                    if (metadata instanceof JpegImageMetadata) {
                        final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                        //Inside the MetaData, the GPS inforrmation is saved as EXIF data. Check if it exists.
                        if (jpegMetadata.getExif() != null) {
                            final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
                            if (null != exifMetadata.getGPS()) {
                                final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                                if (null != gpsInfo) {
                                    //Finally, we get to the GPS data.
                                    //final String gpsDescription = gpsInfo.toString();
                                    final double longitude = gpsInfo.getLongitudeAsDegreesEast();
                                    final double latitude = gpsInfo.getLatitudeAsDegreesNorth();

                                   // out.println("    " + "GPS Description: "
                                    //            + gpsDescription);
                                  //  out.println("    "
                                  //              + "GPS Longitude (Degrees East): " + longitude);
                                  //  out.println("    "
                                  //              + "GPS Latitude (Degrees North): " + latitude);
                                  double meterLatitude = (double)session.getAttribute("meterLatitude");
                                  double meterLongitude = (double)session.getAttribute("meterLongitude");
                                  if((Math.abs(meterLatitude - latitude) <=2) && (Math.abs(meterLongitude - longitude) <=2)){
                               //   out.println("GPS info is OK");
                               RequestDispatcher dispatcher = request.getRequestDispatcher("QrReader");
                               dispatcher.forward(request, response);
                                  }
                                }
                            } else {
                                out.println("No GPS data!");
                            }
                        } else {
                            out.println("No EXIF data!");
                        }

                    } else {
                        out.println("The format of the image file you uploaded "
                                    + "does not contain metadata!");
                    }
                } else {
                    out.println("No META data!");
                }
                out.println("<br><a href=\"ShowUploaded\">ShowUploaded</a>");

            } else {
                response.sendRedirect("fileUpload.html");
            }
        } catch (FileNotFoundException | ImageReadException ex) {
            System.err.println("Error in the Servlet GPSReader: " + ex.getMessage());
            response.sendRedirect("fileUpload.html");
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
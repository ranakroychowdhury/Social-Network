package Servlets;

import Models.Album;
import Models.Picture;
import Utilities.DatabaseConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ASUS on 12/20/2016.
 */
@WebServlet(name = "UploadPictureProcess", urlPatterns = "/UploadPictureProcess.do")
@MultipartConfig(maxFileSize = 16177215)
public class UploadPictureProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String albumId = request.getParameter("albumId");
        String albumName = request.getParameter("albumName");
        String creatorId = request.getParameter("creatorId");
        String userId = request.getParameter("userId");
        String picName = request.getParameter("picName");
        String caption = request.getParameter("caption");


        InputStream inputStreamPicture = null; // input stream of the upload file


        // obtains the upload file part in this multipart request
        Part filePartProfile = request.getPart("picture");
        if (filePartProfile != null) {
            // prints out some information for debugging
           // System.out.println(filePartProfile.getName());
            System.out.println(filePartProfile.getSize());
            System.out.println(filePartProfile.getContentType());

            // obtains input stream of the upload file
            inputStreamPicture = filePartProfile.getInputStream();
        }
        DatabaseConnection db = new DatabaseConnection();
        HttpSession session = request.getSession();
        int count = db.createPicture(picName,caption,userId,albumId,inputStreamPicture);
        Album currAlbum = new Album(albumId,albumName,creatorId);
        ArrayList<Picture>pictures = db.picturesInAlbum(albumId);
        if(pictures!= null){
            currAlbum.setPics(pictures);
        }
        session.setAttribute("currAlbum", currAlbum);
        RequestDispatcher rd = request.getRequestDispatcher("albums.jsp");
        rd.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

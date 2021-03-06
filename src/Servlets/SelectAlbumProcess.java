package Servlets;

import Models.Album;
import Models.Picture;
import Utilities.DatabaseConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ASUS on 12/20/2016.
 */
@WebServlet(name = "SelectAlbumProcess", urlPatterns = "/SelectAlbumProcess.do")
public class SelectAlbumProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String albumId = request.getParameter("albumId");
        String albumName = request.getParameter("albumName");
        String creatorId = request.getParameter("creatorId");
        HttpSession session = request.getSession();
        DatabaseConnection db = new DatabaseConnection();

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

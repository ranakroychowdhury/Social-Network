package Servlets;

import Utilities.DatabaseConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ASUS on 12/21/2016.
 */
@WebServlet(name = "AddCollaboratorProcess", urlPatterns = "/AddCollaboratorProcess.do")
public class AddCollaboratorProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String albumId = request.getParameter("albumId");
        String userId = request.getParameter("userId");
        DatabaseConnection db = new DatabaseConnection();
        int count = db.assignAlbumCollaborator(albumId,userId);
        HttpSession session = request.getSession();
        session.setAttribute("albumId", albumId);
        RequestDispatcher rd = request.getRequestDispatcher("albums.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

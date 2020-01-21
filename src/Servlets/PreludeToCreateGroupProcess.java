package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ASUS on 12/19/2016.
 */
@WebServlet(name = "PreludeToCreateGroupProcess", urlPatterns = "/PreludeToCreateGroupProcess.do")
public class PreludeToCreateGroupProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String createGroup = "createGroup";
        session.setAttribute("createGroup", createGroup);
        RequestDispatcher rd = request.getRequestDispatcher("group.jsp");
        rd.forward(request, response);
    }
}

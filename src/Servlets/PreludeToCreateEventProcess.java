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
 * Created by ASUS on 12/16/2016.
 */
@WebServlet(name = "PreludeToCreateEventProcess", urlPatterns = "/PreludeToCreateEventProcess.do")
public class PreludeToCreateEventProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String createEvent = "createEvent";
        session.setAttribute("createEvent", createEvent);
        RequestDispatcher rd = request.getRequestDispatcher("events.jsp");
        rd.forward(request, response);
    }
}

package Servlets;

import Models.Event;
import Models.Person;
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
 * Created by ASUS on 12/17/2016.
 */
@WebServlet(name = "MyEventsProcess", urlPatterns = "/MyEventsProcess.do")
public class MyEventsProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseConnection db = new DatabaseConnection();
        HttpSession session = request.getSession();
        Person user = (Person)session.getAttribute("currUser");
        String userId = user.getUserId();
        ArrayList<Event>myEvents = db.getMyEvents(userId);

        session.setAttribute("goingEvents", "goingEvents");
        if(myEvents!=null && !myEvents.isEmpty())session.setAttribute("myEvents", myEvents);
        RequestDispatcher rd = request.getRequestDispatcher("events.jsp");
        rd.forward(request, response);


    }
}

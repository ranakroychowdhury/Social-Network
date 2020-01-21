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
 * Created by ASUS on 12/19/2016.
 */
@WebServlet(name = "EventsInMyCityProcess", urlPatterns = "/EventsInMyCityProcess.do")
public class EventsInMyCityProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("currUser");
        String userId = user.getUserId();
        DatabaseConnection db = new DatabaseConnection();
        ArrayList<Event> eventsInMyCity = db.getMyEventsInCity(userId);
        if(eventsInMyCity!= null && !eventsInMyCity.isEmpty()){
            session.setAttribute("eventsInMyCity", eventsInMyCity);
        }
        RequestDispatcher rd = request.getRequestDispatcher("events.jsp");
        rd.forward(request, response);
    }
}

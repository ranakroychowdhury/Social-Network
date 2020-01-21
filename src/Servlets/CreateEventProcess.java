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

/**
 * Created by ASUS on 12/16/2016.
 */
@WebServlet(name = "CreateEventProcess", urlPatterns = "/CreateEventProcess.do")
public class CreateEventProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");
        String eventType = request.getParameter("eventType");
        String description = request.getParameter("description");
        String eventDate = request.getParameter("eventDate");
        String eventMonth = request.getParameter("eventMonth");
        String eventYear = request.getParameter("eventYear");
        String eventHour = request.getParameter("eventHour");
        String eventMinute = request.getParameter("eventMinute");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String roadNo = request.getParameter("roadNo");
        String buildingNo = request.getParameter("buildingNo");
        Event event = new Event(eventName,eventType,description,eventDate,eventMonth,eventYear,eventHour,eventMinute,country,city,roadNo,buildingNo);
        HttpSession session = request.getSession();
        Person user = (Person)session.getAttribute("currUser");

        event.setCreatorId(user.getUserId());
        DatabaseConnection db = new DatabaseConnection();
        int count = db.createEvent(event);
        db.close();
        if(count > 0){
            String eventId = Integer.toString(count);
            String inviteGuests = "inviteGuests";
            session.setAttribute("inviteGuests",inviteGuests);
            session.setAttribute("eventId", eventId);
            RequestDispatcher rd = request.getRequestDispatcher("events.jsp");
            rd.forward(request, response);
        }
        else{
            RequestDispatcher rd = request.getRequestDispatcher("events.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

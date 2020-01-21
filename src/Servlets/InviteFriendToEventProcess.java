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
 * Created by ASUS on 12/16/2016.
 */
@WebServlet(name = "InviteFriendToEventProcess", urlPatterns = "/InviteFriendToEventProcess.do")
public class InviteFriendToEventProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventId = request.getParameter("eventId");
        String userId = request.getParameter("userId");
        DatabaseConnection db = new DatabaseConnection();
        int count = db.inviteFriendToEvent(eventId, userId);
        if(count>0){
            //String eventId = Integer.toString(count);
            HttpSession session =request.getSession();
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

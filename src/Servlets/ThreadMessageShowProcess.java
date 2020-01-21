package Servlets;

import Models.MessageThread;
import Models.Messages;
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
 * Created by ASUS on 12/18/2016.
 */
@WebServlet(name = "ThreadMessageShowProcess", urlPatterns = "/ThreadMessageShowProcess.do")
public class ThreadMessageShowProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String threadId = request.getParameter("threadId");
        String threadName = request.getParameter("threadName");
        DatabaseConnection db = new DatabaseConnection();
        ArrayList<Messages> messages = db.getMessagesByThread(threadId);
        ArrayList<Person> participants = db.getParticipantsByThread(threadId);
        MessageThread currThread = new MessageThread(threadId,threadName);
        if(messages!=null ){
            currThread.setMessages(messages);
        }
        if(participants != null){
            currThread.setParticipants(participants);
        }
        HttpSession session = request.getSession();
        session.setAttribute("currThread", currThread);
        db.close();
        RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
        rd.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

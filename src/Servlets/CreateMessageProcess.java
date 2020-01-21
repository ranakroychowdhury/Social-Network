package Servlets;

import Utilities.DatabaseConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ASUS on 12/18/2016.
 */
@WebServlet(name = "CreateMessageProcess",urlPatterns = "/CreateMessageProcess.do")
public class CreateMessageProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String content = request.getParameter("messageContent");
        String threadId = request.getParameter("threadId");
        String userId = request.getParameter("userId");
        if(content!=null){
            DatabaseConnection db = new DatabaseConnection();
            int count = db.createNewMessage(threadId,userId,content);
            db.close();
            if(count>0){
                RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
                rd.forward(request, response);
            }
            else {
                RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
                rd.forward(request, response);
            }
        }
        else{
            RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

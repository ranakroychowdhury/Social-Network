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
 * Created by ASUS on 12/15/2016.
 */
@WebServlet(name = "CommentProcess", urlPatterns = "/CommentProcess.do")
public class CommentProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contents = request.getParameter("comment");
        String postId = request.getParameter("postId");
        String commenterId = request.getParameter("commenterId");
        DatabaseConnection db = new DatabaseConnection();
        int count = db.createComment(commenterId,contents,postId);
        db.close();
        if(count >0){
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);
        }
        else{
            System.out.println("comment didn't work");
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

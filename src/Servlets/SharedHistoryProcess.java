package Servlets;

import Models.Posts;
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
@WebServlet(name = "SharedHistoryProcess", urlPatterns = "/SharedHistoryProcess.do")
public class SharedHistoryProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String friendId = request.getParameter("friendId");
        DatabaseConnection db = new DatabaseConnection();
        ArrayList<Posts> sharedPosts = db.sharedHistory(userId, friendId);
        HttpSession session = request.getSession();
        if(sharedPosts != null && !sharedPosts.isEmpty()){
            session.setAttribute("sharedPosts", sharedPosts);
        }
        RequestDispatcher rd = request.getRequestDispatcher("friendList.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

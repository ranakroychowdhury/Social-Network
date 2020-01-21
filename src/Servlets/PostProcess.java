package Servlets;

import Models.Person;
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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ASUS on 12/12/2016.
 */
@WebServlet(name = "PostProcess" , urlPatterns = "/PostProcess.do")
public class PostProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contents = request.getParameter("post");
        String visibility = request.getParameter("visibility");
        DatabaseConnection db = new DatabaseConnection();
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("currUser");
        String userId = user.getUserId();

        int count;
        try{
            count = db.createPost(userId,contents,visibility);
            /*ArrayList<Posts>posts = db.getPosts(userId);
            if(session.getAttribute("posts") != null) session.removeAttribute("posts");
            if(!posts.isEmpty()){
                session.setAttribute("posts", posts);
            }*/
            db.close();

            if(count>0)
            {

                RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
                rd.forward(request, response);
            }
            else
            {
                //String postNot = "Your post didn't get posted.";


                RequestDispatcher rd = request.getRequestDispatcher("home.jsp");

                rd.forward(request, response);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

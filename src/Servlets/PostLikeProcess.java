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
@WebServlet(name = "PostLikeProcess", urlPatterns = "/PostLikeProcess.do")
public class PostLikeProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postId = request.getParameter("postId");
        DatabaseConnection db = new DatabaseConnection();
        try{
            System.out.println(postId);
            int count = db.postLikeIncrement(postId);
            db.close();
            if(count>0){
                System.out.println("");
                RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
                rd.forward(request, response);
            }
            else{
                System.out.println("");
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

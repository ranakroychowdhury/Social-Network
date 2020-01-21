package Servlets;

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
 * Created by ASUS on 12/13/2016.
 */
@WebServlet(name = "SearchProcess", urlPatterns = "/SearchProcess.do")
public class SearchProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        DatabaseConnection db = new DatabaseConnection();
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("currUser");
        String userId = user.getUserId();
        ArrayList<Person> persons = db.getUsersByName("%"+userName.toUpperCase()+"%", userId);
        db.close();


        session.setAttribute("searchedPersons", persons);
        System.out.println("I was here. \n\n\n got persons in session.");
        RequestDispatcher rd = request.getRequestDispatcher("searchResults.jsp");
        rd.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

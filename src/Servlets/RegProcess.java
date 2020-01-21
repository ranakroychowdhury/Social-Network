package Servlets;

import Utilities.DatabaseConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created by ASUS on 12/5/2016.
 */
@WebServlet(name = "RegProcess", urlPatterns="/RegProcess.do")
@MultipartConfig(maxFileSize = 16177215)
public class RegProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = null;
        String birthDate = request.getParameter("birthDate");
        String birthMonth = request.getParameter("birthMonth");
        String birthYear = request.getParameter("birthYear");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String country = request.getParameter("country");
        String state = request.getParameter("state");
        String city = request.getParameter("city");
        String zipCode = request.getParameter("zipCode");

        InputStream inputStreamProfile = null; // input stream of the upload file
        InputStream inputStreamCover = null; // input stream of the upload file

        // obtains the upload file part in this multipart request
        Part filePartProfile = request.getPart("profilePic");
        Part filePartCover = request.getPart("coverPhoto");
        if (filePartProfile != null) {
            // prints out some information for debugging
            System.out.println(filePartProfile.getName());
            System.out.println(filePartProfile.getSize());
            System.out.println(filePartProfile.getContentType());

            // obtains input stream of the upload file
            inputStreamProfile = filePartProfile.getInputStream();
        }

        if (filePartCover != null) {
            System.out.println(filePartCover.getName());
            System.out.println(filePartCover.getSize());
            //System.out.println(filePartCover.getContentType());

            inputStreamCover = filePartCover.getInputStream();
        }

        DatabaseConnection db = new DatabaseConnection();
        long count;
        try {
            count = db.createAccount(birthDate, birthMonth, birthYear, inputStreamProfile, userName, password, firstName, lastName, email, phone, gender, country, state, city, zipCode, inputStreamCover);
            db.close();

        if(count>0)
        {
            message = "You have registered successfully. Now give your username and password to login.";
            HttpSession session = request.getSession();
            session.setAttribute("Message", message);
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
        else
        {
            message = "You have not registered successfully.Please check if your inputs are correct.";
            HttpSession session = request.getSession();
            session.setAttribute("Message", message);
            RequestDispatcher rd = request.getRequestDispatcher("regForm.jsp");
            PrintWriter out = response.getWriter();
            //out.println("<h1>"+birthDate+"</h1>");
            rd.forward(request, response);
        }

        }
        catch(Exception e){
            e.printStackTrace();

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

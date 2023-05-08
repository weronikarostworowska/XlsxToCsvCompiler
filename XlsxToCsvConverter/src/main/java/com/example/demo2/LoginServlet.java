package com.example.demo2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate username and password
        if (username.equals("myusername") && password.equals("mypassword")) {
            // If login is successful, redirect to welcome page
            response.sendRedirect("welcome.jsp");
        } else {
            // If login fails, display error message on login page
            response.sendRedirect("error.jsp");
        }
    }
}

package twitterDupe;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author sagenixon
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.sendRedirect("login.jsp?error=1");
            return;
        }
// if valid send to hompage or stay on login page
        if (isValidLogin(username, password)) {
            request.getSession().setAttribute("username", username);
            response.sendRedirect("homepage.jsp");
        } else {
            response.sendRedirect("login.jsp?error=2");
        }
    }

    private boolean isValidLogin(String username, String password) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String selectUserQuery = "SELECT * FROM User WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectUserQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean isValid = resultSet.next();
            preparedStatement.close();
            resultSet.close();
            connection.close();
            return isValid;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

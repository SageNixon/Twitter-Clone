package twitterDupe;

import jakarta.servlet.ServletException;
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
@WebServlet("/FollowServlet")
public class FollowServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get logged-in user's username 
        String loggedInUsername = (String) request.getSession().getAttribute("username");
        int loggedInUserId = getUserIdByUsername(loggedInUsername);
        
        
        // Set loggedInUserId in session
        request.getSession().setAttribute("loggedInUserId", loggedInUserId);
        

        // Get user ID to follow
        int userIdToFollow = Integer.parseInt(request.getParameter("userId"));
        
        
        // Perform follow operation in database
        followUser(loggedInUserId, userIdToFollow);

        // Redirect back to homepage
        response.sendRedirect("homepage.jsp");
    }

    private int getUserIdByUsername(String username) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id FROM User WHERE username = ?")) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Default value if user not found or error occurs
    }

    private void followUser(int follower_id, int followee_id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Followers (follower_id, followee_id) VALUES (?, ?)")) {

            preparedStatement.setInt(1, follower_id); //logged in user
            preparedStatement.setInt(2, followee_id); //who logged in user is following

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
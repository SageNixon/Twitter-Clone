
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
@WebServlet(name = "UnfollowServlet", urlPatterns = {"/UnfollowServlet"})
public class UnfollowServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get logged-in user's username 
        String loggedInUsername = (String) request.getSession().getAttribute("username");
        int loggedInUserId = getUserIdByUsername(loggedInUsername);

        // Get user ID to unfollow
        int userIdToUnfollow = Integer.parseInt(request.getParameter("userId"));

        // Perform follow operation in database
        unfollowUser(loggedInUserId, userIdToUnfollow);
        


        // Redirect back to homepage
        response.sendRedirect("homepage.jsp");
    }

    private int getUserIdByUsername(String username) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String selectQuery = "SELECT user_id FROM User WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            int userId = -1; // Default value if user not found

            if (resultSet.next()) {
                userId = resultSet.getInt("user_id");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return userId;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void unfollowUser(int loggedInUserId, int userIdToUnfollow) {
        
      try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Followers WHERE follower_id = ? AND followee_id = ?")) {

        preparedStatement.setInt(1, loggedInUserId);
        preparedStatement.setInt(2, userIdToUnfollow);

        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
}


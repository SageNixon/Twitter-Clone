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
@WebServlet(name = "HeartTweetServlet", urlPatterns = {"/HeartTweetServlet"})
public class HeartTweetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tweetIdString = request.getParameter("tweetId");

        if (tweetIdString != null) {
            int tweetId = Integer.parseInt(tweetIdString);

            // Get logged-in user's username
                String username = (String) request.getSession().getAttribute("username");

                int userId = getUserIdByUsername(username);
                
            // Check if user has liked the tweet
            if (!hasUserLikedTweet(userId, tweetId)) {
                // Add heart to Heart table
                addHeart(userId, tweetId);

                // Update Tweet table to increase heart count
                updateTweetHeartCount(tweetId);
            }
        }

        // Redirect back to the homepage
        response.sendRedirect("homepage.jsp");
    }
        // get logged in user 
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
        
        
         private boolean hasUserLikedTweet(int userId, int tweetId) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            try {
                String selectQuery = "SELECT * FROM Heart WHERE user_id = ? AND tweet_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                    preparedStatement.setInt(1, userId);
                    preparedStatement.setInt(2, tweetId);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        return resultSet.next(); // Return true if user has liked the tweet
                    }
                }
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // if the user hasn't liked the tweet
    }
    
    
    private void addHeart(int userId, int tweetId) {
        // Add heart to the Heart table
        try {
            Connection connection = DatabaseConnection.getConnection();
            try {
                String insertQuery = "INSERT INTO Heart (user_id, tweet_id) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setInt(1, userId);
                    preparedStatement.setInt(2, tweetId);
                    preparedStatement.executeUpdate();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
        
        private void updateTweetHeartCount(int tweetId) {
        // Update the heart count in the Tweet table
        try {
            Connection connection = DatabaseConnection.getConnection();
            try {
                String updateQuery = "UPDATE Tweet SET heart_count = heart_count + 1 WHERE tweet_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setInt(1, tweetId);
                    preparedStatement.executeUpdate();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    
}
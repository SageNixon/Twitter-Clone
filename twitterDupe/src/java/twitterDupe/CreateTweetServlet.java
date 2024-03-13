package twitterDupe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author sagenixon
 */
@WebServlet("/createTweet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)  // 50MB
public class CreateTweetServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tweetContent = request.getParameter("tweetContent");

        // Get logged-in user's username 
        String username = (String) request.getSession().getAttribute("username");

        int userId = getUserIdByUsername(username);

        // Create new Tweet object
        Tweet newTweet = new Tweet();
        newTweet.setUser_id(userId);
        newTweet.setContent(tweetContent);
        newTweet.setHeart_count(0); 
        
        
        
           // Handle image upload
        Part filePart = request.getPart("photo");
        if (filePart != null && filePart.getSize() > 0) {
            newTweet.setImage(filePart.getInputStream().readAllBytes());
            newTweet.setFilename(filePart.getSubmittedFileName());
        }
       
        

        // Insert  new tweet into  database
        insertTweet(newTweet);

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

    
    
    
private void insertTweet(Tweet tweet) {
    try {
        Connection connection = DatabaseConnection.getConnection();
        String insertQuery = "INSERT INTO Tweet (user_id, content, created_at, heart_count, image, filename) VALUES (?, ?, NOW(), ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, tweet.getUser_id());
            preparedStatement.setString(2, tweet.getContent());
            preparedStatement.setInt(3, tweet.getHeart_count());
            preparedStatement.setBytes(4, tweet.getImage());
            preparedStatement.setString(5, tweet.getFilename());

            preparedStatement.executeUpdate();
        }
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}


package twitterDupe;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author sagenixon
 */

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost/twitterdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    
    //list all user tweets on homepage
       public static List<Tweet> getRecentTweets() {
        List<Tweet> tweets = new ArrayList<>();
        try {
            Connection connection = getConnection();
            String selectQuery = "SELECT * FROM Tweet ORDER BY created_at DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int tweetId = resultSet.getInt("tweet_id");
                int userId = resultSet.getInt("user_id");
                String content = resultSet.getString("content");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                int heartCount = resultSet.getInt("heart_count");
                
                
                // Retrieve image data and filename
            byte[] imageData = resultSet.getBytes("image");
            String filename = resultSet.getString("filename");
                


                Tweet tweet = new Tweet(tweetId, userId, content, createdAt, heartCount, imageData, filename);
                tweets.add(tweet);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tweets;
    }
       
    public static User getUserById(int userId) {
        User user = null;
        try {
            Connection connection = getConnection();
            String selectQuery = "SELECT * FROM User WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int user_id = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                user = new User(user_id, username, email, password);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }   
    
    
    public static byte[] getImageDataByTweetId(int tweetId) {
    try {
        Connection connection = getConnection();
        String selectQuery = "SELECT image FROM Tweet WHERE tweet_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setInt(1, tweetId);
        ResultSet resultSet = preparedStatement.executeQuery();

        byte[] imageData = null;
        if (resultSet.next()) {
            imageData = resultSet.getBytes("image");
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return imageData;
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}
    
    
    
    //list all users on right of homepage
    
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            Connection connection = getConnection();
            String selectQuery = "SELECT * FROM User";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                User user = new User(userId, username, email, password);
                users.add(user);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    
    public static List<Tweet> getTweetsByUserId(int userId) {
    List<Tweet> tweets = new ArrayList<>();

    try {
        Connection connection = getConnection();
        String selectQuery = "SELECT * FROM Tweet WHERE user_id = ? ORDER BY created_at DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int tweetId = resultSet.getInt("tweet_id");
            String content = resultSet.getString("content");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            int heartCount = resultSet.getInt("heart_count");
            
            
            
           // Retrieve image data and filename
            byte[] imageData = resultSet.getBytes("image");
            String filename = resultSet.getString("filename");
                


                Tweet tweet = new Tweet(tweetId, userId, content, createdAt, heartCount, imageData, filename);
            tweets.add(tweet);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tweets;
}
      
public static List<Tweet> getFollowedUsersTweets(int loggedInUserId) {
    List<Tweet> tweets = new ArrayList<>();

    try {
        Connection connection = getConnection();
        String selectQuery = "SELECT t.*, u.username FROM Tweet t " +
                             "JOIN Followers f ON t.user_id = f.followee_id " +
                             "JOIN User u ON t.user_id = u.user_id " +
                             "WHERE f.follower_id = ? " +
                             "ORDER BY t.created_at DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, loggedInUserId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int tweetId = resultSet.getInt("tweet_id");
                int userId = resultSet.getInt("user_id");
                String content = resultSet.getString("content");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                int heartCount = resultSet.getInt("heart_count");
                String username = resultSet.getString("username");

                // Retrieve image data and filename
                byte[] imageData = resultSet.getBytes("image");
                String filename = resultSet.getString("filename");

                Tweet tweet = new Tweet(tweetId, userId, content, createdAt, heartCount, imageData, filename);
                tweet.setUsername(username);
                tweets.add(tweet);
            }
        }

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tweets;
}



  
}
    
    
    
    
    
    
    
       


    

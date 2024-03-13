<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="java.util.List" %>
<%@page import="twitterDupe.Tweet" %>
<%@page import="twitterDupe.DatabaseConnection" %>
<%@page import="twitterDupe.User" %> 



<!DOCTYPE html>
<html>
    <head>
        <title>Twitter Clone - Homepage</title>
        <link href="styles/mainStyleCSS.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Twitter <br>
            Clone!</h1>
        
        <br>

        <a href="profile.jsp" class="profileButton">View Profile</a>


        <form action="createTweet" method="post" enctype="multipart/form-data">
            <textarea name="tweetContent" rows="2" cols="50" placeholder="Enter your tweet here"></textarea>
            <input type="file" name="photo" accept="image/*" />
          <br>
            <input type="submit" value="Tweet" class="button">
        </form>
        <br>
        <div class="wrap">
        
        <div class="list" id="tweetList">
            <h2>Recent Tweets:</h2>
           
            <%
           List<Tweet> tweets = DatabaseConnection.getRecentTweets(); // Get the list of recent tweets

           for (Tweet tweet : tweets) {
                User user = DatabaseConnection.getUserById(tweet.getUser_id());
            
            %>
            
            
            <h4><b><%= user.getUsername() %></b></h4><p> <%= tweet.getContent() %></p>
    <% if (tweet.getFilename() != null) { %>
        <img src="getImage?tweetId=<%= tweet.getTweet_id() %>" alt="tweet image" style="width: 15%">
    <% } %>
            
   
                <p><small>Posted at <%= tweet.getCreated_at() %></small></p>
                <p><small>Hearts: <%= tweet.getHeart_count() %></small></p>



                <form action="HeartTweetServlet" method="post">
                    <input type="hidden" name="tweetId" value="<%= tweet.getTweet_id() %>">
                    <input type="submit" value="Heart"  class="button">
                </form>
                    <br>

<hr class="tweetSplit">
            
            <%
               }
            %>
            </div>
            
            
            <div class="list" id="userList">
            <h2>Twitter Clone Users:</h2>
            <ul>
                <%
                    List<User> users = DatabaseConnection.getAllUsers(); // Get the list of all users

                    for (User user : users) {
                %>

                <%
                    }
                %>
            </ul>
            <ul>
                <% for (User user : users) { %>
                <li>
                    <a 
                        href="userTweets.jsp?userId=<%= user.getUser_id() %>" class="white"><%= user.getUsername() %>
                    </a>
                    <br>


                    <form action="FollowServlet" method="post" style="display:inline;">
                        <input type="hidden" name="userId" value="<%= user.getUser_id() %>">
                        <input type="submit" value="Follow"  class="button">
                    </form>
                    <form action="UnfollowServlet" method="post" style="display:inline;">
                        <input type="hidden" name="userId" value="<%= user.getUser_id() %>">
                        <input type="submit" value="Unfollow" class="button">
                    </form>
                        <br>
                        <br>

                </li>
                <% } %>
            </ul>
        </div>
            
            
            

</div>

    </body>
</html>


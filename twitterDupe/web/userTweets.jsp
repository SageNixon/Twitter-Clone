<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="twitterDupe.Tweet" %>
<%@ page import="twitterDupe.DatabaseConnection" %>
<%@ page import="twitterDupe.User" %>

<%
    int userId = Integer.parseInt(request.getParameter("userId"));
    User user = DatabaseConnection.getUserById(userId);
    List<Tweet> userTweets = DatabaseConnection.getTweetsByUserId(userId);
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= user.getUsername() %>'s Tweets</title>
    <link href="styles/mainStyleCSS.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <h1>Twitter <br>
        Clone!</h1>
    <h2><%= user.getUsername() %>'s Profile</h2>
    <hr>
    <br>

    <a href="homepage.jsp" class="profileButton">Back to homepage</a>
    <div class="wrap">
        <div class="list" id="followedTweetList">
    <h3>Tweets:</h3>

    <% if (userTweets != null && !userTweets.isEmpty()) { %>
        <ul>
            <% for (Tweet tweet : userTweets) { %>
            <li><h4><%= user.getUsername() %></h4><%= tweet.getContent() %>
                    <br>
                    
                       <% if (tweet.getFilename() != null) { %>
                    <img src="getImage?tweetId=<%= tweet.getTweet_id() %>" alt="tweet image" style="width: 15%">
                    
                    <% } %>
                    <br>
                    
                    
                    (Posted at <%= tweet.getCreated_at() %>)</li>
            
            <br>
            <hr class="tweetSplit">
            
            <% } %>
            
        </ul>
        
    <% } else { %>
        <p>No tweets to display.</p>
       
    <% } %>
        </div>
        
        
        
        
        
        
        
        
    </div>
</body>
</html>
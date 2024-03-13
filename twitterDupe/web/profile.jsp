<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="twitterDupe.DatabaseConnection" %>
<%@ page import="twitterDupe.User" %>
<%@page import="twitterDupe.Tweet" %>
<%@page import="java.util.List" %>


<%
    Integer loggedInUserIdObject = (Integer) session.getAttribute("loggedInUserId");
    int loggedInUserId = (loggedInUserIdObject != null) ? loggedInUserIdObject.intValue() : -1;
    List<Tweet> followedUsersTweets = DatabaseConnection.getFollowedUsersTweets(loggedInUserId);
%>


<!DOCTYPE html>
<html>
    <head>
        <title>Twitter Clone - Profile</title>
        <link href="styles/mainStyleCSS.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Twitter <br>
            Clone!</h1>

    <h2>My Profile</h2>
    <hr>
    <br>
    <a href="homepage.jsp" class="profileButton">Back to Homepage</a>
    <br>
    <br>
    <br>
    
    <div class="wrap">
        <div class="list" id="followedTweetList">
        <h2>Followed Users Tweets:</h2>
        <% if (followedUsersTweets != null && !followedUsersTweets.isEmpty()) { %>
            <ul>
                <% for (Tweet tweet : followedUsersTweets) { %>
                    <li>
                        <h4><b><%= tweet.getUsername() %></b></h4> <%= tweet.getContent() %> 
                        
<br>
                        <% if (tweet.getFilename() != null) { %>
                            <img src="getImage?tweetId=<%= tweet.getTweet_id() %>" alt="tweet image" style="width: 15%">
                        <% } %>
                        <br>
                        (Posted at <%= tweet.getCreated_at() %>)
                        <br>
                        <br>
                        <hr class="tweetSplit">
                    </li>
                <% } %>
            </ul>
        <% } else { %>
            <p>No tweets to display.</p>
        <% } %>
    </div>
    
            </div>
</body>
</html>
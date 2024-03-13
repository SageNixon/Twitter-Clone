<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User Login</title>
    <link href="styles/registrationCSS.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <h1>Twitter <br>
        Clone!</h1>
    <div class="exterior">
        <h1>User Login</h1>
        <form action="login" method="post">
            <div class="input">
                <label for="username">Username: </label>
                <input type="text" id="username" name="username" required>
            </div>
            <br>
            <div class="input">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <br>
            <div class="subInput">
                <input type="submit" value="Login" class="submit">
            </div>
        </form>

        <p>Don't have an account? <a href="registration.jsp">Register here</a></p>
    </div>
</body>
</html>
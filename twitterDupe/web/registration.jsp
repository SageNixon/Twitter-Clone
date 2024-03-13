<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
    <link href="styles/registrationCSS.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <h1>Twitter <br>
        Clone!</h1>
    <div class="exterior">
        <h1>User Registration</h1>
            <form action="register" method="post">
        
                <div class="input">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <br>
                <div class="input">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <br>
                <div class="input">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <br>
                <div class="subInput">
                    <input type="submit" value="Register" class="submit">
                </div>
            </form>
        <p>Already have an account? <a href="login.jsp">Login here</a></p>
    </div>
</body>
</html>
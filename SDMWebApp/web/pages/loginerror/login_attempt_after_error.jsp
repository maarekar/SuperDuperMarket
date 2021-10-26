<%--
  Created by IntelliJ IDEA.
  User: arnaudmaarek
  Date: 06/10/2020
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@page import="utils.*" %>
<%@ page import="constants.Constants" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SuperDuperMarket - Sign up</title>



    <!--Default Header=================================================================================-->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--===============================================================================================-->
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <!--===============================================================================================-->
    <!-- jQuery library -->
    <script type="text/javascript" src="common/lib/jquery-2.0.3.min.js"></script>
    <!--===============================================================================================-->
    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <!--===============================================================================================-->
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <!--===============================================================================================-->
    <!--    <link rel="icon" href="common/images/icon32.png">-->
    <!--===============================================================================================-->
    <!--    <link rel="stylesheet" type="text/css" href="common/stylesheet/navbar.css">-->
    <!--===============================================================================================-->




    <!--CSS============================================================================================-->
    <link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/sign-in/">
    <link href="pages/Signup/Signup.css" rel="stylesheet">
    <!--Scripts========================================================================================-->
    <script src="pages/Signup/Signup.js" type="text/javascript"></script>
</head>

<body class="text-center">
<div class="card shadow p-3 mb-5 bg-white rounded" style="width: 25rem">
    <% String usernameFromSession = SessionUtils.getUsername(request);%>
    <% String usernameFromParameter = request.getParameter(Constants.USERNAME) != null ? request.getParameter(Constants.USERNAME) : "";%>
    <% if (usernameFromSession == null) {%>
    <form class="form-signup" method="GET" action="login">
        <!-- <img class="mb-4" src="common/images/login_logo.png" alt="TransPool">-->
        <br>
        <label for="username" class="sr-only">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="username" required autofocus value="<%=usernameFromParameter%>">
        <p class="error"></p>
        <br>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="account_type" id="owner" value="owner" checked>
            <label class="form-check-label" for="owner">
                I'm an owner
            </label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="account_type" id="customer" value="customer">
            <label class="form-check-label" for="customer">
                I'm a customer
            </label>
        </div>
        <br><br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
    <% Object errorMessage = request.getAttribute(Constants.USER_NAME_ERROR);%>
    <% if (errorMessage != null) {%>
    <span  style="color:red;"><%=errorMessage%></span>
    <% } %>
    <% } else {%>
    <h1>Welcome back, <%=usernameFromSession%>
    </h1>
    <a href="../chatroom/chatroom.html">Click here to enter the chat room</a>
    <br/>
    <a href="login?logout=true" id="logout">logout</a>
    <% }%>
</div>

</body>
</html>

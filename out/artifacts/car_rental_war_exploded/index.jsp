<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: SI2001
  Date: 30/07/2019
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Car Rental Login</title>

        <!-- META -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- META -->

        <!-- CSS -->
        <link href="css/mio.css" rel="stylesheet" media="screen">
        <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
        <!-- CSS -->
    </head>
    <body>
        <div class="container col-md-4 col-md-offset-4">
            <br>
            <%
                if(request.getAttribute("messaggioErrore")!=null){
            %><h5 class="text-danger">${messaggioErrore}</h5><br><%
                }
            %>
            <br>
            <h3 class="text-center text-success">Car Rental Login</h3>
            <br>
            <!--<button onclick="location.href='./homePageAdmin'">Home Page Admin</button>-->
            <%
              //request.setAttribute("codice_fiscale", "BDGDNL97C12F704S");
              //request.setAttribute("password", "1234");
            %>
<%--          <div class="myForm">--%>
            <form action="./homePageAdmin" method="post"> <!-- use the controller to update session attribute -->
                <div class="form-group">
                    <input type="text" class="form-control" name="codiceFiscaleLogin" value="BDGDNL97C12F704S"/>
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" name="password" value="1234"/>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-success">Login</button>
                </div>
            </form>

            <!-- JS -->
            <script src="//code.jquery.com/jquery.js"></script>
            <script src="js/bootstrap.min.js"></script>
            <!-- JS -->
<%--          </div>--%>
        </div>
    </body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: SI2001
  Date: 01/08/2019
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Header della Pagina</title>

    <!-- META -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- META -->

    <!-- CSS -->
    <link href="css/mio.css">
    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <!-- CSS -->
</head>
<body>
<!-- Header -->
<div id="header">

    <!-- Logo -->
<%--    <h2>Car Rental Service</h2>--%>
    <!-- Nav -->
    <nav id="nav" class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand">Car Rental Service</a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <c:choose>
                    <c:when test="${sessionScope.superuserSessione == true}">
                        <li class="nav-item"><a class="nav-link" href="./homePageAdmin">Home Page</a></li> <!-- DEVO RISCRIVERE LA get, non c'è post per i link. -->
                        <li class="nav-item"><a class="nav-link" href="./parcoAuto">Parco Auto</a></li>
                        <li class="nav-item"><a class="nav-link" href="./codiciSconto">Codici Sconto</a></li>
<%--                        <li class="nav-item"><a class="nav-link" href="./creaModificaUtente?codiceFiscaleUtenteDaModificare=${sessionScope.codiceFiscaleSessione}">Profilo Utente</a></li>--%>
                    </c:when>
                    <c:when test="${sessionScope.superuserSessione == false}">
                        <li class="nav-item"><a class="nav-link" href="./homePageAdmin">Home Page</a></li> <!-- DEVO RISCRIVERE LA get, non c'è post per i link. -->
                        <li class="nav-item"><a class="nav-link" href="./parcoAuto">Parco Auto</a></li>
                        <li class="nav-item"><a class="nav-link" href="./multeUtente?codiceFiscaleUtenteConMulte=${sessionScope.codiceFiscaleSessione}">Multe</a></li>
                    </c:when>
                </c:choose>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Profilo Utente
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="./creaModificaUtente?codiceFiscaleUtenteDaModificare=${sessionScope.codiceFiscaleSessione}">Profilo Utente</a>
                        <a class="dropdown-item" href="./LogOut">LogOut</a>
                    </div>
                </li>

<%--                <li>--%>
<%--                    <button class="button btn-danger" value="LogOut" onClick="document.location.href='pages/LogOut.html'">LogOut</button>--%>
<%--                </li>--%>
            </ul>
        </div>
    </nav>

</div>
</body>
</html>

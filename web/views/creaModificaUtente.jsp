<%@ page import="app.model.entities.Utente" %><%--
  Created by IntelliJ IDEA.
  User: SI2001
  Date: 02/08/2019
  Time: 09:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Crea Nuovo Utente</title>
</head>
<body>
    <%@include file="../header.jsp"%>
    <div class="container">
<%--        <c:if test="${sessionScope.superuserSessione == true}">--%>
        <c:choose>
            <c:when test="${requestScope.datiUtenteDaModificare != null && requestScope.datiUtenteDaModificare.codiceFiscale == sessionScope.codiceFiscaleSessione}">
                <br><h4 class="text-center text-primary">Profilo utente</h4>
            </c:when>
            <c:otherwise>
                <br><h4 class="text-center text-primary">Crea o modifica un utente</h4>
            </c:otherwise>
        </c:choose>
            <c:if test="${requestScope.utenteInserito != null}">
                <c:choose>
                    <c:when test="${requestScope.utenteInserito == true}">
                        <h5 class="text-success">Utente inserito correttamente</h5>
                    </c:when>
                    <c:otherwise>
                        <h5 class="text-danger">Si è verificato un errore, l'utente non è stato inserito</h5>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${requestScope.erroreCFLungo == true}">
                <h5 class="text-danger">Codice fiscale troppo lungo, deve essere di <%=Utente.getLunghezzaCampoCodiceFiscale()%> caratteri.</h5>
            </c:if>
            <c:if test="${requestScope.erroreCFCorto == true}">
                <h5 class="text-danger">Codice fiscale troppo corto, deve essere di <%=Utente.getLunghezzaCampoCodiceFiscale()%> caratteri.</h5>
            </c:if>
            <c:if test="${requestScope.erroreCognomeLungo == true}">
                <h5 class="text-danger">Cognome troppo lungo, deve essere di massimo <%=Utente.getLunghezzaCampoCognome()%> caratteri.</h5>
            </c:if>
            <c:if test="${requestScope.erroreNomeLungo == true}">
                <h5 class="text-danger">Nome troppo lungo, deve essere di massimo <%=Utente.getLunghezzaCampoNome()%> caratteri.</h5>
            </c:if>
            <c:if test="${requestScope.errorePasswordLunga == true}">
                <h5 class="text-danger">Password troppo lunga, deve essere di massimo <%=Utente.getLunghezzaCampoPassword()%> caratteri.</h5>
            </c:if>
            <c:if test="${requestScope.erroreDataFutura == true}">
                <h5 class="text-danger">La data di nascita inserita è una data futura, inserire una data precedente alla data odierna, o la data odierna.</h5>
            </c:if>
            <c:if test="${(requestScope.datiUtenteDaModificare != null && requestScope.datiUtenteDaModificare.codiceFiscale == sessionScope.codiceFiscaleSessione) || sessionScope.superuserSessione}">
                <form action="./creaModificaUtente" method="post"> <!-- use the controller to update session attribute -->
                    <div class="form-group">
                        <div class="row">
                            <div class="col">
                                <label for="formNome">Nome:</label>
                                <input type="text" class="form-control" name="nome" id="formNome" maxlength="<%=Utente.getLunghezzaCampoNome()%>" <c:if test="${requestScope.datiUtenteDaModificare != null}"> value="${requestScope.datiUtenteDaModificare.getNome()}" </c:if> required/>
                            </div>
                            <div class="col">
                                <label for="formCognome">Cognome:</label>
                                <input type="text" class="form-control" name="cognome" id="formCognome" maxlength="<%=Utente.getLunghezzaCampoCognome()%>" <c:if test="${requestScope.datiUtenteDaModificare != null}"> value="${requestScope.datiUtenteDaModificare.getCognome()}" </c:if> required/><br><br>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="dataNascita">Data di nascita:</label>
                            <input type="date" class="form-control" name="dataNascita" id="dataNascita" onchange="TDate()" <c:if test="${requestScope.datiUtenteDaModificare != null}"> value="${requestScope.datiUtenteDaModificare.getDataNascita()}" </c:if> required/>
                        </div>
                        <div class="form-group">
                            <label for="formCodiceFiscale">Codice Fiscale:</label>
                            <input type="text" class="form-control" id="formCodiceFiscale" minlength="<%=Utente.getLunghezzaCampoCodiceFiscale()%>"  name="codiceFiscale" maxlength="<%=Utente.getLunghezzaCampoCodiceFiscale()%>" <c:if test="${requestScope.datiUtenteDaModificare != null}"> value="${requestScope.datiUtenteDaModificare.getCodiceFiscale()}" readonly </c:if> required/> <!-- pattern="(.){16,16}" title="Il codice fiscale deve essere di 16 caratteri" -->
                        </div>
                        <div class="form-group">
                            <c:choose>
                                <c:when test="${requestScope.datiUtenteDaModificare != null}">
                                    <label for="formPassword">Inserisci la nuova password:</label>
                                </c:when>
                                <c:otherwise>
                                    <label for="formPassword">Password:</label>
                                </c:otherwise>
                            </c:choose>
                            <input type="password" class="form-control" id="formPassword" name="password" maxlength="<%=Utente.getLunghezzaCampoPassword()%>" required/>
                        </div>
                        <div class="form-group">
                            <c:choose>
                                <c:when test="${requestScope.datiUtenteDaModificare != null && requestScope.datiUtenteDaModificare.codiceFiscale == sessionScope.codiceFiscaleSessione}">
                                    <br><button type="submit" class="btn btn-primary">Aggiorna profilo</button>
                                </c:when>
                                <c:otherwise>
                                    <br><button type="submit" class="btn btn-primary">Inserisci utente</button>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                </form>
            </c:if>
<%--        </c:if>--%>
    </div>
    <script>
        Date.prototype.toDateInputValue = (function() {
            var local = new Date(this);
            local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
            return local.toJSON().slice(0,10);
        });

        function TDate() {
            var UserDate = document.getElementById("dataNascita").value;
            var ToDate = new Date();

            if (new Date(UserDate).getTime() > ToDate.getTime()) {
                alert("Non puoi essere nato nel futuro");
                document.getElementById("dataNascita").value = new Date().toDateInputValue();;
                return false;
            }
            return true;
        }
    </script>
    <!-- JS -->
    <script src="//code.jquery.com/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <!-- JS -->
</body>
</html>

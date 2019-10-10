<%@ page import="app.DTO.UtenteDTO" %>
<%@ page import="app.DTO.PrenotazioneDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="app.model.entities.Multa" %>
<%@ page import="app.services.PrenotazioniService" %><%--
  Created by IntelliJ IDEA.
  User: SI2001
  Date: 07/08/2019
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Inserimento multa</title>
    </head>
    <body>
        <%@include file="../header.jsp"%>
        <div class="container">
            <% UtenteDTO utenteDTODaMultare = (UtenteDTO) request.getAttribute("utenteDTODaMultare");%>
            <br><h4 class="text-center text-primary">Crea una multa per l'utente <%=utenteDTODaMultare.getNome()%> <%=utenteDTODaMultare.getCognome()%></h4>

            <!-- Stampa messaggi di successo o errore dopo l'inserimento -->
            <c:if test="${requestScope.multaInserita != null}">
                <c:choose>
                    <c:when test="${requestScope.multaInserita == true}">
                        <h5 class="text-success">Multa inserita correttamente</h5>
                    </c:when>
                    <c:otherwise>
                        <h5 class="text-danger">Si è verificato un errore, la multa non è stata inserita</h5>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${requestScope.erroreDescrizioneLunga == true}">
                <h5 class="text-danger">Descrizione troppo lunga, non deve superare i <%=Multa.getLunghezzaCampoDescrizione()%> caratteri.</h5>
            </c:if>
            <c:if test="${requestScope.erroreDataNonCompresa == true}">
                <h5 class="text-danger">La data selezionata non è compresa tra la data di inizio e di fine della prenotazione.</h5>
            </c:if>


            <!-- se ha delle prenotazioni e sono tutte future oppure non ha prenotazioni stampo un messaggio -->
            <c:if test="${requestScope.prenotazioniDTOUtenteDaMultare == null || requestScope.prenotazioniDTOUtenteDaMultare.isEmpty()}">
                <br><h5 class="text-center text-warning">L'utente non ha effettuato prenotazioni, quindi non può essere multato</h5>
            </c:if>

            <c:if test="${requestScope.prenotazioniDTOUtenteDaMultare != null && !(requestScope.prenotazioniDTOUtenteDaMultare.isEmpty())}">
                <!-- controllo se esistono prenotazioni nel passato, altrimenti l'utente non ha prenotazioni da multare se ne ha solo future -->
                <%
                    boolean tuttePrenotazioniFuture = true;
                %>
                <c:forEach items="${requestScope.prenotazioniDTOUtenteDaMultare}" var="item">
                    <c:if test="${(item.getDataFine()).compareTo(LocalDate.now())<0}">
                        <% tuttePrenotazioniFuture = false; %>
                    </c:if>
                </c:forEach>
                <% pageContext.setAttribute("tuttePrenotazioniFuture", tuttePrenotazioniFuture); %>
                <c:if test="${tuttePrenotazioniFuture}">
                    <br><h5 class="text-center text-warning">L'utente ha prenotazioni solo future, oppure non ancora terminate. Quindi non può essere multato</h5>
                </c:if>
            </c:if>
            <!-- se utente ha prenotazioni passate creo il form -->
            <c:if test="${requestScope.prenotazioniDTOUtenteDaMultare != null && !(requestScope.prenotazioniDTOUtenteDaMultare.isEmpty()) && !tuttePrenotazioniFuture}">
                <%
                    DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    pageContext.setAttribute("pattern", pattern);
                    //LocalDate oggi = LocalDate.now();
                    //pageContext.setAttribute("oggi", oggi);
                %>
                <br>
                <form action="./creaMultaUtente" method="post"> <!-- use the controller to update session attribute -->
                    <div class="form-group">
                        <select class="form-control text-center" name="prenotazione" id="formPrenotazione">
                            <c:forEach items="${requestScope.prenotazioniDTOUtenteDaMultare}" var="item">
                                <c:if test="${(item.getDataFine()).compareTo(LocalDate.now())<0}"> <!-- posso selezionare solo le prenotazioni già terminate -->
                                    <option value="${item.getNumero()}">Numero prenotazione: ${item.getNumero()} &emsp; Veicolo: ${item.getVeicolo().getCasaCostruttrice()} ${item.getVeicolo().getModello()} &emsp; Dal: ${item.getDataInizio().format(pattern)} al: ${item.getDataFine().format(pattern)}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="formDescrizione">Descrizione:</label>
                        <input type="text" class="form-control" name="descrizione" id="formDescrizione" maxlength="<%=Multa.getLunghezzaCampoDescrizione()%>" required>
                    </div>
                    <div class="form-group">
                        <!-- Qui inserire data problema opzionale e fare controllo che sia compresa tra le date inizio e fine prenotazione INCLUSE -->
                        <label for="formDataProblema">Data problema (opzionale):</label>
                        <input type="date" class="form-control" name="dataProblema" id="formDataProblema" maxlength="<%=Multa.getLunghezzaCampoDescrizione()%>" onchange="TDate()">
                    </div>
                    <input type="hidden" name="codiceFiscaleUtenteDaMultare" value="${requestScope.codiceFiscaleUtenteDaMultare}">
                    <div class="form-group">
                        <br><button type="submit" class="btn btn-warning">Inserisci multa</button>
                    </div>
                </form>
            </c:if>

        </div>
<%--        <script>--%>
<%--            Date.prototype.toDateInputValue = (function() {--%>
<%--                var local = new Date(this);--%>
<%--                local.setMinutes(this.getMinutes() - this.getTimezoneOffset());--%>
<%--                return local.toJSON().slice(0,10);--%>
<%--            });--%>

<%--            function TDate() {--%>
<%--                var UserDate = document.getElementById("formDataProblema").value;--%>
<%--                var NumeroPrenotazione = document.getElementById("formPrenotazione").value;--%>
<%--                var FromDate = ${PrenotazioniService.getPrenotazioniService().selezionaDataInizioByNumero(Integer.parseInt(NumeroPrenotazione))};--%>
<%--                var ToDate = ${PrenotazioniService.getPrenotazioniService().selezionaDataFineByNumero(Integer.parseInt(NumeroPrenotazione))};--%>

<%--                if (new Date(UserDate).getTime() > new Date(ToDate).getTime() || new Date(UserDate).getTime() < new Date(FromDate).getTime()) {--%>
<%--                    alert("Inserisci un valore compreso tra la data inizio e fine noleggio");--%>
<%--                    document.getElementById("formDataProblema").value = new Date(FromDate).toDateInputValue();--%>
<%--                    return false;--%>
<%--                }--%>
<%--                return true;--%>
<%--            }--%>
<%--        </script>--%>
        <!-- JS -->
        <script src="//code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- JS -->
    </body>
</html>

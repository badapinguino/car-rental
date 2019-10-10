<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="app.model.entities.BuonoSconto" %><%--
  Created by IntelliJ IDEA.
  User: SI2001
  Date: 03/09/2019
  Time: 09:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Crea prenotazione</title>
    </head>
    <body>
        <%@include file="../header.jsp"%>
        <div class="container">
            <br><h4 class="text-center text-primary">Crea una nuova prenotazione</h4>

            <c:if test="${requestScope.prenotazioneInserita != null}">
                <c:choose>
                    <c:when test="${requestScope.prenotazioneInserita == true}">
                        <h5 class="text-success">Prenotazione inserita correttamente.</h5><br>
                        <c:choose>
                            <c:when test="${requestScope.valoreCodiceSconto==null}">
                                <h6 class="text-success">Importo totale prenotazione: ${requestScope.costoFinalePrenotazione}€.</h6>
                            </c:when>
                            <c:otherwise>
                                <h6 class="text-success">Importo prenotazione prima dello sconto: ${requestScope.costoTotalePreScontoPrenotazione}€.
                                    Sconto applicato: ${requestScope.valoreCodiceSconto}<c:if test="${requestScope.percentualeCodiceSconto==true}">%</c:if><c:if test="${requestScope.percentualeCodiceSconto==false}">€</c:if></h6><br>
                                <h6 class="text-success">Importo finale: ${requestScope.costoFinalePrenotazione}€</h6>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <h5 class="text-danger">Si è verificato un errore, la prenotazione non è stata inserita!</h5>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${requestScope.erroreVeicoloNonDisponibile == true}">
                <h5 class="text-danger">Il veicolo scelto non è disponibile per le date selezionate.</h5>
            </c:if>
            <c:if test="${requestScope.erroreDataInizioMaggioreDataFine == true}">
                <h5 class="text-danger">La data di inizio prenotazione è futura rispetto alla data di fine.</h5>
            </c:if>
            <c:if test="${requestScope.erroreDataNonFutura == true}">
                <h5 class="text-danger">La prenotazione non può essere effettuata per dei giorni già trascorsi oppure per la data odierna.</h5>
            </c:if>
            <c:if test="${requestScope.erroreCodiceScontoInesistente == true}">
                <h5 class="text-danger">Il codice sconto inserito non corrisponde a nessun buono sconto esistente.</h5>
            </c:if>

            <%
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                pageContext.setAttribute("pattern", pattern);
                //LocalDate oggi = LocalDate.now();
                //pageContext.setAttribute("oggi", oggi);
            %>
            <br>
            <form action="./creaModificaPrenotazioneUtente" method="post"> <!-- use the controller to update session attribute -->
                <div class="form-group">
                    <c:choose>
                        <c:when test="${requestScope.codiceVeicoloDaModificare==null}">
                            <label for="formVeicolo">Scegli il veicolo</label>
                        </c:when>
                        <c:otherwise>
                            <label for="formVeicolo" class="text-warning">Seleziona un nuovo veicolo</label>
                        </c:otherwise>
                    </c:choose>

                    <select class="form-control text-center" name="veicolo" id="formVeicolo">
                        <c:forEach items="${requestScope.listaVeicoliDTO}" var="item">
                            <option value="${item.getCodiceMezzo()}">${item.getCasaCostruttrice()} ${item.getModello()} &emsp;&emsp; Tipologia: ${item.getTipologia()} &emsp;&emsp; Al prezzo di ${item.getPrezzoGiornata()}€ al giorno.</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="formDataInizioPrenotazione">Data di inizio della prenotazione:</label>
                    <input type="date" class="form-control" name="dataInizioPrenotazione" id="formDataInizioPrenotazione" onchange="TDate()" <c:if test="${requestScope.dataInizioDaModificare != null}"> value="${requestScope.dataInizioDaModificare}" </c:if> required>
                </div>
                <div class="form-group">
                    <label for="formDataFinePrenotazione">Data del termine della prenotazione:</label>
                    <input type="date" class="form-control" name="dataFinePrenotazione" id="formDataFinePrenotazione" onchange="TDate2()" <c:if test="${requestScope.dataFineDaModificare != null}"> value="${requestScope.dataFineDaModificare}" </c:if> required>
                </div>

                <div class="form-group">
                    <label for="formCodiceSconto">Codice Sconto (opzionale):</label>
                    <input type="text" class="form-control" name="codiceSconto" id="formCodiceSconto" <c:if test="${requestScope.codiceScontoDaModificare != null}"> value="${requestScope.codiceScontoDaModificare}" </c:if> maxlength="<%=BuonoSconto.getLunghezzaCodiceSconto()%>" autocomplete="off">
                </div>
                <input type="hidden" name="codiceFiscaleUtentePrenotazione" value="${requestScope.codiceFiscaleUtentePrenotazione}">
                <c:if test="${requestScope.numeroPrenotazioneDaModificare!=null}">
                    <input type="hidden" name="numeroPrenotazioneDaModificare" value="${requestScope.numeroPrenotazioneDaModificare}">
                </c:if>
                <div class="form-group">
                    <br><button type="submit" class="btn btn-primary">Crea prenotazione</button>
                </div>
            </form>
        </div>

        <script>
            Date.prototype.toDateInputValue = (function() {
                var local = new Date(this);
                local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
                return local.toJSON().slice(0,10);
            });

            function TDate() {
                var UserDate = document.getElementById("formDataInizioPrenotazione").value;
                var ToDate = new Date();

                if (new Date(UserDate).getTime() <= ToDate.getTime()) {
                    alert("Non puoi effettuare prenotazioni per giorni passati o il giorno attuale");
                    var today = new Date();
                    var tomorrow = new Date();
                    tomorrow.setDate(today.getDate()+1);
                    document.getElementById("formDataInizioPrenotazione").value = tomorrow.toDateInputValue();
                    return false;
                }
                return true;
            }

            function TDate2() {
                var UserDate = document.getElementById("formDataFinePrenotazione").value;
                var ToDate = new Date();

                if (new Date(UserDate).getTime() <= ToDate.getTime()) {
                    alert("Non puoi effettuare prenotazioni per giorni passati o il giorno attuale");
                    var today = new Date();
                    var tomorrow = new Date();
                    tomorrow.setDate(today.getDate()+1);
                    document.getElementById("formDataFinePrenotazione").value = tomorrow.toDateInputValue();
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

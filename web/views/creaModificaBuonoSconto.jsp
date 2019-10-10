<%@ page import="app.model.entities.BuonoSconto" %><%--
  Created by IntelliJ IDEA.
  User: SI2001
  Date: 09/08/2019
  Time: 12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Crea buono sconto</title>
    </head>
    <body>
        <%@include file="../header.jsp"%>
        <div class="container">
            <c:if test="${sessionScope.superuserSessione == true}">
                <br><h4 class="text-center text-primary">Crea o modifica un buono sconto</h4>
                <c:if test="${requestScope.buonoScontoInserito != null}">
                    <c:choose>
                        <c:when test="${requestScope.buonoScontoInserito == true}">
                            <h5 class="text-success">Buono sconto inserito correttamente</h5>
                        </c:when>
                        <c:otherwise>
                            <h5 class="text-danger">Si è verificato un errore, il buono sconto non è stato inserito</h5>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${requestScope.erroreCodiceScontoLungo == true}">
                    <h5 class="text-danger">Codice sconto troppo lungo, deve essere di massimo <%=BuonoSconto.getLunghezzaCodiceSconto()%> caratteri.</h5>
                </c:if>
                <c:if test="${requestScope.valoreNegativoOZero == true}">
                    <h5 class="text-danger">Il valore del buono sconto non può essere 0 o minore di 0.</h5>
                </c:if>
                <c:if test="${requestScope.valorePercentualeOltreCento == true}">
                    <h5 class="text-danger">Il valore non può superare il cento percento se è stato impostato "percentuale" come tipo di buono sconto.</h5>
                </c:if>
                <form action="./creaModificaBuonoSconto" method="post"> <!-- use the controller to update session attribute -->
                    <div class="form-group">
                        <label for="formCodiceSconto">Codice sconto:</label>
                        <input type="text" class="form-control" name="codiceSconto" id="formCodiceSconto" maxlength="<%=BuonoSconto.getLunghezzaCodiceSconto()%>" <c:if test="${requestScope.datiBuonoScontoDaModificare != null}"> value="${requestScope.datiBuonoScontoDaModificare.getCodiceSconto()}" readonly </c:if> required/>
                    </div>
                    <div class="form-group">
<%--                        <div class="form-check form-check-inline">--%>
<%--                        <div class="row">--%>
                        <div class="form-group col-md-6">
                            <c:choose>
                                <c:when test="${requestScope.datiBuonoScontoDaModificare.isPercentuale()}">
                                    <input type="radio" class="form-check-input" id="formPercentualeT" name="percentuale" value="true" checked="checked">
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" class="form-check-input" id="formPercentualeT" name="percentuale" value="true">
                                </c:otherwise>
                            </c:choose>
                            <label class="form-check-label" for="formPercentualeT">
                                Sconto percentuale
                            </label>
                        </div>
                        <div class="form-group col-md-6">
                            <c:choose>
                                <c:when test="${requestScope.datiBuonoScontoDaModificare.isPercentuale()}">
                                    <input type="radio" class="form-check-input" id="formPercentualeF" name="percentuale" value="false">
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" class="form-check-input" id="formPercentualeF" name="percentuale" value="false" checked="checked">
                                </c:otherwise>
                            </c:choose>

                            <label class="form-check-label" for="formPercentualeF">
                                Sconto contante
                            </label>
<%--                            </div>--%>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="formValore">Valore:</label>
                        <input type="number" step="0.01" class="form-control" name="valore" id="formValore" min="0.01" <c:if test="${requestScope.datiBuonoScontoDaModificare != null}"> value="${requestScope.datiBuonoScontoDaModificare.getValore()}" </c:if> required/>
                    </div>
                    <div class="form-group">
                        <br><button type="submit" class="btn btn-primary">Inserisci buono sconto</button>
                    </div>
                </form>
            </c:if>
        </div>

        <!-- JS -->
        <script src="//code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- JS -->
    </body>
</html>

<%@ page import="app.model.entities.Veicolo" %>
<%@ page import="app.DAO.DAOImplements.VeicoliDAO" %><%--
  Created by IntelliJ IDEA.
  User: SI2001
  Date: 08/08/2019
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Crea o modifica veicolo</title>
    </head>
    <body>
        <%@include file="../header.jsp"%>
        <div class="container">
            <c:if test="${sessionScope.superuserSessione == true}">
                <br><h4 class="text-center text-primary">Crea o modifica un veicolo</h4>
                <c:if test="${requestScope.veicoloInserito != null}">
                    <c:choose>
                        <c:when test="${requestScope.veicoloInserito == true}">
                            <h5 class="text-success">Veicolo inserito correttamente</h5>
                        </c:when>
                        <c:otherwise>
                            <h5 class="text-danger">Si è verificato un errore, il veicolo non è stato inserito</h5>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${requestScope.erroreCodiceMezzoLungo == true}">
                    <h5 class="text-danger">Codice mezzo troppo lungo, deve essere di massimo <%=Veicolo.getLunghezzaCampoCodiceMezzo()%> caratteri.</h5>
                </c:if>
                <c:if test="${requestScope.erroreTargaLunga == true}">
                    <h5 class="text-danger">Targa troppo lunga, deve essere di massimo <%=Veicolo.getLunghezzaCampoTarga()%> caratteri.</h5>
                </c:if>
                <c:if test="${requestScope.erroreCasaCostruttriceLunga == true}">
                    <h5 class="text-danger">Casa costruttrice troppo lunga, deve essere di massimo <%=Veicolo.getLunghezzaCampoCasaCostruttrice()%> caratteri.</h5>
                </c:if>
                <c:if test="${requestScope.erroreModelloLungo == true}">
                    <h5 class="text-danger">Nome modello troppo lungo, deve essere di massimo <%=Veicolo.getLunghezzaCampoModello()%> caratteri.</h5>
                </c:if>
                <c:if test="${requestScope.erroreAnnoPiccolo == true}">
                    <h5 class="text-danger">L'anno inserito non è valido, deve essere superiore o uguale al 1900.</h5>
                </c:if>
                <c:if test="${requestScope.errorePrezzoGiornataBasso == true}">
                    <h5 class="text-danger">Il prezzo inserito non è valido, deve essere superiore a 0.</h5>
                </c:if>
                <c:if test="${requestScope.erroreTipologiaLunga == true}">
                    <h5 class="text-danger">Nome tipologia veicolo troppo lunga, deve essere di massimo <%=Veicolo.getLunghezzaCampoTipologia()%> caratteri.</h5>
                </c:if>
                <form action="./creaModificaVeicolo" method="post"> <!-- use the controller to update session attribute -->
                    <div class="form-group">
                        <div class="row">
                            <div class="col">
                                <label for="formCasaCostruttrice">Casa costruttrice:</label>
                                <input type="text" class="form-control" name="casaCostruttrice" id="formCasaCostruttrice" maxlength="<%=Veicolo.getLunghezzaCampoCasaCostruttrice()%>" <c:if test="${requestScope.datiVeicoloDaModificare != null}"> value="${requestScope.datiVeicoloDaModificare.getCasaCostruttrice()}" </c:if> required/>
                            </div>
                            <div class="col">
                                <label for="formModello">Modello:</label>
                                <input type="text" class="form-control" name="modello" id="formModello" maxlength="<%=Veicolo.getLunghezzaCampoModello()%>" <c:if test="${requestScope.datiVeicoloDaModificare != null}"> value="${requestScope.datiVeicoloDaModificare.getModello()}" </c:if> required/><br><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <label for="formCodiceMezzo">Codice mezzo:</label>
                                <input type="text" class="form-control" name="codiceMezzo" id="formCodiceMezzo" maxlength="<%=Veicolo.getLunghezzaCampoCodiceMezzo()%>" <c:if test="${requestScope.datiVeicoloDaModificare != null}"> value="${requestScope.datiVeicoloDaModificare.getCodiceMezzo()}" readonly </c:if> required/>
                            </div>
                            <div class="col">
                                <label for="formTarga">Targa:</label>
                                <input type="text" class="form-control" name="targa" id="formTarga" maxlength="<%=Veicolo.getLunghezzaCampoTarga()%>" <c:if test="${requestScope.datiVeicoloDaModificare != null}"> value="${requestScope.datiVeicoloDaModificare.getTarga()}" </c:if> required/><br><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <label for="formAnno">Anno:</label>
                                <input type="number" step="1" class="form-control" name="anno" id="formAnno" min="1900" <c:if test="${requestScope.datiVeicoloDaModificare != null}"> value="${requestScope.datiVeicoloDaModificare.getAnno()}" </c:if> required/>
                            </div>
                            <div class="col">
                                <label for="formPrezzoGiornata">Prezzo a giornata:</label>
                                <input type="number" step="0.01" class="form-control" name="prezzoGiornata" id="formPrezzoGiornata" min="0.01" <c:if test="${requestScope.datiVeicoloDaModificare != null}"> value="${requestScope.datiVeicoloDaModificare.getPrezzoGiornata()}" </c:if> <c:if test="${requestScope.datiVeicoloDaModificare == null}">value="0.01"</c:if> required/><br><br>
                            </div>
                        </div>
                        <div class="form-group">
                            <c:if test="${requestScope.datiVeicoloDaModificare != null}">
                                <label for="formTipologia">Nuova tipologia:</label>
                            </c:if>
                            <c:if test="${requestScope.datiVeicoloDaModificare == null}">
                                <label for="formTipologia">Tipologia:</label>
                            </c:if>
                            <select class="form-control text-center" name="tipologia" id="formTipologia">
                                <option value="Berlina">Berlina</option>
                                <option value="City car">City car</option>
                                <option value="Furgone">Furgone</option>
                                <option value="Lusso">Lusso</option>
                                <option value="Monovolume">Monovolume</option>
                                <option value="Sportiva">Sportiva</option>
                                <option value="Supercar">Supercar</option>
                                <option value="Suv">Suv</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <br><button type="submit" class="btn btn-primary">Inserisci veicolo</button>
                        </div>
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

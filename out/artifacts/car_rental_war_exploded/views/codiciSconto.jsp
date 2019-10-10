<%@ page import="app.DTO.BuonoScontoDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: SI2001
  Date: 09/08/2019
  Time: 09:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Codici sconto</title>
    </head>
    <body>
        <%@include file="../header.jsp"%>
        <div class="container">
            <br><h3 class="text-primary">Buoni sconto</h3><br>
            <!-- pulsante per creare un nuovo veicolo -->
            <button onclick="location.href='./creaModificaBuonoSconto'" class="btn btn-primary">Crea un nuovo buono sconto</button><br><br>

            <c:if test="${requestScope.buonoScontoDTOEliminato != null}">
                <h5 class="text-success">Il buono sconto <%=((BuonoScontoDTO) request.getAttribute("buonoScontoDTOEliminato")).getCodiceSconto()%> è stato eliminato</h5><br>
            </c:if>
            <c:if test="${requestScope.erroreChiaveEsterna != null}">
                <h5 class="text-danger"><c:out value="${requestScope.erroreChiaveEsterna}"/></h5>
            </c:if>
            <h4 class="text-center text-secondary">Tabella buoni sconto</h4><br>
            <!-- tabella -->
            <table id="tabellaBuoniSconto" class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th scope="col" class="text-center">Codice sconto</th>
                    <th scope="col" class="text-center">Valore</th>
                    <c:if test="${sessionScope.superuserSessione == true}">
                        <th scope="col" class="text-center">Eliminazione</th>
                        <th scope="col" class="text-center">Modifica</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <%
                    List<BuonoScontoDTO> listaBuoniSconto = (List<BuonoScontoDTO>) request.getAttribute("listaBuoniSconto");
                    if(listaBuoniSconto!=null && !listaBuoniSconto.isEmpty()){
                        int contatore = 0;
                        for (BuonoScontoDTO b : listaBuoniSconto) {
                            pageContext.setAttribute("b", b);
                %>
                <tr>
                    <%--                                    <th scope="row"></th>--%>
                    <td class="text-center"><%=b.getCodiceSconto()%></td>
                    <c:if test="${b.isPercentuale()}">
                        <td class="text-center"><%=b.getValore()%>%</td>
                    </c:if>
                    <c:if test="${!b.isPercentuale()}">
                        <td class="text-center"><%=b.getValore()%>€</td>
                    </c:if>
                    <c:if test="${sessionScope.superuserSessione == true}">
                        <!-- Pulsanti relativi ad ogni buono sconto, che può vedere solo l'admin -->
                        <td class="text-center">
                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#eliminaBuonoScontoModal<%=contatore%>">Elimina</button>
                        </td>

                        <%--        Inserisco qui la modale di bootstrap per eliminare il buono sconto--%>
                        <div class="modal fade" tabindex="-1" id="eliminaBuonoScontoModal<%=contatore%>"
                             data-keyboard="false" data-backdrop="static">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title text-danger">Eliminazione buono sconto</h4>
                                        <button type="button" class="close" data-dismiss="modal">
                                            &times;
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Sei sicuro di voler eliminare il codice sconto <%=b.getCodiceSconto()%> da te selezionato? Verranno eliminate anche tutte le prenotazioni ad esso associate</p>
                                    </div>
                                    <div class="modal-footer">
                                        <form action="./codiciSconto" method="post" class="text-center">
                                            <input type="hidden" name="codiceScontoDaEliminare" value="<%=b.getCodiceSconto()%>"/>
                                            <input type="hidden" name="buonoScontoDaEliminare" value="true">
                                            <button type="submit" class="btn btn-danger">Elimina</button>
                                        </form>
                                        <button type="button" class="btn btn-secondary"
                                                data-dismiss="modal">Annulla</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <td>
                            <form action="./creaModificaBuonoSconto" method="get" class="text-center"> <!-- Riutilizzo stessa pagina della creazione/modifica -->
                                <input type="hidden" name="codiceScontoDaModificare" value="<%=b.getCodiceSconto()%>"/>
                                <button type="submit" class="btn btn-outline-primary">Modifica</button>
                            </form>
                        </td>
                    </c:if>
                </tr>
                <%
                        contatore++;
                    }
                }else{
                %>
                <tr>
                    <td>Non esistono codici sconto</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
        <!-- JS -->
        <script src="//code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- JS -->
    </body>
</html>

<%@ page import="app.DTO.UtenteDTO" %>
<%@ page import="app.DTO.MultaDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: SI2001
  Date: 06/08/2019
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Multe</title>
    </head>
    <body>
        <%@include file="../header.jsp"%>
        <div class="container">
            <br><h4 class="text-center text-primary">Multe dell'utente <%=((UtenteDTO) request.getAttribute("utenteDTOMulte")).getNome()%> <%=((UtenteDTO) request.getAttribute("utenteDTOMulte")).getCognome()%></h4><br>
            <c:if test="${sessionScope.superuserSessione == true}">
                <form action="./creaMultaUtente" method="get"> <!-- use the controller to update session attribute -->
                    <input type="hidden" name="codiceFiscaleUtenteDaMultare" value="<%=((UtenteDTO) request.getAttribute("utenteDTOMulte")).getCodiceFiscale()%>"/>
                    <button type="submit" class="btn btn-primary">Inserisci una nuova multa</button>
                </form>
            </c:if>
            <!-- tabella -->
            <table id="tabellaUtenti" class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th scope="col" class="text-center">Codice</th>
                    <th scope="col" class="text-center">Data problema</th>
                    <th scope="col" class="text-center">Descrizione</th>
                    <th scope="col" class="text-center">Numero prenotazione</th>
                    <th scope="col" class="text-center">Data inizio prenotazione</th>
                    <th scope="col" class="text-center">Data fine prenotazione</th>
                    <th scope="col" class="text-center">Modello</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<MultaDTO> listaMulte = (List<MultaDTO>) request.getAttribute("multeDTOUtente");
                    if(listaMulte!=null && !listaMulte.isEmpty()){
                        for (MultaDTO m : listaMulte) {
                %>
                <tr>
                    <%--                                    <th scope="row"></th>--%>
                    <td class="text-center"><%=m.getCodice()%></td>
                    <td class="text-center">
                        <%
                            String dataProblemaStringDDMMYYYY = "";
                            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            if(m.getDataProblema()!=null){
                                dataProblemaStringDDMMYYYY = m.getDataProblema().format(pattern);
                            }
                        %><%=dataProblemaStringDDMMYYYY%>
                    </td>
                    <td class="text-center"><%=m.getDescrizione()%></td>
                    <td class="text-center"><%=m.getPrenotazione().getNumero()%></td>
                    <td class="text-center"><%
                        String dataInizioPrenotazioneStringDDMMYYYY = m.getPrenotazione().getDataInizio().format(pattern);
                        %><%=dataInizioPrenotazioneStringDDMMYYYY%>
                    </td>
                    <td class="text-center"><%
                        String dataFinePrenotazioneStringDDMMYYYY = m.getPrenotazione().getDataFine().format(pattern);
                        %><%=dataFinePrenotazioneStringDDMMYYYY%>
                    </td>
                    <td class="text-center"><%=m.getPrenotazione().getVeicolo().getCasaCostruttrice()%> <%=m.getPrenotazione().getVeicolo().getModello()%></td>
<%--                    <c:if test="${sessionScope.superuserSessione == true}">--%>
                        <!-- Inserire qui il pulsante per approvare se l'utente è moroso -->
                        <%--                        <td>--%>
                        <%--                            <form action="./creaModificaUtente" method="get"> <!-- Riutilizzo stessa pagina della creazione/modifica -->--%>
                        <%--                                <input type="hidden" name="codiceFiscaleUtenteDaModificare" value="<%=u.getCodiceFiscale()%>"/>--%>
                        <%--                                <button type="submit" class="btn btn-outline-primary">Modifica</button>--%>
                        <%--                            </form>--%>
                        <%--                        </td>--%>
<%--                    </c:if>--%>
                </tr>
                <%
                    }
                }else{
                %>
                <tr>
                    <td>L'utente non ha preso multe</td>
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

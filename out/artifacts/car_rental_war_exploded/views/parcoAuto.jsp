<%@ page import="app.DTO.VeicoloDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: SI2001
  Date: 08/08/2019
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Parco Auto</title>
    </head>
    <body>
        <%@include file="../header.jsp"%>

        <div class="container">
            <br><h3 class="text-primary">Parco auto</h3><br>
            <c:if test="${sessionScope.superuserSessione == true}">
                <!-- pulsante per creare un nuovo veicolo -->
                <button onclick="location.href='./creaModificaVeicolo'" class="btn btn-primary">Crea un nuovo veicolo</button><br><br>
            </c:if>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <div class="form-check form-check-inline">
                        <input type="radio" class="form-check-input" id="r1" name="colonnaSelezionataRicerca" value="casaCostruttrice" checked="checked">
                        <label class="form-check-label" for="r1">
                            Casa costruttrice &emsp; &ensp;
                        </label>
                        <input type="radio" class="form-check-input" id="r2" name="colonnaSelezionataRicerca" value="modello">
                        <label class="form-check-label" for="r2">
                            Modello &emsp;
                        </label>
                        <input type="radio" class="form-check-input" id="r3" name="colonnaSelezionataRicerca" value="tipologia">
                        <label class="form-check-label" for="r3">
                            Tipologia &emsp;
                        </label>
                        <input type="radio" class="form-check-input" id="r4" name="colonnaSelezionataRicerca" value="codiceMezzo">
                        <label class="form-check-label" for="r4">
                            Codice mezzo &emsp; &ensp;
                        </label>
                        <input type="radio" class="form-check-input" id="r5" name="colonnaSelezionataRicerca" value="targa">
                        <label class="form-check-label" for="r5">
                            Targa &emsp;
                        </label>
                    </div>
                </div>
                <!-- barra di ricerca sulla tabella -->
                <div class="form-group col-md-6">
                    <input type="text" class="form-control" id="ricercaSuTabella" onkeyup="myFunction()" placeholder="Cerca nella tabella...">
                </div>
            </div>

            <c:if test="${requestScope.veicoloDTOEliminato != null}">
                <h5 class="text-success">Il veicolo <%=((VeicoloDTO) request.getAttribute("veicoloDTOEliminato")).getCasaCostruttrice()%> <%=((VeicoloDTO) request.getAttribute("veicoloDTOEliminato")).getCasaCostruttrice()%> con codice <%=((VeicoloDTO) request.getAttribute("veicoloDTOEliminato")).getCodiceMezzo()%> è stato eliminato</h5><br>
            </c:if>
            <c:if test="${requestScope.erroreChiaveEsterna != null}">
                <h5 class="text-danger"><c:out value="${requestScope.erroreChiaveEsterna}"/></h5>
            </c:if>
            <h4 class="text-center text-secondary">Tabella veicoli</h4><br>
            <!-- tabella -->
            <table id="tabellaUtenti" class="table table-striped table-bordered">
                <%--                <caption>--%>
                <%--                    <p>Tabella Utenti</p>--%>
                <%--                </caption>--%>
                <thead>
                    <tr>
                        <th scope="col" class="text-center">Codice mezzo</th>
                        <th scope="col" class="text-center">Targa</th>
                        <th scope="col" class="text-center">Casa costruttrice</th>
                        <th scope="col" class="text-center">Modello</th>
                        <th scope="col" class="text-center">Anno immatricolazione</th>
                        <th scope="col" class="text-center">Tipologia</th>
                        <th scope="col" class="text-center">Prezzo a giornata</th>
                        <c:if test="${sessionScope.superuserSessione == true}">
                            <th scope="col" class="text-center">Eliminazione</th>
                            <th scope="col" class="text-center">Modifica</th>
    <%--                        <th scope="col" class="text-center">Visualizzare prenotazioni</th>--%>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<VeicoloDTO> listaVeicoli = (List<VeicoloDTO>) request.getAttribute("listaVeicoli");
                        if(listaVeicoli!=null && !listaVeicoli.isEmpty()){
                            int contatore = 0;
                            for (VeicoloDTO v : listaVeicoli) {
                    %>
                        <tr>
                            <%--                                    <th scope="row"></th>--%>
                            <td class="text-center"><%=v.getCodiceMezzo()%></td>
                            <td class="text-center"><%=v.getTarga()%></td>
                            <td class="text-center"><%=v.getCasaCostruttrice()%></td>
                            <td class="text-center"><%=v.getModello()%></td>
                            <td class="text-center"><%=v.getAnno()%></td>
                            <td class="text-center"><%=v.getTipologia()%></td>
                            <td class="text-center"><%=v.getPrezzoGiornata()%>€</td>
                            <c:if test="${sessionScope.superuserSessione == true}">
                                <!-- Pulsanti relativi ad ogni veicolo, che può vedere solo l'admin -->
                                <td class="text-center">
                                    <button type="button" class="btn btn-danger " data-toggle="modal" data-target="#eliminaVeicoloModal<%=contatore%>">Elimina</button>
                                </td>

                                <div class="modal fade" tabindex="-1" id="eliminaVeicoloModal<%=contatore%>"
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
                                                <p>Sei sicuro di voler eliminare il veicolo <%=v.getCodiceMezzo()%> da te selezionato? Verranno eliminate anche tutte le prenotazioni e le multe ad esso associate</p>
                                            </div>
                                            <div class="modal-footer">
                                                <form action="./parcoAuto" method="post"> <!-- non è richiesta una pagina di eliminazione ma solo conferma (magari con get?, non so) -->
                                                    <input type="hidden" name="codiceMezzoDaEliminare" value="<%=v.getCodiceMezzo()%>"/>
                                                    <input type="hidden" name="veicoloDaEliminare" value="true">
                                                    <button type="submit" class="btn btn-danger">Elimina</button>
                                                </form>
                                                <button type="button" class="btn btn-secondary"
                                                        data-dismiss="modal">Annulla</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <td>
                                    <form action="./creaModificaVeicolo" method="get"> <!-- Riutilizzo stessa pagina della creazione/modifica -->
                                        <input type="hidden" name="codiceMezzoDaModificare" value="<%=v.getCodiceMezzo()%>"/>
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
                            <td>Non esistono veicoli registrati</td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>

        <script>
            function myFunction() {
                //cerco colonna selezionata dal radio button
                if (document.getElementById('r1').checked) {
                    valore_colonna = document.getElementById('r1').value;
                }
                if (document.getElementById('r2').checked) {
                    valore_colonna = document.getElementById('r2').value;
                }
                if (document.getElementById('r3').checked) {
                    valore_colonna = document.getElementById('r3').value;
                }
                if (document.getElementById('r4').checked) {
                    valore_colonna = document.getElementById('r4').value;
                }
                if (document.getElementById('r5').checked) {
                    valore_colonna = document.getElementById('r5').value;
                }
                switch (valore_colonna) {
                    case "casaCostruttrice": numero_colonna=2;
                        break;
                    case "modello": numero_colonna=3;
                        break;
                    case "tipologia": numero_colonna=5;
                        break;
                    case "codiceMezzo": numero_colonna=0;
                        break;
                    case "targa": numero_colonna=1;
                }

                // Declare variables
                var input, filter, table, tr, td, i, txtValue;
                input = document.getElementById("ricercaSuTabella");
                filter = input.value.toUpperCase();
                table = document.getElementById("tabellaUtenti");
                tr = table.getElementsByTagName("tr");

                // Loop through all table rows, and hide those who don't match the search query
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[numero_colonna];
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }
        </script>

        <!-- JS -->
        <script src="//code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- JS -->
    </body>
</html>

<%@ page import="app.DTO.UtenteDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="app.services.UtentiService" %>
<%@ page import="app.services.MulteService" %>
<%--
  Created by IntelliJ IDEA.
  User: SI2001
  Date: 31/07/2019
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Home Page</title>
    </head>
    <body>
        <%@include file="../header.jsp"%>
        <div class="container">
            <br><h3 class="text-primary">Benvenuto ${nome} ${cognome}</h3><br>
            <!-- pulsante per creare un nuovo utente -->
            <button onclick="location.href='./creaModificaUtente'" class="btn btn-primary">Crea un nuovo customer</button><br><br>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <div class="form-check form-check-inline">
                        <input type="radio" class="form-check-input" id="r1" name="colonnaSelezionataRicerca" value="nome" checked="checked">
                        <label class="form-check-label" for="r1">
                            Nome &emsp;&emsp;&emsp;&emsp;&ensp;
                        </label>
                        <input type="radio" class="form-check-input" id="r2" name="colonnaSelezionataRicerca" value="cognome">
                        <label class="form-check-label" for="r2">
                            Cognome &emsp;&emsp;&emsp;&emsp;&ensp;
                        </label>
                        <input type="radio" class="form-check-input" id="r3" name="colonnaSelezionataRicerca" value="codiceFiscale">
                        <label class="form-check-label" for="r3">
                            Codice Fiscale &emsp;&emsp;&emsp;&ensp;
                        </label>
            <%--        <input type="radio" id="r4" name="colonnaSelezionataRicerca" value="amministratore"> Amministratore <!-- NON VA, comunque in teoria da mettere in c:if ma in realtà andrebbe tutto il radio dentro -->--%>
                    </div>
                </div>
                <!-- barra di ricerca sulla tabella -->
                <div class="form-group col-md-6">
                    <input type="text" class="form-control" id="ricercaSuTabella" onkeyup="myFunction()" placeholder="Cerca nella tabella...">
                </div>
            </div>

            <c:if test="${requestScope.utenteDTOEliminato != null}">
                <h5 class="text-success">Utente <%=((UtenteDTO) request.getAttribute("utenteDTOEliminato")).getNome()%> <%=((UtenteDTO) request.getAttribute("utenteDTOEliminato")).getCognome()%> eliminato</h5><br>
            </c:if>
            <h4 class="text-center text-secondary">Tabella utenti</h4><br>
            <!-- tabella -->
            <table id="tabellaUtenti" class="table table-striped table-bordered">
<%--                <caption>--%>
<%--                    <p>Tabella Utenti</p>--%>
<%--                </caption>--%>
                <thead>
                    <tr>
                        <th scope="col" class="text-center">Nome</th>
                        <th scope="col" class="text-center">Cognome</th>
                        <th scope="col" class="text-center">Codice Fiscale</th>
                        <th scope="col" class="text-center">Data di Nascita</th>
                        <c:if test="${sessionScope.superuserSessione == true}">
                            <th scope="col" class="text-center">Utente moroso</th>
                            <th scope="col" class="text-center">Amministratore</th>
                            <th scope="col" class="text-center">Modifica dati</th>
                            <th scope="col" class="text-center">Eliminazione</th>
                            <th scope="col" class="text-center">Multe utente</th>
                            <th scope="col" class="text-center">Visualizzare prenotazioni</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<UtenteDTO> listaUtenti = (List<UtenteDTO>) request.getAttribute("listaUtenti");
                        if(listaUtenti!=null && !listaUtenti.isEmpty()){
                            int contatore = 0;
                            for (UtenteDTO u : listaUtenti) {
                                %>
                                <tr>
<%--                                    <th scope="row"></th>--%>
                                    <td class="text-center"><%=u.getNome()%></td>
                                    <td class="text-center"><%=u.getCognome()%></td>
                                    <td class="text-center"><%=u.getCodiceFiscale()%></td>
                                    <td class="text-center"><%
                                        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        String dataStringDDMMYYYY = u.getDataNascita().format(pattern);
                                        %><%=dataStringDDMMYYYY%>
                                    </td>
                                    <c:if test="${sessionScope.superuserSessione == true}">
                                        <td class="text-center">
                                            <%
                                                //ATTENZIONE, qui uso del codice per accedere al db dalla jsp, di solito lo facevo nella servlet ma non ho altre idee, va bene?
                                                if(MulteService.getMulteService().verificaUtenteMorosoByCF(u.getCodiceFiscale())){
                                                    %>Sì<%
                                                }else{
                                                    %>No<%
                                                }
                                            %>
                                        </td>
                                        <td class="text-center"><%
                                            if(u.isSuperuser()){
                                                %>Sì<%
                                            }else{
                                                %>No<%
                                            }
                                            %>
                                        </td>
                                        <!-- Pulsanti relativi ad ogni utente, che può vedere solo l'admin -->
                                        <td class="text-center">
                                            <form action="./creaModificaUtente" method="get"> <!-- Riutilizzo stessa pagina della creazione/modifica -->
                                                <input type="hidden" name="codiceFiscaleUtenteDaModificare" value="<%=u.getCodiceFiscale()%>"/>
                                                <button type="submit" class="btn btn-outline-primary">Modifica</button>
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#eliminaUtenteModal<%=contatore%>">Elimina</button>
<%--                                            <form action="./homePageAdmin" method="get" id="formEliminaUtenti" > <!-- non è richiesta una pagina di eliminazione ma solo conferma (magari con get?, non so) -->--%>
<%--                                                <input type="hidden" name="codiceFiscaleUtenteDaEliminare" value="<%=u.getCodiceFiscale()%>"/>--%>
<%--                                                <input type="hidden" name="utenteDaEliminare" value="true">--%>
<%--                                                <button type="submit" class="btn btn-danger" data-toggle="modal">Elimina</button>--%>
<%--&lt;%&ndash;                                                data-target="#basicExampleModal" name="elimina_utente"&ndash;%&gt;--%>
<%--                                            </form>--%>
                                        </td>

                                        <%--        Inserisco qui la modale di bootstrap per eliminare l'utente--%>
                                        <div class="modal fade" tabindex="-1" id="eliminaUtenteModal<%=contatore%>"
                                             data-keyboard="false" data-backdrop="static">
                                            <div class="modal-dialog modal-lg">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h4 class="modal-title text-danger">Eliminazione utente</h4>
                                                        <button type="button" class="close" data-dismiss="modal">
                                                            &times;
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <p>Sei sicuro di voler eliminare l'utente <%=u.getCodiceFiscale()%> da te selezionato? Verranno eliminate anche tutte le prenotazioni e le multe ad esso associate</p>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <form action="./homePageAdmin" method="get" id="formEliminaUtenti" > <!-- non è richiesta una pagina di eliminazione ma solo conferma (magari con get?, non so) -->
                                                            <input type="hidden" name="codiceFiscaleUtenteDaEliminare" value="<%=u.getCodiceFiscale()%>"/>
                                                            <input type="hidden" name="utenteDaEliminare" value="true">
                                                            <button type="submit" class="btn btn-danger" data-toggle="modal">Elimina</button>
                                                        </form>
<%--                                                        <button type="submit" class="btn btn-danger">Elimina</button>--%>
                                                        <button type="button" class="btn btn-secondary"
                                                                data-dismiss="modal">Annulla</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <td class="text-center">
                                            <form action="./multeUtente" method="get"> <!-- Da creare la pagina multe -->
                                                <input type="hidden" name="codiceFiscaleUtenteConMulte" value="<%=u.getCodiceFiscale()%>"/>
                                                <% if(MulteService.getMulteService().verificaUtenteMorosoByCF(u.getCodiceFiscale())){ %>
                                                    <button type="submit" class="btn btn-outline-warning">Multe</button>
                                                <% }else{ %>
                                                    <button type="submit" class="btn btn-outline-primary">Multe</button>
                                                <% } %>
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <form action="./prenotazioniUtente" method="get"> <!-- Da creare la pagina prenotazioni -->
                                                <input type="hidden" name="codiceFiscaleUtentePerPrenotazioni" value="<%=u.getCodiceFiscale()%>"/>
                                                <% if(MulteService.getMulteService().verificaUtenteMorosoByCF(u.getCodiceFiscale())){ %>
                                                    <button type="submit" class="btn btn-outline-warning">Prenotazioni</button>
                                                <% }else{ %>
                                                    <button type="submit" class="btn btn-outline-primary">Prenotazioni</button>
                                                <% } %>
                                            </form>
                                        </td>
                                    </c:if>
                                </tr>
                                <%
                                contatore ++;
                            }
                        }else{
                            %>
                                <tr>
                                        <td>Non esistono utenti registrati</td>
                                </tr>
                            <%
                        }
                    %>
                </tbody>
            </table>


<%--            <!-- Modal -->--%>
<%--            <div class="modal fade" id="basicExampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"--%>
<%--                 aria-hidden="true">--%>
<%--                <div class="modal-dialog" role="document">--%>
<%--                    <div class="modal-content">--%>
<%--                        <div class="modal-header">--%>
<%--                            <h5 class="modal-title" id="exampleModalLabel">Sicuro di voler cancellare?</h5>--%>
<%--                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">--%>
<%--                                <span aria-hidden="true">&times;</span>--%>
<%--                            </button>--%>
<%--                        </div>--%>
<%--                        <div class="modal-body">--%>
<%--                            Attenzione! In questo modo non solo cancellerai l'utente selezionato ma anche tutte le prenotazioni e multe a cui è associato.--%>
<%--                            Sicuro di voler procedere?--%>
<%--                        </div>--%>
<%--                        <div class="modal-footer">--%>
<%--                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>--%>
<%--                            <button type="button" class="btn btn-danger">Elimina</button>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>

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
                // if (document.getElementById('r4').checked) {
                //     valore_colonna = document.getElementById('r4').value;
                // }
                switch (valore_colonna) {
                    case "nome": numero_colonna=0;
                                    break;
                    case "cognome": numero_colonna=1;
                                    break;
                    case "codiceFiscale": numero_colonna=2;
                                    break;
                    // case "amministratore": numero_colonna=3;
                }

                // Declare variables
                var input, filter, table, tr, td, i, txtValue;
                input = document.getElementById("ricercaSuTabella");
                filter = input.value.toUpperCase();
                table = document.getElementById("tabellaUtenti");
                tr = table.getElementsByTagName("tr");

                // Loop through all table rows, and hide those who don't match the search query
                for (i = 0; i < tr.length; i++) {
                    //for(j=0; j < 4; j++) { //4 perché solo le prime 4 colonne mi servono NON VA, ma se potessi scegliere quale colonna potrei mettere il numero fisso
                        td = tr[i].getElementsByTagName("td")[numero_colonna];
                        if (td) {
                            txtValue = td.textContent || td.innerText;
                            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                                tr[i].style.display = "";
                            } else {
                                tr[i].style.display = "none";
                            }
                        }
                    //}
                }
            }
        </script>

<%--        https://stackoverflow.com/questions/22636819/confirm-deletion-using-bootstrap-3-modal-box--%>
<%--        <script>--%>
<%--            $('button[name="elimina_utente"]').on('click', function(e) {--%>
<%--                var $form = $(this).getElementById("formEliminaUtenti");--%>
<%--                e.preventDefault();--%>
<%--                $('#confirm').modal({--%>
<%--                    backdrop: 'static',--%>
<%--                    keyboard: false--%>
<%--                })--%>
<%--                    .on('click', '#delete', function(e) {--%>
<%--                        $form.trigger('submit');--%>
<%--                    });--%>
<%--            });--%>
<%--        </script>--%>
        <!-- JS -->
        <script src="//code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- JS -->
    </body>
</html>

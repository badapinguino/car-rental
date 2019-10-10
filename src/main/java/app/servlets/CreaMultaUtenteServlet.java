package app.servlets;

import app.DTO.PrenotazioneDTO;
import app.DTO.UtenteDTO;
import app.exceptions.prenotazione.DataNonCompresaNellaPrenotazioneException;
import app.exceptions.prenotazione.DescrizioneTroppoLungaException;
import app.services.MulteService;
import app.services.PrenotazioniService;
import app.services.UtentiService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CreaMultaUtenteServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codiceFiscaleUtenteDaMultare = req.getParameter("codiceFiscaleUtenteDaMultare");
        req.setAttribute("codiceFiscaleUtenteDaMultare", codiceFiscaleUtenteDaMultare);

        //controllo e nel caso reinserisco nella request, se ci sono degli errori o degli attributi dal post
        if(req.getAttribute("multaInserita")!=null){
            req.setAttribute("multaInserita", req.getAttribute("multaInserita"));
        }
        if(req.getAttribute("erroreDescrizioneLunga")!=null){
            req.setAttribute("erroreDescrizioneLunga", req.getAttribute("erroreDescrizioneLunga"));
        }
        if(req.getAttribute("erroreDataNonCompresa")!=null){
            req.setAttribute("erroreDataNonCompresa", req.getAttribute("erroreDataNonCompresa"));
        }

        HttpSession session = req.getSession();
        //controllo se sta cercando di modificare un superuser oppure se è l'utente che sta modificando i suoi dati
        if(codiceFiscaleUtenteDaMultare!=null){
            if(UtentiService.getUtentiService().controllaSeSuperuserByCF( (String) session.getAttribute("codiceFiscaleSessione")) ){
                UtenteDTO uDTO = UtentiService.getUtentiService().selezionaUtenteByCFNoPassword(codiceFiscaleUtenteDaMultare);
                req.setAttribute("utenteDTODaMultare", uDTO);
                //cerco le prenotazioni dell'utente e le passo alla pagina delle multe
                List<PrenotazioneDTO> prenotazioniDTO = PrenotazioniService.getPrenotazioniService().selezionaPrenotazioniByCF(codiceFiscaleUtenteDaMultare);
                req.setAttribute("prenotazioniDTOUtenteDaMultare", prenotazioniDTO);
            }
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/creaMultaUtente.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String prenotazioneDaInserire = req.getParameter("prenotazione");
        int numeroPrenotazioneDaInserire = Integer.parseInt(prenotazioneDaInserire);
        String descrizioneDaInserire = req.getParameter("descrizione");
        try {
            if(req.getParameter("dataProblema")!=null && !req.getParameter("dataProblema").equals("")){
                String dataProblemaString = req.getParameter("dataProblema");
                //LocalDate.parse(dataProblemaString);
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dataProblemaString);
                LocalDate dataProblemaDaInserire = date.toInstant().atZone(ZoneId.of("Europe/Rome")).toLocalDate();
                MulteService.getMulteService().salvaMultaConNumeroPrenotazione(descrizioneDaInserire, numeroPrenotazioneDaInserire, dataProblemaDaInserire);
                req.setAttribute("multaInserita", true);
            }else{
                MulteService.getMulteService().salvaMultaConNumeroPrenotazione(descrizioneDaInserire, numeroPrenotazioneDaInserire);
                req.setAttribute("multaInserita", true);
            }
        }catch(DescrizioneTroppoLungaException dtle){
            req.setAttribute("multaInserita", false);
            req.setAttribute("erroreDescrizioneLunga", true);
        }catch(DataNonCompresaNellaPrenotazioneException dncnpe){
            req.setAttribute("multaInserita", false);
            req.setAttribute("erroreDataNonCompresa", true);
        }catch(Exception e){
            req.setAttribute("multaInserita", false);
            System.out.println(e);
            e.printStackTrace();
        }
        String codiceFiscaleUtenteDaMultare = req.getParameter("codiceFiscaleUtenteDaMultare");
        req.setAttribute("codiceFiscaleUtenteDaMultare", codiceFiscaleUtenteDaMultare);
        doGet(req, resp);
    }
}

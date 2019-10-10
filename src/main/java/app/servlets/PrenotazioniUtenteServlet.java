package app.servlets;

import app.DAO.DAOImplements.PrenotazioniDAO;
import app.DTO.PrenotazioneDTO;
import app.DTO.UtenteDTO;
import app.services.PrenotazioniService;
import app.services.UtentiService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class PrenotazioniUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //prendo il CF e lo inserisco di nuovo perché serve nei form per le post di approvazione ed eliminazione
        String codiceFiscaleUtentePerPrenotazioni = req.getParameter("codiceFiscaleUtentePerPrenotazioni");
        req.setAttribute("codiceFiscaleUtentePerPrenotazioni", codiceFiscaleUtentePerPrenotazioni);
        HttpSession session = req.getSession();
        //controllo se sta cercando di modificare un superuser oppure se è l'utente che sta visualizzando le sue prenotazioni
        if(codiceFiscaleUtentePerPrenotazioni!=null){
            if(UtentiService.getUtentiService().controllaSeSuperuserByCF( (String) session.getAttribute("codiceFiscaleSessione")) || (codiceFiscaleUtentePerPrenotazioni.equals((String) session.getAttribute("codiceFiscaleSessione")))){
                //UtenteDTO uDTO = UtentiService.getUtentiService().selezionaUtenteByCFNoPassword(codiceFiscaleUtentePerPrenotazioni);
                //req.setAttribute("datiUtenteDaModificare", uDTO);
                //richiesta dati prenotazioni
                List<PrenotazioneDTO> prenotazioniDTO = PrenotazioniService.getPrenotazioniService().selezionaPrenotazioniByCF(codiceFiscaleUtentePerPrenotazioni);
                req.setAttribute("prenotazioniDTOUtente", prenotazioniDTO);
                // cerca l'utente e lo passa alla JSP per poter scrivere nome e cognome utente in cima alla tabella
                UtenteDTO utenteDTO = UtentiService.getUtentiService().selezionaUtenteByCFNoPassword(req.getParameter("codiceFiscaleUtentePerPrenotazioni"));
                req.setAttribute("utenteDTOPrenotazioni", utenteDTO);
            }
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/prenotazioniUtente.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("numeroPrenotazioneDaApprovare")!=null){
            //approva la prenotazione e visualizza un messaggio nella jsp
            PrenotazioniDAO.getPrenotazioniDAO().approvaLaPrenotazione(Integer.parseInt(req.getParameter("numeroPrenotazioneDaApprovare")));
            //aggiungere un messaggio da visualizzare nella JSP
            req.setAttribute("prenotazioneApprovata", true);
        }
        if(req.getParameter("numeroPrenotazioneDaEliminare")!=null){
            //elimina la prenotazione e visualizza un messaggio nella jsp
            PrenotazioniDAO.getPrenotazioniDAO().eliminaPrenotazioneByNumero(Integer.parseInt(req.getParameter("numeroPrenotazioneDaEliminare")));
            //aggiungere un messaggio da visualizzare nella JSP
            req.setAttribute("prenotazioneEliminata", true);
        }
        //se non prendo e riaggiungo l'attributo CF mi perde l'utente, quindi nella pagina stampa prenotazioni per l'utente null null
        String codiceFiscaleUtentePerPrenotazioni = req.getParameter("codiceFiscaleUtentePerPrenotazioni");
        req.setAttribute("codiceFiscaleUtentePerPrenotazioni", codiceFiscaleUtentePerPrenotazioni);
        doGet(req,resp);
    }
}

package app.servlets;

import app.DTO.MultaDTO;
import app.DTO.UtenteDTO;
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
import java.util.List;

public class MulteUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codiceFiscaleUtenteConMulte = req.getParameter("codiceFiscaleUtenteConMulte");
        HttpSession session = req.getSession();
        //controllo se sta cercando di modificare un superuser oppure se è l'utente che sta visualizzando le sue prenotazioni
        if(codiceFiscaleUtenteConMulte!=null){
            if(UtentiService.getUtentiService().controllaSeSuperuserByCF( (String) session.getAttribute("codiceFiscaleSessione")) || (codiceFiscaleUtenteConMulte.equals((String) session.getAttribute("codiceFiscaleSessione")))){
                //richiesta dati multe
                List<MultaDTO> multeDTO = MulteService.getMulteService().selezionaMulteByCF(codiceFiscaleUtenteConMulte);
                req.setAttribute("multeDTOUtente", multeDTO);
                // cerca l'utente e lo passa alla JSP per poter scrivere nome e cognome utente in cima alla tabella
                UtenteDTO utenteDTO = UtentiService.getUtentiService().selezionaUtenteByCFNoPassword(req.getParameter("codiceFiscaleUtenteConMulte"));
                req.setAttribute("utenteDTOMulte", utenteDTO);
            }
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/multeUtente.jsp");
        requestDispatcher.forward(req, resp);
    }
}

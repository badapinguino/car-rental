package app.servlets;

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

public class HomePageAdminServlet extends HttpServlet {
    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        User user = new User(name, password);
        Model model = Model.getInstance();
        model.add(user);

        req.setAttribute("userName", name);
        doGet(req, resp);
    }*/

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UtenteDTO utenteLoggato;
        String codiceFiscale = (String) session.getAttribute("codiceFiscaleSessione");
        if(codiceFiscale==null) {
            String password = req.getParameter("password");
            //req.setAttribute("pass", password);
            codiceFiscale = req.getParameter("codiceFiscaleLogin");
            //req.setAttribute("codiceFiscale", codiceFiscale);

            //controllo che esista l'utente con CF e pwd passati
            utenteLoggato = UtentiService.getUtentiService().loginUtente(codiceFiscale, password);
        }else{
            utenteLoggato = UtentiService.getUtentiService().selezionaUtenteByCFNoPassword(codiceFiscale);
        }
        if(utenteLoggato==null || utenteLoggato.getCognome()==null){
            req.setAttribute("messaggioErrore", "Errore, username o password sbagliati");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(req, resp);
        }else{
            session.setAttribute("codiceFiscaleSessione", codiceFiscale); //setto dopo il login, nella sessione il codice fiscale dell'utente, lo eliminerò solo al logout
            String nome = utenteLoggato.getNome();
            String cognome = utenteLoggato.getCognome();
            boolean superuser = utenteLoggato.isSuperuser();
            req.setAttribute("nome", nome);
            req.setAttribute("cognome", cognome);
            session.setAttribute("superuserSessione", superuser); //setto dopo il login, nella sessione se l'utente è superuser, lo eliminerò solo al logout
            //if colonna selezionata esiste allora impostalo come attributo
//            if(req.getParameter("colonnaSuCuiCercare") == null){
//                req.setAttribute("colonnaSuCuiCercare", "cognome");
//            }else{
//                req.setAttribute("colonnaSuCuiCercare", req.getParameter("colonnaSuCuiCercare"));
//            }
            RequestDispatcher requestDispatcher;
            if(superuser){
                List<UtenteDTO> lista = UtentiService.getUtentiService().selezionaTuttiUtentiNoPassword();
                req.setAttribute("listaUtenti", lista);
                requestDispatcher = req.getRequestDispatcher("views/homePageAdmin.jsp");
                //requestDispatcher.forward(req, resp);
            }else{
                req.setAttribute("codiceFiscaleUtentePerPrenotazioni", codiceFiscale);
                List<PrenotazioneDTO> prenotazioniDTO = PrenotazioniService.getPrenotazioniService().selezionaPrenotazioniByCF(codiceFiscale);
                req.setAttribute("prenotazioniDTOUtente", prenotazioniDTO);
                UtenteDTO utenteDTO = UtentiService.getUtentiService().selezionaUtenteByCFNoPassword(codiceFiscale);
                req.setAttribute("utenteDTOPrenotazioni", utenteDTO);
                requestDispatcher = req.getRequestDispatcher("views/prenotazioniUtente.jsp");
                //requestDispatcher = req.getRequestDispatcher("views/homePageCustomer.jsp");
                //resp.sendRedirect("views/prenotazioniUtente.jsp");
                //resp.sendRedirect(req.getContextPath() + "/prenotazioniUtente");
//                requestDispatcher.include(req, resp);
            }
            requestDispatcher.forward(req, resp);
        }
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String utenteDaEliminare = req.getParameter("utenteDaEliminare");
        if(utenteDaEliminare!=null && utenteDaEliminare.equals("true")){
            String codiceFiscaleUtenteDaEliminare = req.getParameter("codiceFiscaleUtenteDaEliminare");
            UtenteDTO utenteDTOEliminato = UtentiService.getUtentiService().eliminaUtenteByCF(codiceFiscaleUtenteDaEliminare);
            req.setAttribute("utenteDTOEliminato", utenteDTOEliminato);
        }
        doPost(req,resp);
    }
}

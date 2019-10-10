package app.servlets;

import app.DTO.UtenteDTO;
import app.exceptions.utente.*;
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

public class CreaModificaUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codiceFiscaleUtenteDaModificare = req.getParameter("codiceFiscaleUtenteDaModificare");
        HttpSession session = req.getSession();
        //controllo se sta cercando di modificare un superuser oppure se è l'utente che sta modificando i suoi dati
        if(codiceFiscaleUtenteDaModificare!=null){
            if(UtentiService.getUtentiService().controllaSeSuperuserByCF( (String) session.getAttribute("codiceFiscaleSessione")) || (codiceFiscaleUtenteDaModificare.equals((String) session.getAttribute("codiceFiscaleSessione")))){
                UtenteDTO uDTO = UtentiService.getUtentiService().selezionaUtenteByCFNoPassword(codiceFiscaleUtenteDaModificare);
                req.setAttribute("datiUtenteDaModificare", uDTO);
            }
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/creaModificaUtente.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String nomeDaInserire= req.getParameter("nome");
        String cognomeDaInserire= req.getParameter("cognome");
        String passwordDaInserire= req.getParameter("password");
        String codiceFiscaleDaInserire= req.getParameter("codiceFiscale");
        try {
            if(UtentiService.getUtentiService().controllaSeSuperuserByCF( (String) session.getAttribute("codiceFiscaleSessione")) || (codiceFiscaleDaInserire.equals((String) session.getAttribute("codiceFiscaleSessione")))) {
                String dataNascitaString = req.getParameter("dataNascita");
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dataNascitaString);
                LocalDate dataNascitaDaInserire = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                UtenteDTO utenteCheInseriremo = UtentiService.getUtentiService().selezionaUtenteByCFNoPassword(codiceFiscaleDaInserire);
                //System.out.println(utenteCheInseriremo.getCodiceFiscale());
                if (utenteCheInseriremo.getCodiceFiscale() != null && UtentiService.getUtentiService().controllaSeSuperuserByCF(codiceFiscaleDaInserire)) {
                    UtentiService.getUtentiService().salvaUtente(codiceFiscaleDaInserire, cognomeDaInserire, nomeDaInserire, dataNascitaDaInserire, passwordDaInserire, true);
                } else {
                    UtentiService.getUtentiService().salvaUtente(codiceFiscaleDaInserire, cognomeDaInserire, nomeDaInserire, dataNascitaDaInserire, passwordDaInserire);
                }
                req.setAttribute("utenteInserito", true);
            }else{
                throw new Exception();
            }
        }catch(CodiceFiscaleTroppoLungoException cftle){
            req.setAttribute("utenteInserito", false);
            req.setAttribute("erroreCFLungo", true);
        }catch(CodiceFiscaleTroppoCortoException cftce){
            req.setAttribute("utenteInserito", false);
            req.setAttribute("erroreCFCorto", true);
        }catch(CognomeTroppoLungoException ctle){
            req.setAttribute("utenteInserito", false);
            req.setAttribute("erroreCognomeLungo", true);
        }catch(NomeTroppoLungoException ntle){
            req.setAttribute("utenteInserito", false);
            req.setAttribute("erroreNomeLungo", true);
        }catch(PasswordTroppoLungaException ptle){
            req.setAttribute("utenteInserito", false);
            req.setAttribute("errorePasswordLunga", true);
        }catch(DataDiNascitaFuturaException dnfe){
            req.setAttribute("utenteInserito", false);
            req.setAttribute("erroreDataFutura", true);
        }catch(Exception e){
            req.setAttribute("utenteInserito", false);
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/creaModificaUtente.jsp");
        requestDispatcher.forward(req, resp);
    }
}

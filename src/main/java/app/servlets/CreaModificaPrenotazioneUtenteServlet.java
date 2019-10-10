package app.servlets;

import app.DAO.DAOImplements.UtentiDAO;
import app.DTO.PrenotazioneDTO;
import app.DTO.VeicoloDTO;
import app.exceptions.prenotazione.CodiceScontoInesistenteException;
import app.exceptions.prenotazione.DataInizioPiuGrandeDataFineException;
import app.exceptions.prenotazione.DataNonFuturaException;
import app.exceptions.prenotazione.VeicoloNonDisponibileDateSelezionateException;
import app.model.entities.Utente;
import app.services.BuoniScontoService;
import app.services.PrenotazioniService;
import app.services.UtentiService;
import app.services.VeicoliService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class CreaModificaPrenotazioneUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VeicoloDTO> veicoliDTO = VeicoliService.getVeicoliService().selezionaTuttiVeicoli();
        req.setAttribute("listaVeicoliDTO", veicoliDTO);
        String codiceFiscaleUtentePrenotazione = req.getParameter("codiceFiscaleUtentePrenotazione");
        req.setAttribute("codiceFiscaleUtentePrenotazione", codiceFiscaleUtentePrenotazione);
        String numeroPrenotazioneDaModificareString = req.getParameter("numeroPrenotazioneDaModificare");
        if(numeroPrenotazioneDaModificareString!=null && numeroPrenotazioneDaModificareString!=""){
            req.setAttribute("numeroPrenotazioneDaModificare", numeroPrenotazioneDaModificareString);
            PrenotazioneDTO prenotazioneDTO = PrenotazioniService.getPrenotazioniService().selezionaPrenotazioneByNumero(Integer.parseInt(numeroPrenotazioneDaModificareString));
            if(prenotazioneDTO.getBuonoSconto()!=null) {
                req.setAttribute("codiceScontoDaModificare", prenotazioneDTO.getBuonoSconto().getCodiceSconto());
            }
            req.setAttribute("dataInizioDaModificare", prenotazioneDTO.getDataInizio());
            req.setAttribute("dataFineDaModificare", prenotazioneDTO.getDataFine());
            req.setAttribute("codiceVeicoloDaModificare", prenotazioneDTO.getVeicolo().getCodiceMezzo());
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/creaModificaPrenotazioneUtente.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codiceFiscaleUtentePrenotazione = req.getParameter("codiceFiscaleUtentePrenotazione");
        req.setAttribute("codiceFiscaleUtentePrenotazione", codiceFiscaleUtentePrenotazione);
        String codiceMezzoPrenotazione = req.getParameter("veicolo");
        String codiceScontoPrenotazione = req.getParameter("codiceSconto");
        String dataInizioPrenotazioneString = req.getParameter("dataInizioPrenotazione");
        String dataFinePrenotazioneString = req.getParameter("dataFinePrenotazione");
        Date date = null;
        Date dateEnd = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dataInizioPrenotazioneString);
            LocalDate dataInizioPrenotazione = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(dataFinePrenotazioneString);
            LocalDate dataFinePrenotazione = dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Utente utente = UtentiDAO.getUtentiDAO().selezionaUtenteByCF(codiceFiscaleUtentePrenotazione);
            String numeroPrenotazioneString = req.getParameter("numeroPrenotazioneDaModificare");
            int numeroPrenotazione = -1;
            if(numeroPrenotazioneString!=null){
                numeroPrenotazione = Integer.parseInt(numeroPrenotazioneString);
            }
            if(codiceScontoPrenotazione!=null && codiceScontoPrenotazione!=""){
                PrenotazioniService.getPrenotazioniService().salvaPrenotazione(dataInizioPrenotazione, dataFinePrenotazione, utente, codiceMezzoPrenotazione, numeroPrenotazione, codiceScontoPrenotazione);
            }else{
                PrenotazioniService.getPrenotazioniService().salvaPrenotazione(dataInizioPrenotazione, dataFinePrenotazione, utente, codiceMezzoPrenotazione, numeroPrenotazione);
            }
            //calcolo costo totale prenotazione e valore buoni sconto
            float costoTotalePrenotazione = VeicoliService.getVeicoliService().selezionaVeicoloByCodice(codiceMezzoPrenotazione).getPrezzoGiornata() * (DAYS.between(dataInizioPrenotazione, dataFinePrenotazione)+1);
            float costoFinalePrenotazione = costoTotalePrenotazione;
            if(codiceScontoPrenotazione!=null && codiceScontoPrenotazione!=""){
                float valoreCodiceSconto = BuoniScontoService.getBuoniScontoService().selezionaBuonoScontoByCodice(codiceScontoPrenotazione).getValore();
                boolean percentualeCodiceSconto = BuoniScontoService.getBuoniScontoService().selezionaBuonoScontoByCodice(codiceScontoPrenotazione).isPercentuale();
                if(percentualeCodiceSconto){
                    costoFinalePrenotazione = costoTotalePrenotazione - ( costoTotalePrenotazione * (valoreCodiceSconto/100) );
                }else{
                    costoFinalePrenotazione = costoTotalePrenotazione - valoreCodiceSconto;
                }
                req.setAttribute("valoreCodiceSconto", valoreCodiceSconto);
                req.setAttribute("percentualeCodiceSconto", percentualeCodiceSconto);
            }
            req.setAttribute("costoTotalePreScontoPrenotazione", costoTotalePrenotazione);
            req.setAttribute("costoFinalePrenotazione", costoFinalePrenotazione);

            req.setAttribute("prenotazioneInserita", true);
        } catch (VeicoloNonDisponibileDateSelezionateException vnddse) {
            req.setAttribute("prenotazioneInserita", false);
            req.setAttribute("erroreVeicoloNonDisponibile", true);
        } catch (DataInizioPiuGrandeDataFineException dipgdfe) {
            req.setAttribute("prenotazioneInserita", false);
            req.setAttribute("erroreDataInizioMaggioreDataFine", true);
        } catch (DataNonFuturaException dnfe) {
            req.setAttribute("prenotazioneInserita", false);
            req.setAttribute("erroreDataNonFutura", true);
        } catch (CodiceScontoInesistenteException csie) {
            req.setAttribute("prenotazioneInserita", false);
            req.setAttribute("erroreCodiceScontoInesistente", true);
        } catch (Exception e){
            req.setAttribute("prenotazioneInserita", false);
            e.printStackTrace();
        }

//        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/creaModificaPrenotazioneUtente.jsp");
//        requestDispatcher.forward(req, resp);
        doGet(req,resp);
    }
}

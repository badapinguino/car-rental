package app.services;

import app.DAO.DAOImplements.BuoniScontoDAO;
import app.DAO.DAOImplements.PrenotazioniDAO;
import app.DAO.DAOImplements.VeicoliDAO;
import app.DTO.PrenotazioneDTO;
import app.exceptions.prenotazione.CodiceScontoInesistenteException;
import app.exceptions.prenotazione.DataInizioPiuGrandeDataFineException;
import app.exceptions.prenotazione.DataNonFuturaException;
import app.exceptions.prenotazione.VeicoloNonDisponibileDateSelezionateException;
import app.model.entities.BuonoSconto;
import app.model.entities.Prenotazione;
import app.model.entities.Utente;
import app.model.entities.Veicolo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioniService {
    public List<PrenotazioneDTO> selezionaPrenotazioniByCF(String codiceFiscale){
        List<Prenotazione> prenotazioni = PrenotazioniDAO.getPrenotazioniDAO().selezionaPrenotazioniByCF(codiceFiscale);
        List<PrenotazioneDTO> prenotazioniDTO = new ArrayList<>();
        for(Prenotazione prenotazione : prenotazioni){
            try {
                PrenotazioneDTO pDTO = new PrenotazioneDTO();
                pDTO.setNumero(prenotazione.getNumero());
                pDTO.setDataInizio(prenotazione.getDataInizio());
                pDTO.setDataFine(prenotazione.getDataFine());
                pDTO.setApprovata(prenotazione.isApprovata());
                pDTO.setUtente(prenotazione.getUtente());
                pDTO.setVeicolo(prenotazione.getVeicolo());
                if(prenotazione.getMulte()!=null){
                    pDTO.setMulte(prenotazione.getMulte());
                }
                if(prenotazione.getBuonoSconto()!=null){
                    pDTO.setBuonoSconto(prenotazione.getBuonoSconto());
                }
                prenotazioniDTO.add(pDTO);
            }catch(NullPointerException npe){
                //non fare nulla perché voglio mi ritorni null
            }
        }
        return prenotazioniDTO;
    }

    public PrenotazioneDTO selezionaPrenotazioneByNumero(int numeroPrenotazione){
        Prenotazione prenotazione = PrenotazioniDAO.getPrenotazioniDAO().selezionaPrenotazioneByNumero(numeroPrenotazione);
        PrenotazioneDTO pDTO = null;
        try {
            pDTO = new PrenotazioneDTO();
            pDTO.setNumero(prenotazione.getNumero());
            pDTO.setDataInizio(prenotazione.getDataInizio());
            pDTO.setDataFine(prenotazione.getDataFine());
            pDTO.setApprovata(prenotazione.isApprovata());
            pDTO.setUtente(prenotazione.getUtente());
            pDTO.setVeicolo(prenotazione.getVeicolo());
            if(prenotazione.getMulte()!=null){
                pDTO.setMulte(prenotazione.getMulte());
            }
            if(prenotazione.getBuonoSconto()!=null){
                pDTO.setBuonoSconto(prenotazione.getBuonoSconto());
            }
        }catch(NullPointerException npe){
            //non fare nulla perché voglio mi ritorni null
        }
        return pDTO;
    }

    public LocalDate selezionaDataInizioByNumero(int numeroPrenotazione){
        Prenotazione prenotazione = PrenotazioniDAO.getPrenotazioniDAO().selezionaPrenotazioneByNumero(numeroPrenotazione);
        LocalDate dataInizio = null;
        try {
            dataInizio = prenotazione.getDataInizio();
        }catch(NullPointerException npe){
            //non fare nulla perché voglio mi ritorni null
        }
        return dataInizio;
    }

    public LocalDate selezionaDataFineByNumero(int numeroPrenotazione){
        Prenotazione prenotazione = PrenotazioniDAO.getPrenotazioniDAO().selezionaPrenotazioneByNumero(numeroPrenotazione);
        LocalDate dataFine = null;
        try {
            dataFine = prenotazione.getDataFine();
        }catch(NullPointerException npe){
            //non fare nulla perché voglio mi ritorni null
        }
        return dataFine;
    }

    public void salvaPrenotazione(LocalDate dataInizio, LocalDate dataFine, Utente utente, String codiceMezzo, int numeroPrenotazione, String codiceSconto) throws DataNonFuturaException, DataInizioPiuGrandeDataFineException, CodiceScontoInesistenteException, VeicoloNonDisponibileDateSelezionateException {
        // controllo che le date non siano passate e che la data inizio sia antecedente alla data fine
        if(dataInizio.compareTo(LocalDate.now())<=0){
            throw new DataNonFuturaException();
        }
        if(dataFine.compareTo(LocalDate.now())<=0){
            throw new DataNonFuturaException();
        }
        if(dataInizio.compareTo(dataFine)>0){
            throw new DataInizioPiuGrandeDataFineException();
        }

        //potrebbe verificarsi una eccezione (null pointer) se non esiste veicolo con quel codice mezzo
        Veicolo veicolo = VeicoliDAO.getVeicoliDAO().selezionaVeicoloByCodice(codiceMezzo);
        boolean controlloDataInizio = PrenotazioniDAO.getPrenotazioniDAO().esistePrenotazioneDataVeicolo(dataInizio, veicolo, numeroPrenotazione);
        boolean controlloDataFine = PrenotazioniDAO.getPrenotazioniDAO().esistePrenotazioneDataVeicolo(dataFine, veicolo, numeroPrenotazione);
        List<Prenotazione> prenotazioniVeicolo = PrenotazioniDAO.getPrenotazioniDAO().selezionaPrenotazioniByCodiceMezzo(codiceMezzo);
        boolean controlloNelMezzo = false;
        for (Prenotazione p : prenotazioniVeicolo ) {
            if( p.getNumero()!=numeroPrenotazione && (((p.getDataInizio()).compareTo(dataInizio) >= 0 && p.getDataInizio().compareTo(dataFine) <= 0) || (p.getDataFine().compareTo(dataInizio) >= 0 && p.getDataFine().compareTo(dataFine) <= 0))){
                controlloNelMezzo = true;
            }
        }
        if( !controlloDataInizio && !controlloDataFine && !controlloNelMezzo){
            // se tutti i controlli sono falsi procedere con inserimento della prenotazione
            PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
            prenotazioneDTO.setDataInizio(dataInizio);
            prenotazioneDTO.setDataFine(dataFine);
            prenotazioneDTO.setVeicolo(veicolo);
            prenotazioneDTO.setUtente(utente);
            prenotazioneDTO.setNumero(numeroPrenotazione);
            if(MulteService.getMulteService().verificaUtenteMorosoByCF(utente.getCodiceFiscale())){
                prenotazioneDTO.setApprovata(false);
            }else{
                prenotazioneDTO.setApprovata(true);
            }
            BuonoSconto buonoSconto = BuoniScontoDAO.getBuoniScontoDAO().selezionaBuonoScontoByCodice(codiceSconto);
            if(buonoSconto!=null){
                prenotazioneDTO.setBuonoSconto(buonoSconto);
            }else{
                throw new CodiceScontoInesistenteException();
            }
            PrenotazioniDAO.getPrenotazioniDAO().salvaPrenotazione(prenotazioneDTO);
        }else{
            throw new VeicoloNonDisponibileDateSelezionateException();
        }
    }

    public void salvaPrenotazione(LocalDate dataInizio, LocalDate dataFine, Utente utente, String codiceMezzo, int numeroPrenotazione) throws DataNonFuturaException, DataInizioPiuGrandeDataFineException, VeicoloNonDisponibileDateSelezionateException {
        // controllo che le date non siano passate e che la data inizio sia antecedente alla data fine
        if(dataInizio.compareTo(LocalDate.now())<=0){
            throw new DataNonFuturaException();
        }
        if(dataFine.compareTo(LocalDate.now())<=0){
            throw new DataNonFuturaException();
        }
        if(dataInizio.compareTo(dataFine)>0){
            throw new DataInizioPiuGrandeDataFineException();
        }

        //potrebbe verificarsi una eccezione (null pointer) se non esiste veicolo con quel codice mezzo
        Veicolo veicolo = VeicoliDAO.getVeicoliDAO().selezionaVeicoloByCodice(codiceMezzo);
        boolean controlloDataInizio = PrenotazioniDAO.getPrenotazioniDAO().esistePrenotazioneDataVeicolo(dataInizio, veicolo, numeroPrenotazione);
        boolean controlloDataFine = PrenotazioniDAO.getPrenotazioniDAO().esistePrenotazioneDataVeicolo(dataFine, veicolo, numeroPrenotazione);
        List<Prenotazione> prenotazioniVeicolo = PrenotazioniDAO.getPrenotazioniDAO().selezionaPrenotazioniByCodiceMezzo(codiceMezzo);
        boolean controlloNelMezzo = false;
        for (Prenotazione p : prenotazioniVeicolo ) {
            if( p.getNumero()!=numeroPrenotazione && (((p.getDataInizio()).compareTo(dataInizio) >= 0 && p.getDataInizio().compareTo(dataFine) <= 0) || (p.getDataFine().compareTo(dataInizio) >= 0 && p.getDataFine().compareTo(dataFine) <= 0))){
                controlloNelMezzo = true;
            }
        }
        if( !controlloDataInizio && !controlloDataFine && !controlloNelMezzo){
            // se tutti i controlli sono falsi procedere con inserimento della prenotazione
            PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
            prenotazioneDTO.setDataInizio(dataInizio);
            prenotazioneDTO.setDataFine(dataFine);
            prenotazioneDTO.setVeicolo(veicolo);
            prenotazioneDTO.setUtente(utente);
            prenotazioneDTO.setNumero(numeroPrenotazione);
            if(MulteService.getMulteService().verificaUtenteMorosoByCF(utente.getCodiceFiscale())){
                prenotazioneDTO.setApprovata(false);
            }else{
                prenotazioneDTO.setApprovata(true);
            }
            PrenotazioniDAO.getPrenotazioniDAO().salvaPrenotazione(prenotazioneDTO);
        }else{
            throw new VeicoloNonDisponibileDateSelezionateException();
        }
    }

    private static PrenotazioniService istanza = null;
    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private PrenotazioniService() {}
    // Metodo della classe impiegato per accedere al singleton
    public static synchronized PrenotazioniService getPrenotazioniService() {
        if (istanza == null) {
            istanza = new PrenotazioniService();
        }
        return istanza;
    }
}

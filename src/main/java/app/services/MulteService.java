package app.services;

import app.DAO.DAOImplements.MulteDAO;
import app.DAO.DAOImplements.PrenotazioniDAO;
import app.DTO.MultaDTO;
import app.exceptions.prenotazione.DataNonCompresaNellaPrenotazioneException;
import app.exceptions.prenotazione.DescrizioneTroppoLungaException;
import app.model.entities.Multa;
import app.model.entities.Prenotazione;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MulteService {
    public List<MultaDTO> selezionaMulteByCF(String codiceFiscale){
        List<Multa> multe = MulteDAO.getMulteDAO().selezionaMulteByCF(codiceFiscale);
        List<MultaDTO> multeDTO = new ArrayList<>();
        for(Multa multa : multe) {
            try {
                MultaDTO mDTO = new MultaDTO();
                mDTO.setCodice(multa.getCodice());
                mDTO.setDescrizione(multa.getDescrizione());
                mDTO.setPrenotazione(multa.getPrenotazione());
                if (multa.getDataProblema() != null) {
                    mDTO.setDataProblema(multa.getDataProblema());
                }
                multeDTO.add(mDTO);
            } catch (NullPointerException npe) {
                //non fare nulla perché voglio mi ritorni null
            }
        }
        return multeDTO;
    }

    public boolean verificaUtenteMorosoByCF(String codiceFiscale){
        List<Multa> multe = MulteDAO.getMulteDAO().selezionaMulteByCF(codiceFiscale);
        boolean utenteMoroso = false;
        for(Multa multa : multe) {
            if(multa!=null || multa.getDescrizione()!=null){
                utenteMoroso=true;
            }
        }
        return utenteMoroso;
    }

    public void salvaMulta(String descrizione, Prenotazione prenotazione, LocalDate dataProblema) throws DataNonCompresaNellaPrenotazioneException, DescrizioneTroppoLungaException {
        MultaDTO multaDTO = new MultaDTO();
        multaDTO.setDescrizione(descrizione);
        multaDTO.setPrenotazione(prenotazione);
        if(dataProblema!=null){
            multaDTO.setDataProblema(dataProblema);
        }
        MulteDAO.getMulteDAO().salvaMulta(multaDTO);
    }
    public void salvaMulta(String descrizione, Prenotazione prenotazione) throws DataNonCompresaNellaPrenotazioneException, DescrizioneTroppoLungaException {
        MultaDTO multaDTO = new MultaDTO();
        multaDTO.setDescrizione(descrizione);
        multaDTO.setPrenotazione(prenotazione);
        MulteDAO.getMulteDAO().salvaMulta(multaDTO);
    }

    public void salvaMultaConNumeroPrenotazione(String descrizione, int numeroPrenotazione, LocalDate dataProblema) throws DataNonCompresaNellaPrenotazioneException, DescrizioneTroppoLungaException {
        Prenotazione prenotazione = PrenotazioniDAO.getPrenotazioniDAO().selezionaPrenotazioneByNumero(numeroPrenotazione);
        salvaMulta(descrizione, prenotazione, dataProblema);
    }
    public void salvaMultaConNumeroPrenotazione(String descrizione, int numeroPrenotazione) throws DataNonCompresaNellaPrenotazioneException, DescrizioneTroppoLungaException {
        Prenotazione prenotazione = PrenotazioniDAO.getPrenotazioniDAO().selezionaPrenotazioneByNumero(numeroPrenotazione);
        salvaMulta(descrizione, prenotazione);
    }

    private static MulteService istanza = null;
    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private MulteService() {}
    // Metodo della classe impiegato per accedere al singleton
    public static synchronized MulteService getMulteService() {
        if (istanza == null) {
            istanza = new MulteService();
        }
        return istanza;
    }
}

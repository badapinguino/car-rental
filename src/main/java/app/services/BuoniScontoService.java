package app.services;

import app.DAO.DAOImplements.BuoniScontoDAO;
import app.DTO.BuonoScontoDTO;
import app.exceptions.buonoSconto.CodiceScontoTroppoLungoException;
import app.exceptions.buonoSconto.ValoreNegativoOZeroException;
import app.exceptions.buonoSconto.ValorePercentualeOltreCentoException;
import app.model.entities.BuonoSconto;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class BuoniScontoService {
    public List<BuonoScontoDTO> selezionaTuttiBuoniSconto() {
        List<BuonoSconto> buoniSconto = BuoniScontoDAO.getBuoniScontoDAO().selezionaTuttiBuoniSconto();
        List<BuonoScontoDTO> buoniScontoDTO = new ArrayList<>();
        if(buoniSconto!=null){
            for (BuonoSconto buono : buoniSconto) {
                BuonoScontoDTO bDTO = new BuonoScontoDTO();
                bDTO.setCodiceSconto(buono.getCodiceSconto());
                bDTO.setValore(buono.getValore());
                bDTO.setPercentuale(buono.isPercentuale());
                bDTO.setPrenotazioni(buono.getPrenotazioni());
                buoniScontoDTO.add(bDTO);
            }
        }
        return buoniScontoDTO;
    }

    public BuonoScontoDTO eliminaBuonoScontoByCodice(String codiceSconto) throws PersistenceException {
        BuonoSconto buonoSconto = BuoniScontoDAO.getBuoniScontoDAO().selezionaBuonoScontoByCodice(codiceSconto);
        BuonoScontoDTO bsDTO = null;
        if(buonoSconto!=null) {
            bsDTO = new BuonoScontoDTO();
            bsDTO.setCodiceSconto(buonoSconto.getCodiceSconto());
            bsDTO.setPercentuale(buonoSconto.isPercentuale());
            bsDTO.setValore(buonoSconto.getValore());
            if(buonoSconto.getPrenotazioni()!=null)
                bsDTO.setPrenotazioni(buonoSconto.getPrenotazioni());
            BuoniScontoDAO.getBuoniScontoDAO().eliminaBuonoScontoByCodice(codiceSconto);
        }
        return bsDTO;
    }

    public BuonoScontoDTO selezionaBuonoScontoByCodice(String codiceSconto){
        BuonoSconto buonoSconto = BuoniScontoDAO.getBuoniScontoDAO().selezionaBuonoScontoByCodice(codiceSconto);
        BuonoScontoDTO bsDTO = null;
        try {
            bsDTO = new BuonoScontoDTO();
            bsDTO.setCodiceSconto(buonoSconto.getCodiceSconto());
            bsDTO.setPercentuale(buonoSconto.isPercentuale());
            bsDTO.setValore(buonoSconto.getValore());
            if(buonoSconto.getPrenotazioni()!=null){
                bsDTO.setPrenotazioni(buonoSconto.getPrenotazioni());
            }
        }catch(NullPointerException npe){
            //non fare nulla perché voglio mi ritorni null
        }
        return bsDTO;
    }

    public void salvaBuonoSconto(String codiceSconto, float valore, boolean percentuale) throws CodiceScontoTroppoLungoException, ValorePercentualeOltreCentoException, ValoreNegativoOZeroException {
        BuonoScontoDTO buonoScontoDTO = new BuonoScontoDTO();
        buonoScontoDTO.setCodiceSconto(codiceSconto);
        buonoScontoDTO.setPercentuale(percentuale);
        buonoScontoDTO.setValore(valore);
        BuoniScontoDAO.getBuoniScontoDAO().salvaBuonoSconto(buonoScontoDTO);
    }

    private static BuoniScontoService istanza = null;
    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private BuoniScontoService() {}
    // Metodo della classe impiegato per accedere al singleton
    public static synchronized BuoniScontoService getBuoniScontoService() {
        if (istanza == null) {
            istanza = new BuoniScontoService();
        }
        return istanza;
    }
}

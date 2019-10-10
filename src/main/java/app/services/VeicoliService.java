package app.services;

import app.DAO.DAOImplements.VeicoliDAO;
import app.DTO.VeicoloDTO;
import app.exceptions.veicolo.*;
import app.model.entities.Veicolo;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class VeicoliService {
    public VeicoloDTO selezionaVeicoloByCodice(String codiceMezzo){
        Veicolo veicolo = VeicoliDAO.getVeicoliDAO().selezionaVeicoloByCodice(codiceMezzo);
        VeicoloDTO vDTO = null;
        try {
            vDTO = new VeicoloDTO();
            vDTO.setCodiceMezzo(veicolo.getCodiceMezzo());
            vDTO.setTarga(veicolo.getTarga());
            vDTO.setModello(veicolo.getModello());
            vDTO.setCasaCostruttrice(veicolo.getCasaCostruttrice());
            vDTO.setAnno(veicolo.getAnno());
            vDTO.setTipologia(veicolo.getTipologia());
            vDTO.setPrezzoGiornata(veicolo.getPrezzoGiornata());
            if(veicolo.getPrenotazioni()!=null){
                vDTO.setPrenotazioni(veicolo.getPrenotazioni());
            }
        }catch(NullPointerException npe){
            //non fare nulla perché voglio mi ritorni null
        }
        return vDTO;
    }
    
    public List<VeicoloDTO> selezionaTuttiVeicoli(){
        List<Veicolo> veicoli = VeicoliDAO.getVeicoliDAO().selezionaTuttiVeicoli();
        List<VeicoloDTO> veicoliDTO = new ArrayList<VeicoloDTO>();
        for (Veicolo veicolo : veicoli) {
            VeicoloDTO vDTO = new VeicoloDTO();
            vDTO.setCodiceMezzo(veicolo.getCodiceMezzo());
            vDTO.setTarga(veicolo.getTarga());
            vDTO.setCasaCostruttrice(veicolo.getCasaCostruttrice());
            vDTO.setModello(veicolo.getModello());
            vDTO.setAnno(veicolo.getAnno());
            vDTO.setTipologia(veicolo.getTipologia());
            vDTO.setPrezzoGiornata(veicolo.getPrezzoGiornata());
            vDTO.setPrenotazioni(veicolo.getPrenotazioni());
            veicoliDTO.add(vDTO);
        }
        return veicoliDTO;
    }

    public VeicoloDTO eliminaVeicoloByCodice(String codiceMezzo) throws PersistenceException {
        Veicolo veicolo = VeicoliDAO.getVeicoliDAO().selezionaVeicoloByCodice(codiceMezzo);
        VeicoloDTO vDTO = null;
        if(veicolo!=null) {
            vDTO = new VeicoloDTO();
            vDTO.setCodiceMezzo(veicolo.getCodiceMezzo());
            vDTO.setTarga(veicolo.getTarga());
            vDTO.setCasaCostruttrice(veicolo.getCasaCostruttrice());
            vDTO.setModello(veicolo.getModello());
            vDTO.setCodiceMezzo(veicolo.getCodiceMezzo());
            VeicoliDAO.getVeicoliDAO().eliminaVeicoloByCodice(codiceMezzo);
        }
        return vDTO;
    }

    public void salvaVeicolo(String codiceMezzo, String targa, String casaCostruttrice, String modello, String tipologia, int anno, float prezzoGiornata) throws TipologiaTroppoLungaException, PrezzoGiornataTroppoBassoException, CodiceMezzoTroppoLungoException, AnnoTroppoPiccoloException, TargaTroppoLungaException, CasaCostruttriceTroppoLungaException, ModelloTroppoLungoException {
        VeicoloDTO veicoloDTO = new VeicoloDTO();
        veicoloDTO.setCodiceMezzo(codiceMezzo);
        veicoloDTO.setTarga(targa);
        veicoloDTO.setCasaCostruttrice(casaCostruttrice);
        veicoloDTO.setModello(modello);
        veicoloDTO.setTipologia(tipologia);
        veicoloDTO.setAnno(anno);
        veicoloDTO.setPrezzoGiornata(prezzoGiornata);
        //salva l'oggetto (entity) Utente attraverso il DAO relativo
        VeicoliDAO.getVeicoliDAO().salvaVeicolo(veicoloDTO);
    }

    private static VeicoliService istanza = null;
    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private VeicoliService() {}
    // Metodo della classe impiegato per accedere al singleton
    public static synchronized VeicoliService getVeicoliService() {
        if (istanza == null) {
            istanza = new VeicoliService();
        }
        return istanza;
    }
}

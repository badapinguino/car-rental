package app.services;

import app.DAO.DAOImplements.UtentiDAO;
import app.DTO.UtenteDTO;
import app.exceptions.utente.*;
import app.model.entities.Utente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//service relativi a Utenti, che comunicano con UtentiDAO
public class UtentiService {
    public void salvaUtente(String codiceFiscale, String cognome, String nome, LocalDate dataNascita, String password, boolean superuser) throws CodiceFiscaleTroppoLungoException, CodiceFiscaleTroppoCortoException, CognomeTroppoLungoException, NomeTroppoLungoException, DataDiNascitaFuturaException, PasswordTroppoLungaException {
        UtenteDTO utenteDTO = new UtenteDTO(codiceFiscale, cognome, nome, dataNascita, password, superuser);
        //salva l'oggetto (entity) Utente attraverso il DAO relativo
        UtentiDAO.getUtentiDAO().salvaUtente(utenteDTO);
    }

    public void salvaUtente(String codiceFiscale, String cognome, String nome, LocalDate dataNascita, String password) throws CodiceFiscaleTroppoLungoException, CodiceFiscaleTroppoCortoException, CognomeTroppoLungoException, NomeTroppoLungoException, DataDiNascitaFuturaException, PasswordTroppoLungaException {
        UtenteDTO utenteDTO = new UtenteDTO();
        utenteDTO.setCodiceFiscale(codiceFiscale);
        utenteDTO.setCognome(cognome);
        utenteDTO.setNome(nome);
        utenteDTO.setPassword(password);
        utenteDTO.setDataNascita(dataNascita);
        utenteDTO.setSuperuser(false);
        //salva l'oggetto (entity) Utente attraverso il DAO relativo
        UtentiDAO.getUtentiDAO().salvaUtente(utenteDTO);
    }

    public List<UtenteDTO> selezionaTuttiUtentiNoPassword(){
        List<Utente> utenti = UtentiDAO.getUtentiDAO().selezionaTuttiUtenti();
        List<UtenteDTO> utentiDTO = new ArrayList<UtenteDTO>();
        for (Utente utente : utenti) {
            UtenteDTO uDTO = new UtenteDTO();
            uDTO.setCodiceFiscale(utente.getCodiceFiscale());
            uDTO.setNome(utente.getNome());
            uDTO.setCognome(utente.getCognome());
            uDTO.setDataNascita(utente.getDataNascita());
            uDTO.setSuperuser(utente.isSuperuser());
            utentiDTO.add(uDTO);
        }
        return utentiDTO;
    }

    public UtenteDTO selezionaUtenteByCFNoPassword(String codiceFiscale){
        Utente utente = UtentiDAO.getUtentiDAO().selezionaUtenteByCF(codiceFiscale);
        UtenteDTO uDTO = null;
        try {
            uDTO = new UtenteDTO();
            uDTO.setNome(utente.getNome());
            uDTO.setCognome(utente.getCognome());
            uDTO.setDataNascita(utente.getDataNascita());
            uDTO.setSuperuser(utente.isSuperuser());
            uDTO.setCodiceFiscale(utente.getCodiceFiscale());
        }catch(NullPointerException npe){
            //non fare nulla perché voglio mi ritorni null
        }
        return uDTO;
    }
    public UtenteDTO loginUtente(String codiceFiscale, String password){
        Utente utente = UtentiDAO.getUtentiDAO().loginUtente(codiceFiscale, password);
        UtenteDTO uDTO = null;
        try {
            //se c'è un risultato lo assegno ad uDTO
            uDTO = new UtenteDTO();
            uDTO.setNome(utente.getNome());
            uDTO.setCognome(utente.getCognome());
            uDTO.setSuperuser(utente.isSuperuser());
        }catch (NullPointerException npe){
            //non faccio niente perché voglio che utente rimanga a null
        }
        return uDTO;
    }

    public UtenteDTO eliminaUtenteByCF(String codiceFiscale){
        Utente utente = UtentiDAO.getUtentiDAO().selezionaUtenteByCF(codiceFiscale);
        UtenteDTO uDTO = null;
        if(utente!=null) {
            uDTO = new UtenteDTO();
            uDTO.setCognome(utente.getCognome());
            uDTO.setNome(utente.getNome());
            uDTO.setCodiceFiscale(utente.getCodiceFiscale());
            UtentiDAO.getUtentiDAO().eliminaUtenteByCF(codiceFiscale);
        }
        return uDTO;
    }

    public boolean controllaSeSuperuserByCF(String codiceFiscale){
        Utente utente = UtentiDAO.getUtentiDAO().selezionaUtenteByCF(codiceFiscale);
        boolean superuser = utente.isSuperuser();
        return superuser;
    }

    private static UtentiService istanza = null;
    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private UtentiService() {}
    // Metodo della classe impiegato per accedere al singleton
    public static synchronized UtentiService getUtentiService() {
        if (istanza == null) {
            istanza = new UtentiService();
        }
        return istanza;
    }
}

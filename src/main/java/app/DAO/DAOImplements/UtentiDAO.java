package app.DAO.DAOImplements;

import app.DTO.UtenteDTO;
import app.exceptions.utente.*;
import app.model.entities.Utente;

import java.util.List;

import app.utils.HibernateUtil;
import app.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;

//serve per comunicare con il database
public class UtentiDAO{

    //metodo per selezionare tutti gli utenti
    public List<Utente> selezionaTuttiUtenti() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Utente> utenti = null;
        try{
            session.beginTransaction();
            utenti = (List<Utente>) session.createQuery(
                    "SELECT u FROM Utente u ORDER BY cognome ASC").list();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return utenti;
    }
    //metodo per salvare l'entity Utente
    public void salvaUtente(UtenteDTO utenteDTO) throws CodiceFiscaleTroppoCortoException, CodiceFiscaleTroppoLungoException, CognomeTroppoLungoException, NomeTroppoLungoException, DataDiNascitaFuturaException, PasswordTroppoLungaException {
        Utente utente = new Utente();
        utente.setCodiceFiscale(utenteDTO.getCodiceFiscale());
        utente.setCognome(utenteDTO.getCognome());
        utente.setNome(utenteDTO.getNome());
        utente.setDataNascita(utenteDTO.getDataNascita());
        utente.setSuperuser(utenteDTO.isSuperuser());
        utente.setPassword(utenteDTO.getPassword());
        SessionUtil.getSessionUtil().saveOrUpdateUtente(utente);
    }
    //metodo per selezionare l'utente partendo dal codice fiscale
    public Utente selezionaUtenteByCF(String codiceFiscale){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Utente utente = null;
        try{
            utente = (Utente) session.createQuery(
                    "SELECT s FROM Utente s WHERE codiceFiscale='" + codiceFiscale + "'").getSingleResult();
            session.getTransaction().commit();
        }catch (NoResultException nre){
            //non faccio niente perché voglio che utente rimanga a null
        }finally {
            session.close();
        }
        return utente;
    }

    //metodo per controllare se esiste l'utente con CF e passwd dati
    public Utente loginUtente(String codiceFiscale, String password){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Utente utente = null;
        try {
            utente = (Utente) session.createQuery(
                    "SELECT u FROM Utente u WHERE codiceFiscale='" + codiceFiscale + "' AND password='" + password + "'", Utente.class).getSingleResult();
            session.getTransaction().commit();
        }catch (NoResultException nre){
            //non faccio niente perché voglio che utente rimanga a null
        }finally {
            session.close();
        }
        return utente;
    }

    public void eliminaUtenteByCF(String codiceFiscale){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("DELETE FROM Utente WHERE codiceFiscale='" + codiceFiscale + "' ");
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }


    private static UtentiDAO istanza = null;
    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private UtentiDAO() {}
    // Metodo della classe impiegato per accedere al singleton
    public static synchronized UtentiDAO getUtentiDAO() {
        if (istanza == null) {
            istanza = new UtentiDAO();
        }
        return istanza;
    }
}

package app.DAO.DAOImplements;

import app.DTO.PrenotazioneDTO;
import app.model.entities.Prenotazione;
import app.model.entities.Veicolo;
import app.utils.HibernateUtil;
import app.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;

public class PrenotazioniDAO {
    public void salvaPrenotazione(PrenotazioneDTO prenotazioneDTO){
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDataInizio(prenotazioneDTO.getDataInizio());
        prenotazione.setDataFine(prenotazioneDTO.getDataFine());
        prenotazione.setUtente(prenotazioneDTO.getUtente());
        prenotazione.setVeicolo(prenotazioneDTO.getVeicolo());
        prenotazione.setApprovata(prenotazioneDTO.isApprovata());
        if(prenotazioneDTO.getBuonoSconto()!=null) {
            prenotazione.setBuonoSconto(prenotazioneDTO.getBuonoSconto());
        }
        //non posso fare int != null, quindi metto >0
        if(prenotazioneDTO.getNumero()>0){
            prenotazione.setNumero(prenotazioneDTO.getNumero());
        }
        SessionUtil.getSessionUtil().saveOrUpdatePrenotazione(prenotazione);
    }

    public List<Prenotazione> selezionaPrenotazioniByCF(String codiceFiscale){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Prenotazione> prenotazioni = null;
        try{
            prenotazioni = (List<Prenotazione>) session.createQuery(
                    "SELECT p FROM Prenotazione p WHERE id_utente='" + codiceFiscale + "' ORDER BY dataInizio DESC, dataFine DESC").list();
            session.getTransaction().commit();
        }catch (NoResultException nre){
            //non faccio niente perché voglio che utente rimanga a null
        }finally {
            session.close();
        }
        return prenotazioni;
    }

    public void approvaLaPrenotazione(int numeroPrenotazione){
        //fai una update
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Prenotazione prenotazione =
                    (Prenotazione)session.get(Prenotazione.class, numeroPrenotazione);
            prenotazione.setApprovata(true);
            session.update(prenotazione);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void eliminaPrenotazioneByNumero(int numeroPrenotazione){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("DELETE FROM Prenotazione WHERE numero='" + numeroPrenotazione + "'");
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public Prenotazione selezionaPrenotazioneByNumero(int numeroPrenotazione){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Prenotazione prenotazione = null;
        try{
            prenotazione = (Prenotazione) session.createQuery(
                    "SELECT p FROM Prenotazione p WHERE numero='" + numeroPrenotazione + "'").getSingleResult();
            session.getTransaction().commit();
        }catch (NoResultException nre){
            //non faccio niente perché voglio che prenotazione rimanga a null
        }finally {
            session.close();
        }
        return prenotazione;
    }

    public boolean esistePrenotazioneDataVeicolo(LocalDate dataSelezionata, Veicolo veicolo, int numeroPrenotazione){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Prenotazione prenotazione = null;
        try{
            prenotazione = (Prenotazione) session.createQuery(
                    "SELECT p FROM Prenotazione p WHERE id_veicolo='" + veicolo.getCodiceMezzo() + "' AND '"+ dataSelezionata + "'>=dataInizio AND '"+ dataSelezionata + "'<=dataFine").getSingleResult();
            session.getTransaction().commit();
        }catch (NoResultException nre){
            //non faccio niente perché voglio che prenotazione rimanga a null
        }finally {
            session.close();
        }
        if(prenotazione==null || prenotazione.getNumero() == numeroPrenotazione){
            return false;
        }else{
            return true;
        }
    }

    public List<Prenotazione> selezionaPrenotazioniByCodiceMezzo(String codiceMezzo){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Prenotazione> prenotazioni = null;
        try{
            prenotazioni = (List<Prenotazione>) session.createQuery(
                    "SELECT p FROM Prenotazione p WHERE id_veicolo='" + codiceMezzo + "' ORDER BY dataInizio DESC, dataFine DESC").list();
            session.getTransaction().commit();
        }catch (NoResultException nre){
            //non faccio niente perché voglio che utente rimanga a null
        }finally {
            session.close();
        }
        return prenotazioni;
    }


    private static PrenotazioniDAO istanza = null;
    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private PrenotazioniDAO() {}
    // Metodo della classe impiegato per accedere al singleton
    public static synchronized PrenotazioniDAO getPrenotazioniDAO() {
        if (istanza == null) {
            istanza = new PrenotazioniDAO();
        }
        return istanza;
    }
}

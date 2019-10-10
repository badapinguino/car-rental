package app.utils;

import app.model.entities.*;
import org.hibernate.Session;


//serve per abbreviare in UtentiDAO le operazioni che non ritornano nulla, come save or update
public class SessionUtil {
    private static SessionUtil istanza = null;

    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private SessionUtil() {}

    // Metodo della classe impiegato per accedere al singleton
    public static synchronized SessionUtil getSessionUtil() {
        if (istanza == null) {
            istanza = new SessionUtil();
        }
        return istanza;
    }

    public void saveOrUpdateUtente(Utente utente){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        /*int id = (Integer)*/ session.saveOrUpdate(utente); //va bene saveOrUpdate, o era meglio solo save? RICONTROLLARE dopo implementazione web
        session.getTransaction().commit();
        session.close();
    }

    public void saveOrUpdateMulta(Multa multa){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(multa);
        session.getTransaction().commit();
        session.close();
    }

    public void saveOrUpdateVeicolo(Veicolo veicolo){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        /*int id = (Integer)*/ session.saveOrUpdate(veicolo);
        session.getTransaction().commit();
        session.close();
    }

    public void saveOrUpdateBuonoSconto(BuonoSconto buonoSconto){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(buonoSconto);
        session.getTransaction().commit();
        session.close();
    }

    public void saveOrUpdatePrenotazione(Prenotazione prenotazione){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(prenotazione);
        session.getTransaction().commit();
        session.close();
    }
}

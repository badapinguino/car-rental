package app.DAO.DAOImplements;

import app.DTO.BuonoScontoDTO;
import app.exceptions.buonoSconto.CodiceScontoTroppoLungoException;
import app.exceptions.buonoSconto.ValoreNegativoOZeroException;
import app.exceptions.buonoSconto.ValorePercentualeOltreCentoException;
import app.model.entities.BuonoSconto;
import app.utils.HibernateUtil;
import app.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

public class BuoniScontoDAO {
    public List<BuonoSconto> selezionaTuttiBuoniSconto() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BuonoSconto> buoniSconto = null;
        try{
            session.beginTransaction();
            buoniSconto = (List<BuonoSconto>) session.createQuery(
                    "SELECT b FROM BuonoSconto b ORDER BY codiceSconto ASC").list();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return buoniSconto;
    }

    public BuonoSconto selezionaBuonoScontoByCodice(String codiceSconto){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        BuonoSconto buonoSconto = null;
        try{
            buonoSconto = (BuonoSconto) session.createQuery(
                    "SELECT b FROM BuonoSconto b WHERE codiceSconto = '" + codiceSconto + "'").getSingleResult();
            session.getTransaction().commit();
        }catch (NoResultException nre){
            //non faccio niente perché voglio che utente rimanga a null
        }finally {
            session.close();
        }
        return buonoSconto;
    }

    public void eliminaBuonoScontoByCodice(String codiceSconto) throws PersistenceException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("DELETE FROM BuonoSconto WHERE codiceSconto='" + codiceSconto + "'");
        try {
            q.executeUpdate();
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            throw pe;
        }finally{
            session.close();
        }
    }

    public void salvaBuonoSconto(BuonoScontoDTO buonoScontoDTO) throws ValorePercentualeOltreCentoException, ValoreNegativoOZeroException, CodiceScontoTroppoLungoException {
        BuonoSconto buonoSconto = new BuonoSconto();
        buonoSconto.setCodiceSconto(buonoScontoDTO.getCodiceSconto());
        //importante impostare prima la percentuale, se no non funziona il controllo su valore
        buonoSconto.setPercentuale(buonoScontoDTO.isPercentuale());
        buonoSconto.setValore(buonoScontoDTO.getValore());
        if(buonoScontoDTO.getPrenotazioni()!=null)
            buonoSconto.setPrenotazioni(buonoScontoDTO.getPrenotazioni());
        SessionUtil.getSessionUtil().saveOrUpdateBuonoSconto(buonoSconto);
    }

    private static BuoniScontoDAO istanza = null;
    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private BuoniScontoDAO() {}
    // Metodo della classe impiegato per accedere al singleton
    public static synchronized BuoniScontoDAO getBuoniScontoDAO() {
        if (istanza == null) {
            istanza = new BuoniScontoDAO();
        }
        return istanza;
    }
}

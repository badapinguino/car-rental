package app.DAO.DAOImplements;

import app.DTO.VeicoloDTO;
import app.exceptions.veicolo.*;
import app.model.entities.Veicolo;
import app.utils.HibernateUtil;
import app.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class VeicoliDAO {
    public Veicolo selezionaVeicoloByCodice(String codiceVeicolo){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Veicolo veicolo = null;
        try{
            veicolo = (Veicolo) session.createQuery(
                    "SELECT v FROM Veicolo v WHERE codice_mezzo = '" + codiceVeicolo + "'").getSingleResult();
            session.getTransaction().commit();
        }catch (NoResultException nre){
            //non faccio niente perché voglio che utente rimanga a null
        }finally {
            session.close();
        }
        return veicolo;
    }

    public List<Veicolo> selezionaTuttiVeicoli() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Veicolo> veicoli = null;
        try{
            session.beginTransaction();
            veicoli = (List<Veicolo>) session.createQuery(
                    "SELECT v FROM Veicolo v ORDER BY casaCostruttrice ASC, modello ASC, anno DESC").list();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return veicoli;
    }

    public void eliminaVeicoloByCodice(String codiceMezzo) throws PersistenceException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("DELETE FROM Veicolo WHERE codiceMezzo='" + codiceMezzo + "'");
        try {
            q.executeUpdate();
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            throw pe;
        }finally{
            session.close();
        }
    }

    public void salvaVeicolo(VeicoloDTO veicoloDTO) throws CodiceMezzoTroppoLungoException, TargaTroppoLungaException, CasaCostruttriceTroppoLungaException, ModelloTroppoLungoException, TipologiaTroppoLungaException, PrezzoGiornataTroppoBassoException, AnnoTroppoPiccoloException {
        Veicolo veicolo = new Veicolo();
        veicolo.setCodiceMezzo(veicoloDTO.getCodiceMezzo());
        veicolo.setTarga(veicoloDTO.getTarga());
        veicolo.setCasaCostruttrice(veicoloDTO.getCasaCostruttrice());
        veicolo.setModello(veicoloDTO.getModello());
        veicolo.setTipologia(veicoloDTO.getTipologia());
        veicolo.setPrezzoGiornata(veicoloDTO.getPrezzoGiornata());
        veicolo.setAnno(veicoloDTO.getAnno());
        if(veicoloDTO.getPrenotazioni()!=null)
            veicolo.setPrenotazioni(veicoloDTO.getPrenotazioni());
        SessionUtil.getSessionUtil().saveOrUpdateVeicolo(veicolo);
    }

    private static VeicoliDAO istanza = null;
    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private VeicoliDAO() {}
    // Metodo della classe impiegato per accedere al singleton
    public static synchronized VeicoliDAO getVeicoliDAO() {
        if (istanza == null) {
            istanza = new VeicoliDAO();
        }
        return istanza;
    }
}

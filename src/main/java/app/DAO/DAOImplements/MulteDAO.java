package app.DAO.DAOImplements;

import app.DTO.MultaDTO;
import app.exceptions.prenotazione.DataNonCompresaNellaPrenotazioneException;
import app.exceptions.prenotazione.DescrizioneTroppoLungaException;
import app.model.entities.Multa;
import app.utils.HibernateUtil;
import app.utils.SessionUtil;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class MulteDAO{
    public List<Multa> selezionaMulteByCF(String codiceFiscale){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Multa> multe = null;
        try{
            multe = (List<Multa>) session.createQuery(
                    "SELECT m FROM Multa m JOIN Prenotazione p ON id_prenotazione = numero JOIN Utente ON id_utente = codice_fiscale WHERE codice_fiscale = '" + codiceFiscale + "' ORDER BY p.dataInizio DESC, m.dataProblema DESC, p.dataFine DESC").list();
            session.getTransaction().commit();
        }catch (NoResultException nre){
            //non faccio niente perché voglio che utente rimanga a null
        }finally {
            session.close();
        }
        return multe;
    }

    public void salvaMulta(MultaDTO multaDTO) throws DataNonCompresaNellaPrenotazioneException, DescrizioneTroppoLungaException {
        Multa multa = new Multa();
        multa.setDescrizione(multaDTO.getDescrizione());
        //prima prenotazione poi dataProblema perché se no dataProblema non sa quale siano data inizio e fine
        multa.setPrenotazione(multaDTO.getPrenotazione());
        if(multaDTO.getDataProblema()!=null){
            multa.setDataProblema(multaDTO.getDataProblema());
        }
            SessionUtil.getSessionUtil().saveOrUpdateMulta(multa);
    }


    private static MulteDAO istanza = null;
    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private MulteDAO() {}
    // Metodo della classe impiegato per accedere al singleton
    public static synchronized MulteDAO getMulteDAO() {
        if (istanza == null) {
            istanza = new MulteDAO();
        }
        return istanza;
    }
}

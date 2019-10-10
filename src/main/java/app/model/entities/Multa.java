package app.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import app.exceptions.prenotazione.DataNonCompresaNellaPrenotazioneException;
import app.exceptions.prenotazione.DescrizioneTroppoLungaException;
import app.model.entities.Prenotazione;
import app.services.PrenotazioniService;
import net.bytebuddy.asm.Advice;

@Entity
@Table(name = "Problema")
public class Multa implements Serializable {
    public static final int lunghezzaCampoDescrizione = 256;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="codice", nullable = false)
    private int codice;

    @Column(name="descrizione", nullable = false)
    private String descrizione;

    @Column(name="data_problema", nullable = true)
    private LocalDate dataProblema;

    @ManyToOne
    @JoinColumn(name="id_prenotazione", nullable = false)
    private Prenotazione prenotazione;

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) throws DescrizioneTroppoLungaException {
        if(descrizione.length()>lunghezzaCampoDescrizione){
            throw new DescrizioneTroppoLungaException();
        }else{
            this.descrizione = descrizione;
        }
    }

    public LocalDate getDataProblema() {
        return dataProblema;
    }

    public void setDataProblema(LocalDate dataProblema) throws DataNonCompresaNellaPrenotazioneException {
        LocalDate dataInizioPrenotazione = PrenotazioniService.getPrenotazioniService().selezionaDataInizioByNumero(prenotazione.getNumero());
        LocalDate dataFinePrenotazione = PrenotazioniService.getPrenotazioniService().selezionaDataFineByNumero(prenotazione.getNumero());
        if(dataProblema.compareTo(dataInizioPrenotazione) < 0 || dataProblema.compareTo(dataFinePrenotazione) > 0){
            throw new DataNonCompresaNellaPrenotazioneException();
        }else{
            this.dataProblema = dataProblema;
        }
    }

    public Prenotazione getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }

    public static int getLunghezzaCampoDescrizione() {
        return lunghezzaCampoDescrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Multa)) return false;
        Multa multa = (Multa) o;
        return codice == multa.codice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }
}

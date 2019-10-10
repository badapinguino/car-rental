package app.DTO;

import app.model.entities.Prenotazione;

import java.time.LocalDate;

public class MultaDTO {
    private int codice;
    private String descrizione;
    private LocalDate dataProblema;
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

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getDataProblema() {
        return dataProblema;
    }

    public void setDataProblema(LocalDate dataProblema) {
        this.dataProblema = dataProblema;
    }

    public Prenotazione getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }
}

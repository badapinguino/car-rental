package app.DTO;

import app.model.entities.Prenotazione;

import java.util.Set;

public class BuonoScontoDTO {
    private String codiceSconto;
    private float valore;
    private boolean percentuale;
    private Set<Prenotazione> prenotazioni;

    public String getCodiceSconto() {
        return codiceSconto;
    }

    public void setCodiceSconto(String codiceSconto) {
        this.codiceSconto = codiceSconto;
    }

    public float getValore() {
        return valore;
    }

    public void setValore(float valore) {
        this.valore = valore;
    }

    public boolean isPercentuale() {
        return percentuale;
    }

    public void setPercentuale(boolean percentuale) {
        this.percentuale = percentuale;
    }

    public Set<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(Set<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }
}

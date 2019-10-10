package app.model.entities;

import app.exceptions.buonoSconto.CodiceScontoTroppoLungoException;
import app.exceptions.buonoSconto.ValoreNegativoOZeroException;
import app.exceptions.buonoSconto.ValorePercentualeOltreCentoException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="Buono_Sconto")
public class BuonoSconto implements Serializable{
    public static final int lunghezzaCodiceSconto=15;

    public static int getLunghezzaCodiceSconto() {
        return lunghezzaCodiceSconto;
    }

    @Id
    @Column(name = "codice_sconto", nullable = false)
    private String codiceSconto;

    @Column(name = "valore", nullable = false)
    private float valore;

    @Column(name = "percentuale", nullable = false)
    private boolean percentuale;

    @OneToMany(mappedBy = "buonoSconto", cascade=CascadeType.REMOVE)
    private Set<Prenotazione> prenotazioni;

    public String getCodiceSconto() {
        return codiceSconto;
    }

    public void setCodiceSconto(String codiceSconto) throws CodiceScontoTroppoLungoException {
        if(codiceSconto.length()>lunghezzaCodiceSconto){
            throw new CodiceScontoTroppoLungoException();
        }else{
            this.codiceSconto = codiceSconto;
        }
    }

    public float getValore() {
        return valore;
    }

    public void setValore(float valore) throws ValorePercentualeOltreCentoException, ValoreNegativoOZeroException {
        if(isPercentuale() && valore>100){
            throw new ValorePercentualeOltreCentoException();
        }else if(valore<=0){
            throw new ValoreNegativoOZeroException();
        }else{
            this.valore = valore;
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuonoSconto)) return false;
        BuonoSconto that = (BuonoSconto) o;
        return codiceSconto.equals(that.codiceSconto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceSconto);
    }
}

package app.model.entities;

import com.sun.xml.internal.ws.addressing.ProblemAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="Prenotazione")
public class Prenotazione implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="numero", nullable = false)
    private int numero;

    @Column(name="data_inizio", nullable = false)
    private LocalDate dataInizio;

    @Column(name="data_fine", nullable = false)
    private LocalDate dataFine;

    @Column(name="approvata", nullable = false)
    private boolean approvata;
//    @Column(name="id_veicolo", nullable = false)
//    private String idVeicolo;

//    @Column(name="id_utente", nullable = false)
//    private String idUtente;

//    @Column(name="id_buono_sconto", nullable = false)
//    private String idBuonoSconto;

    @OneToMany(mappedBy = "prenotazione", cascade=CascadeType.REMOVE)
    private Set<Multa> multe;

    @ManyToOne
    @JoinColumn(name="id_veicolo", nullable = false)
    private Veicolo veicolo;

    @ManyToOne
    @JoinColumn(name="id_utente", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name="id_buono_sconto", nullable = true)
    private BuonoSconto buonoSconto;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public Set<Multa> getMulte() {
        return multe;
    }

    public void setMulte(Set<Multa> multe) {
        this.multe = multe;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public BuonoSconto getBuonoSconto() {
        return buonoSconto;
    }

    public void setBuonoSconto(BuonoSconto buonoSconto) {
        this.buonoSconto = buonoSconto;
    }

    public boolean isApprovata() {
        return approvata;
    }

    public void setApprovata(boolean approvata) {
        this.approvata = approvata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prenotazione)) return false;
        Prenotazione that = (Prenotazione) o;
        return numero == that.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}

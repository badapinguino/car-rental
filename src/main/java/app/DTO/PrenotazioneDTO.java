package app.DTO;

import app.model.entities.BuonoSconto;
import app.model.entities.Multa;
import app.model.entities.Utente;
import app.model.entities.Veicolo;

import java.time.LocalDate;
import java.util.Set;

public class PrenotazioneDTO {
    private int numero;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private boolean approvata;
    private Set<Multa> multe;
    private Veicolo veicolo;
    private Utente utente;
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
}

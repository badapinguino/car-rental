package app.DTO;

import app.model.entities.Prenotazione;

import java.util.Set;

public class VeicoloDTO {
    private String codiceMezzo;
    private String targa;
    private String modello;
    private String casaCostruttrice;
    private int anno;
    private String tipologia;
    private float prezzoGiornata;
    private Set<Prenotazione> prenotazioni;

    public String getCodiceMezzo() {
        return codiceMezzo;
    }

    public void setCodiceMezzo(String codiceMezzo) {
        this.codiceMezzo = codiceMezzo;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getCasaCostruttrice() {
        return casaCostruttrice;
    }

    public void setCasaCostruttrice(String casaCostruttrice) {
        this.casaCostruttrice = casaCostruttrice;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public float getPrezzoGiornata() {
        return prezzoGiornata;
    }

    public void setPrezzoGiornata(float prezzoGiornata) {
        this.prezzoGiornata = prezzoGiornata;
    }

    public Set<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(Set<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }
}

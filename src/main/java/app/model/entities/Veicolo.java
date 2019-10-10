package app.model.entities;

import app.exceptions.veicolo.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="Veicolo")
public class Veicolo implements Serializable {
    private static final int lunghezzaCampoCodiceMezzo = 15;
    private static final int lunghezzaCampoTarga = 10;
    private static final int lunghezzaCampoCasaCostruttrice = 80;
    private static final int lunghezzaCampoModello = 80;
    private static final int lunghezzaCampoTipologia = 20;

    public static int getLunghezzaCampoCodiceMezzo() {
        return lunghezzaCampoCodiceMezzo;
    }

    public static int getLunghezzaCampoTarga() {
        return lunghezzaCampoTarga;
    }

    public static int getLunghezzaCampoModello() {
        return lunghezzaCampoModello;
    }

    public static int getLunghezzaCampoTipologia() {
        return lunghezzaCampoTipologia;
    }

    public static int getLunghezzaCampoCasaCostruttrice() {
        return lunghezzaCampoCasaCostruttrice;
    }

    @Id
    @Column(name="codice_mezzo", nullable = false)
    private String codiceMezzo;

    @Column(name="targa", nullable = false)
    private String targa;

    @Column(name="modello", nullable = false)
    private String modello;

    @Column(name="casa_costruttrice", nullable = false)
    private String casaCostruttrice;

    @Column(name="anno", nullable = false)
    private int anno;

    @Column(name="tipologia", nullable = false)
    private String tipologia;

    @Column(name="prezzo_giornata", nullable = false)
    private float prezzoGiornata;

    @OneToMany(mappedBy = "veicolo", cascade=CascadeType.REMOVE)
    private Set<Prenotazione> prenotazioni;

    public String getCodiceMezzo() {
        return codiceMezzo;
    }

    public void setCodiceMezzo(String codiceMezzo) throws CodiceMezzoTroppoLungoException {
        if(codiceMezzo.length()<=lunghezzaCampoCodiceMezzo){
            this.codiceMezzo = codiceMezzo;
        }else{
            throw new CodiceMezzoTroppoLungoException();
        }
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) throws TargaTroppoLungaException {
        if(targa.length()<=lunghezzaCampoTarga){
            this.targa = targa;
        }else{
            throw new TargaTroppoLungaException();
        }
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) throws ModelloTroppoLungoException {
        if(modello.length()<=lunghezzaCampoModello){
            this.modello = modello;
        }else{
            throw new ModelloTroppoLungoException();
        }
    }

    public String getCasaCostruttrice() {
        return casaCostruttrice;
    }

    public void setCasaCostruttrice(String casaCostruttrice) throws CasaCostruttriceTroppoLungaException {
        if(casaCostruttrice.length()<=lunghezzaCampoCasaCostruttrice){
            this.casaCostruttrice = casaCostruttrice;
        }else{
            throw new CasaCostruttriceTroppoLungaException();
        }
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) throws AnnoTroppoPiccoloException {
        if(anno>=1900){
            this.anno = anno;
        }else{
            throw new AnnoTroppoPiccoloException();
        }
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) throws TipologiaTroppoLungaException {
        if(tipologia.length()<=lunghezzaCampoTipologia){
            this.tipologia = tipologia;
        }else{
            throw new TipologiaTroppoLungaException();
        }
    }

    public float getPrezzoGiornata() {
        return prezzoGiornata;
    }

    public void setPrezzoGiornata(float prezzoGiornata) throws PrezzoGiornataTroppoBassoException {
        if(prezzoGiornata>=0){
            this.prezzoGiornata = prezzoGiornata;
        }else{
            throw new PrezzoGiornataTroppoBassoException();
        }
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
        if (!(o instanceof Veicolo)) return false;
        Veicolo veicolo = (Veicolo) o;
        return codiceMezzo.equals(veicolo.codiceMezzo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceMezzo);
    }
}

package app.model.entities;

import app.exceptions.utente.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Utente")
public class Utente implements Serializable{
    private static final int lunghezzaCampoCognome = 80;
    private static final int lunghezzaCampoNome = 80;
    private static final int lunghezzaCampoCodiceFiscale = 16;
    private static final int lunghezzaCampoPassword = 42;

    public static int getLunghezzaCampoPassword() {
        return lunghezzaCampoPassword;
    }

    public static int getLunghezzaCampoCodiceFiscale(){
        return lunghezzaCampoCodiceFiscale;
    }
    public static int getLunghezzaCampoCognome(){
        return lunghezzaCampoCognome;
    }
    public static int getLunghezzaCampoNome(){
        return lunghezzaCampoNome;
    }

    @Id
    @Column(name = "codice_fiscale", nullable = false)
    private String codiceFiscale;

    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;

    @Column(name = "superuser", nullable = false)
    private boolean superuser;

    @Column(name = "password_utente", nullable = false)
    private String password;

    @OneToMany(mappedBy = "utente", cascade=CascadeType.REMOVE)
    private Set<Prenotazione> prenotazioni;

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) throws CodiceFiscaleTroppoLungoException, CodiceFiscaleTroppoCortoException {
        if(codiceFiscale.length()==lunghezzaCampoCodiceFiscale){
            this.codiceFiscale = codiceFiscale;
        }else if(codiceFiscale.length()>lunghezzaCampoCodiceFiscale){
            throw new CodiceFiscaleTroppoLungoException();
        }else{
            throw new CodiceFiscaleTroppoCortoException();
        }
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) throws CognomeTroppoLungoException {
        if(cognome.length()<=lunghezzaCampoCognome) {
            this.cognome = cognome;
        }else{
            throw new CognomeTroppoLungoException();
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws NomeTroppoLungoException {
        if(nome.length()<=lunghezzaCampoNome) {
            this.nome = nome;
        }else{
            throw new NomeTroppoLungoException();
        }
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) throws DataDiNascitaFuturaException {
        if(dataNascita.compareTo(LocalDate.now())<=0) {
            this.dataNascita = dataNascita;
        }else{
            throw new DataDiNascitaFuturaException();
        }
    }

    public boolean isSuperuser() {
        return superuser;
    }

    public void setSuperuser(boolean superuser) {
        this.superuser = superuser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws PasswordTroppoLungaException {
        if(password.length()<=lunghezzaCampoPassword) {
            this.password = password;
        }else{
            throw new PasswordTroppoLungaException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utente)) return false;
        Utente utente = (Utente) o;
        return codiceFiscale.equals(utente.codiceFiscale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceFiscale);
    }
}

package app.DTO;


import java.time.LocalDate;

public class UtenteDTO {
    private String codiceFiscale;
    private String cognome;
    private String nome;
    private LocalDate dataNascita;
    private boolean superuser;
    private String password;

    public UtenteDTO(String codiceFiscale, String cognome, String nome, LocalDate dataNascita, String password, boolean superuser) {
        this.codiceFiscale = codiceFiscale;
        this.cognome = cognome;
        this.nome = nome;
        this.dataNascita = dataNascita;
        this.superuser = superuser;
        this.password = password;
    }
    public UtenteDTO() {
        //costruttore vuoto
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascita() { return dataNascita; }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
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

    public void setPassword(String password) {
        this.password = password;
    }
}

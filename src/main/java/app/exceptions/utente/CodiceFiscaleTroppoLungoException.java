package app.exceptions.utente;

import app.model.entities.Utente;

public class CodiceFiscaleTroppoLungoException extends Exception{
    String error = "Codice fiscale troppo lungo, supera i "+ Utente.getLunghezzaCampoCodiceFiscale() +" caratteri.";
    public CodiceFiscaleTroppoLungoException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

package app.exceptions.utente;

import app.model.entities.Utente;

public class CodiceFiscaleTroppoCortoException extends Exception{
    String error = "Codice fiscale troppo corto, deve essere di "+ Utente.getLunghezzaCampoCodiceFiscale() +" caratteri.";
    public CodiceFiscaleTroppoCortoException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

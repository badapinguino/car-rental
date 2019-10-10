package app.exceptions.utente;

import app.model.entities.Utente;

public class CognomeTroppoLungoException extends Exception{
    String error = "Cognome troppo lungo, supera gli "+ Utente.getLunghezzaCampoCognome() +" caratteri.";
    public CognomeTroppoLungoException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}


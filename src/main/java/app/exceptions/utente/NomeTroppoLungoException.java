package app.exceptions.utente;

import app.model.entities.Utente;

public class NomeTroppoLungoException extends Exception{
    String error = "Nome troppo lungo, supera gli "+ Utente.getLunghezzaCampoNome() +" caratteri.";
    public NomeTroppoLungoException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

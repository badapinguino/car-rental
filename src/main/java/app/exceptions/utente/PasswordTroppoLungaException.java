package app.exceptions.utente;

import app.model.entities.Utente;

public class PasswordTroppoLungaException extends Exception{
    String error = "Password troppo lunga, supera i "+ Utente.getLunghezzaCampoPassword() +" caratteri";
    public PasswordTroppoLungaException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
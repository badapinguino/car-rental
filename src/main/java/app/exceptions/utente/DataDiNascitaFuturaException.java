package app.exceptions.utente;

import app.model.entities.Utente;

public class DataDiNascitaFuturaException extends Exception {
    String error = "Data di nascita nel futuro. La data di nascita deve essere minore o uguale alla data odierna.";
    public DataDiNascitaFuturaException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

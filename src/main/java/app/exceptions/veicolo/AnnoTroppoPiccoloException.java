package app.exceptions.veicolo;

import app.model.entities.Veicolo;

public class AnnoTroppoPiccoloException extends Exception {
    String error = "L'anno inserito non è valido, non può essere minore del 1900.";
    public AnnoTroppoPiccoloException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
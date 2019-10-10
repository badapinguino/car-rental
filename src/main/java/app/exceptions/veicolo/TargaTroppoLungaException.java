package app.exceptions.veicolo;

import app.model.entities.Veicolo;

public class TargaTroppoLungaException extends Exception {
    String error = "Targa troppo lunga, supera gli "+ Veicolo.getLunghezzaCampoTarga() +" caratteri.";
    public TargaTroppoLungaException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
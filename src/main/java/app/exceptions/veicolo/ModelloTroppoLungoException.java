package app.exceptions.veicolo;

import app.model.entities.Veicolo;

public class ModelloTroppoLungoException extends Exception {
    String error = "Nome modello troppo lungo, supera gli "+ Veicolo.getLunghezzaCampoModello() +" caratteri.";
    public ModelloTroppoLungoException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
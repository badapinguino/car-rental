package app.exceptions.veicolo;

import app.model.entities.Veicolo;

public class CodiceMezzoTroppoLungoException extends Exception {
    String error = "Codice mezzo troppo lungo, supera gli "+ Veicolo.getLunghezzaCampoCodiceMezzo() +" caratteri.";
    public CodiceMezzoTroppoLungoException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

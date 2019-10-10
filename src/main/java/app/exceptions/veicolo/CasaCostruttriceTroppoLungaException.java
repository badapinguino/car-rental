package app.exceptions.veicolo;

import app.model.entities.Veicolo;

public class CasaCostruttriceTroppoLungaException extends Exception {
    String error = "Nome casa costruttrice troppo lungo, supera gli "+ Veicolo.getLunghezzaCampoCasaCostruttrice() +" caratteri.";
    public CasaCostruttriceTroppoLungaException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
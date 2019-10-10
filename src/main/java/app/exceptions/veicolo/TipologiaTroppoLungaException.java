package app.exceptions.veicolo;

import app.model.entities.Veicolo;

public class TipologiaTroppoLungaException extends Exception {
    String error = "Tipologia veicolo troppo lunga, supera gli "+ Veicolo.getLunghezzaCampoTipologia() +" caratteri.";
    public TipologiaTroppoLungaException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

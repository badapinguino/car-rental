package app.exceptions.buonoSconto;

import app.model.entities.BuonoSconto;

public class CodiceScontoTroppoLungoException extends Exception{
    String error = "Codice sconto troppo lungo, supera gli " + BuonoSconto.getLunghezzaCodiceSconto() + " caratteri.";
    public CodiceScontoTroppoLungoException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

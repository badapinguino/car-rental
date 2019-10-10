package app.exceptions.veicolo;

import app.model.entities.Veicolo;

public class PrezzoGiornataTroppoBassoException extends Exception {
    String error = "Il prezzo del veicolo per giornata non può essere minore o pari a 0.";
    public PrezzoGiornataTroppoBassoException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

package app.exceptions.prenotazione;

import app.model.entities.Multa;

public class DescrizioneTroppoLungaException extends Exception{
    String error = "Descrizione troppo lunga, non deve superare i "+ Multa.getLunghezzaCampoDescrizione() +" caratteri.";
    public DescrizioneTroppoLungaException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
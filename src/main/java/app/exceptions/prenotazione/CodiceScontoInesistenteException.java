package app.exceptions.prenotazione;

public class CodiceScontoInesistenteException extends Exception{
    String error = "Il codice sconto inserito è inesistente.";
    public CodiceScontoInesistenteException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
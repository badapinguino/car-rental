package app.exceptions.prenotazione;

public class VeicoloNonDisponibileDateSelezionateException extends Exception{
    String error = "Il veicolo scelto non è disponibile nelle date selezionate.";
    public VeicoloNonDisponibileDateSelezionateException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
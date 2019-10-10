package app.exceptions.prenotazione;

public class DataNonFuturaException extends Exception{
    String error = "La data selezionata è una data passata o quella odierna.";
    public DataNonFuturaException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
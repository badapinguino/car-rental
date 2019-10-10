package app.exceptions.prenotazione;

public class DataInizioPiuGrandeDataFineException extends Exception{
    String error = "Le date inserite non sono accettabili: la data di inizio è futura rispetto alla data di fine.";
    public DataInizioPiuGrandeDataFineException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
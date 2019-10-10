package app.exceptions.buonoSconto;

public class ValorePercentualeOltreCentoException extends Exception {
    String error = "Il valore percentuale supera il cento percento";
    public ValorePercentualeOltreCentoException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

package app.exceptions.buonoSconto;

public class ValoreNegativoOZeroException extends Exception{
    String error = "Il valore non può essere 0 o minore di 0";
    public ValoreNegativoOZeroException(){}

    public String getError() {
        return error;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

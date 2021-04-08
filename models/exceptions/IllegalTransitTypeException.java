package models.exceptions;

@Deprecated
public class IllegalTransitTypeException extends Exception {

    public IllegalTransitTypeException() {
        super("Illegal transit type");
    }

}

package fr.swansky.papercommandlib.exceptions;

public class ValidatorException extends Exception {
    public ValidatorException(String message, Object... arg) {
        super(String.format(message, arg));
    }
}

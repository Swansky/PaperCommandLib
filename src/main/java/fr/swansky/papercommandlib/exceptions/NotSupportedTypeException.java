package fr.swansky.papercommandlib.exceptions;

public class NotSupportedTypeException extends RuntimeException {
    public NotSupportedTypeException(Class<?> message) {
        super("not supported class: " + message.getName());
    }
}

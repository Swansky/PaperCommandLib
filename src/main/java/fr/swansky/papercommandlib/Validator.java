package fr.swansky.papercommandlib;

import fr.swansky.papercommandlib.exceptions.NotSupportedTypeException;
import fr.swansky.papercommandlib.exceptions.ValidatorException;
import org.bukkit.command.CommandSender;

public interface Validator<T> {
    void validate(T valueToValidate, CommandSender executor, String parameterName) throws ValidatorException, NotSupportedTypeException;
}

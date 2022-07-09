package fr.swansky.papercommandlib;

import org.bukkit.command.CommandSender;

import java.text.ParseException;
import java.util.List;

public interface Variable<T> {
    Object parseValueToVariable(String valueToParse) throws IllegalArgumentException;

    String getName();

    Class<T> getType();

    Variable<T> addCustomValidator(Validator<T> value);

    List<Validator<T>> getValidors();
}

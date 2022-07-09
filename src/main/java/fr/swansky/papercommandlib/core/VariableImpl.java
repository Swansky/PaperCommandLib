package fr.swansky.papercommandlib.core;

import fr.swansky.papercommandlib.Validator;
import fr.swansky.papercommandlib.Variable;
import fr.swansky.papercommandlib.exceptions.NotSupportedTypeException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class VariableImpl<T> implements Variable<T> {

    private final String name;
    private final Class<T> type;

    private final List<Validator<T>> validators = new ArrayList<>();

    public VariableImpl(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public Object parseValueToVariable(String valueToParse) throws IllegalArgumentException {
        if (type.isEnum()) {
            for (Object enumConstant : type.getEnumConstants()) {
                if (enumConstant.toString().equalsIgnoreCase(valueToParse)) {
                    return enumConstant;
                }
            }
            throw new IllegalArgumentException("Invalid enum value.");
        } else if (type.isAssignableFrom(Float.class)) {
            return Float.parseFloat(valueToParse);
        } else if (type.isAssignableFrom(Integer.class)) {
            return Integer.parseInt(valueToParse);
        } else if (type.isAssignableFrom(Long.class)) {
            return Long.parseLong(valueToParse);
        } else if (type.isAssignableFrom(Short.class)) {
            return Short.parseShort(valueToParse);
        } else if (type.isAssignableFrom(Player.class)) {
            Player player = Bukkit.getPlayer(valueToParse);
            if (player != null) {
                return player;
            } else {
                throw new IllegalArgumentException("Invalid player name.");
            }
        } else if (type.isAssignableFrom(String.class)) {
            return valueToParse;
        } else {
            throw new NotSupportedTypeException(type);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public Variable<T> addCustomValidator(Validator<T> value) {
        validators.add(value);
        return this;
    }

    @Override
    public List<Validator<T>> getValidors() {
        return validators;
    }
}

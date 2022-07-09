package fr.swansky.papercommandlib.utils;

import org.bukkit.command.CommandSender;

public class PermissionUtils {


    public static boolean SenderHasOneOfPermission(CommandSender sender, String... permissions) {
        for (String s : permissions) {
            if (sender.hasPermission(s)) {
                return true;
            }
        }
        return false;
    }
}

package q.andres.qMOTD.utils;

import net.md_5.bungee.api.ChatColor;

public class CC {
    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}

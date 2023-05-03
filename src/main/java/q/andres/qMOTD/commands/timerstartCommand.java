package q.andres.qMOTD.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import q.andres.qMOTD.Main;

public class timerstartCommand extends Command {

    public timerstartCommand() {
        super("timerstart", "bungeecord.timerstart");
    }

    private static int toSeconds(String timeStr) {
        timeStr = timeStr.toLowerCase();
        int days = 0, hours = 0, minutes = 0, seconds = 0;

        String[] parts = timeStr.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");

        for (int i = 0; i < parts.length; i += 2) {
            int value = Integer.parseInt(parts[i]);

            switch (parts[i+1]) {
                case "d":
                    days = value;
                    break;
                case "h":
                    hours = value;
                    break;
                case "m":
                    minutes = value;
                    break;
                case "s":
                    seconds = value;
                    break;
                default:
                    return -1;
            }
        }

        if (days < 0 || hours < 0 || minutes < 0 || seconds < 0) {
            return -1;
        }

        return days * 24 * 60 * 60 + hours * 60 * 60 + minutes * 60 + seconds;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String[] args) {
        Main instance = Main.getInstance();
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(instance.CT("&4No console."));
            return;
        }
        if (args.length != 2) {
            sender.sendMessage(instance.CT("&cInvalid use. Example: /timerstart <placeholder> <time>(Example: 1h15m5s)"));
            return;
        }

        Integer seconds = toSeconds(args[1]);
        if(!Main.timers.isEmpty()){
            for(String k : Main.timers.keySet()){
                Main.timers.remove(k);
            }
        }
        if(seconds.equals(-1)){
            sender.sendMessage(instance.CT("&4Invalid time format."));
            return;
        }
        if(Main.timers.containsKey(args[0])){
            sender.sendMessage(instance.CT("&4Timer already started."));
            return;
        }
        Main.timers.put(args[0], seconds);
        sender.sendMessage(instance.CT("&aTimer started."));
    }
}
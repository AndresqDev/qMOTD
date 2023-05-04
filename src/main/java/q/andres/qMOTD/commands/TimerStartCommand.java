package q.andres.qMOTD.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import q.andres.qMOTD.utils.CC;

import static q.andres.qMOTD.utils.TimeUtil.toSeconds;
import static q.andres.qMOTD.utils.data.Vars.timers;

public class TimerStartCommand extends Command {
    public TimerStartCommand() {
        super("timerstart", "bungeecord.timerstart");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(CC.translate("&4No console."));
            return;
        }
        if (args.length != 2) {
            sender.sendMessage(CC.translate("&cInvalid use. Example: /timerstart <placeholder> <time>(Example: 1h15m5s)"));
            return;
        }

        Integer seconds = toSeconds(args[1]);
        if (!timers.isEmpty()) {
            for (String k : timers.keySet()) {
                timers.remove(k);
            }
        }
        if (seconds.equals(-1)) {
            sender.sendMessage(CC.translate("&4Invalid time format."));
            return;
        }
        String firstArg = args[0];
        if (timers.containsKey(firstArg)) {
            sender.sendMessage(CC.translate("&4Timer already started."));
            return;
        }
        timers.put(firstArg, seconds);
        sender.sendMessage(CC.translate("&aTimer started."));
    }
}
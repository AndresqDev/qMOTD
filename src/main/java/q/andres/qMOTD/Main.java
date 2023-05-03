package q.andres.qMOTD;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.event.EventHandler;
import q.andres.qMOTD.commands.timerstartCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main extends Plugin implements Listener {

    public String CT(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    private static Main instance;

    private static String lastmotd = "&aJoin!";

    public static HashMap<String, Integer> timers = new HashMap<>();

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing resp = e.getResponse();
        String motd = "&a❈ &bTest Network &8┃ &fWorking on &aNEW! &fSeason &a❈\n     &f&l• &a&l{event} {time} &f&l•";
        resp.getPlayers().setMax(2023);

        if(timers.isEmpty()){
            resp.setDescription(CT(motd.replace("{event}", lastmotd).replace("{time}", "&a!NOW¡")));
        } else {
            for(Map.Entry<String, Integer> k : timers.entrySet()){
                resp.setDescription(CT(motd.replace("{event}", k.getKey()).replace("{time}", "&f"+fromSeconds(k.getValue()))));
            }
        }
        e.setResponse(resp);
    }

    @Override
    public void onEnable() {
        instance = this;
        Logger log = getLogger();
        ProxyServer proxy = getProxy();
        TaskScheduler scheduler = proxy.getScheduler();
        PluginManager pm = proxy.getPluginManager();
        log.info(CT("&7&m--------------------"));
        try {
            pm.registerCommand(instance, new timerstartCommand());
            startTicking(scheduler, instance);
            pm.registerListener(instance, this);
        } catch (Exception e){
            log.info(CT("&b| &4qMOTD Activation Error!"));
            log.info(CT("&7- Custom plugins > Andresq#2303"));
            log.info(CT("&7&m--------------------"));
            return;
        }
        log.info(CT("&b| &aqMOTD Activated!"));
        log.info(CT("&7- Custom plugins > Andresq#2303"));
        log.info(CT("&7&m--------------------"));
    }

    public static String fromSeconds(Integer totalSeconds) {
        int days = totalSeconds / (24 * 60 * 60);
        int hours = (totalSeconds % (24 * 60 * 60)) / (60 * 60);
        int minutes = (totalSeconds % (60 * 60)) / 60;
        int seconds = totalSeconds % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append("d");
        }
        if (hours > 0) {
            sb.append(hours).append("h");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m");
        }
        sb.append(seconds).append("s");

        return sb.toString();
    }

    public static void startTicking(TaskScheduler scheduler, Plugin instance) {
        scheduler.schedule(instance, () -> {
            if(!timers.isEmpty()){
                for(Map.Entry<String, Integer> k : timers.entrySet()){
                    if(k.getValue() < 0 || k.getValue() == 0) {
                        lastmotd = k.getKey();
                        timers.remove(k.getKey());
                    } else {
                        timers.put(k.getKey(), k.getValue()-1);
                    }
                }
            }
        }, 1L, 1L, TimeUnit.SECONDS);
    }

    public static Main getInstance() {
        return instance;
    }
}

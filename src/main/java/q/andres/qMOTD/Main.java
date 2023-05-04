package q.andres.qMOTD;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import q.andres.qMOTD.commands.TimerStartCommand;
import q.andres.qMOTD.listeners.PingListener;
import q.andres.qMOTD.utils.CC;

import java.util.logging.Logger;

import static q.andres.qMOTD.utils.TimeUtil.startTicking;

public class Main extends Plugin implements Listener {

    @Override
    public void onLoad() {
        Logger log = getLogger();

        log.info(CC.translate("&7&m--------------------"));
        log.info(CC.translate("&b| &aqMOTD Activated!"));
        log.info(CC.translate("&7- Custom plugins > Andresq#2303"));
        log.info(CC.translate("&7&m--------------------"));
    }

    @Override
    public void onEnable() {
        ProxyServer proxy = getProxy();
        TaskScheduler scheduler = proxy.getScheduler();
        PluginManager pm = proxy.getPluginManager();

        pm.registerCommand(this, new TimerStartCommand());
        pm.registerListener(this, new PingListener());
        startTicking(scheduler, this);
    }
}

package q.andres.qMOTD.listeners;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import q.andres.qMOTD.utils.CC;

import java.util.Map;

import static q.andres.qMOTD.utils.TimeUtil.fromSeconds;
import static q.andres.qMOTD.utils.data.Consts.MOTD;
import static q.andres.qMOTD.utils.data.Vars.lastmotd;
import static q.andres.qMOTD.utils.data.Vars.timers;

public class PingListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing resp = e.getResponse();
        resp.getPlayers().setMax(2023);

        if (timers.isEmpty()) {
            resp.setDescription(CC.translate(MOTD.replace("{event}", lastmotd).replace("{time}", "&a!NOW¡")));
        } else {
            for (Map.Entry<String, Integer> k : timers.entrySet()) {
                resp.setDescription(CC.translate(MOTD.replace("{event}", k.getKey()).replace("{time}", "&f" + fromSeconds(k.getValue()))));
            }
        }
        e.setResponse(resp);
    }
}

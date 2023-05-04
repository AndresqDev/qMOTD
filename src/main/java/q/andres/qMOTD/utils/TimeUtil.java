package q.andres.qMOTD.utils;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import q.andres.qMOTD.utils.data.Vars;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static q.andres.qMOTD.utils.data.Consts.regex;
import static q.andres.qMOTD.utils.data.Vars.timers;

public class TimeUtil {
    public static void startTicking(TaskScheduler scheduler, Plugin instance) {
        scheduler.schedule(instance, () -> {
            if (!timers.isEmpty()) {
                for (Map.Entry<String, Integer> k : timers.entrySet()) {
                    if (k.getValue() < 0 || k.getValue() == 0) {
                        Vars.lastmotd = k.getKey();
                        timers.remove(k.getKey());
                    } else {
                        timers.put(k.getKey(), k.getValue() - 1);
                    }
                }
            }
        }, 1L, 1L, TimeUnit.SECONDS);
    }

    public static int toSeconds(String timeStr) {
        timeStr = timeStr.toLowerCase();
        int days = 0, hours = 0, minutes = 0, seconds = 0;

        String[] parts = timeStr.split(regex);

        for (int i = 0; i < parts.length; i += 2) {
            int value = Integer.parseInt(parts[i]);

            switch (parts[i + 1]) {
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
}

package me.flezy.api;

import lombok.Getter;
import lombok.Setter;
import me.flezy.Main;
import me.flezy.listener.WaterListener;
import me.flezy.setter.Progress;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class EventManager {
    @Setter
    @Getter
    static HashMap<Progress, Boolean> map;

    static {
        map = new HashMap<>();
    }

    public static void setEvent(Progress progress, boolean b) {
        if (!map.containsKey(progress)) {
            map.put(progress, b);
        }
    }

    public static boolean getEvent(Progress progress) {
        return map.getOrDefault(progress, false);
    }

    public static boolean getEventActivity() {
        boolean isOpen = map.containsKey(Progress.OPEN) && map.get(Progress.OPEN);
        boolean isStarted = map.containsKey(Progress.STARTED) && map.get(Progress.STARTED);
        boolean isClosed = map.containsKey(Progress.CLOSED) && map.get(Progress.CLOSED);
        return isOpen || isStarted || isClosed;
    }

    public static void joinEvent(Player player) {
        WaterListener.attemps.put(player, 0);
        PlayerManager.setMap(player.getName(), true);
        player.sendMessage("§aFoi inviado para o evento, jogadores na partida.");
        player.teleport(Main.config.getLocation("evento"));
        PlayerManager.setMap(player.getName(), true);
    }

    public static void leftEvent(Player player) {
        PlayerManager.setMap(player.getName(), false);
        PlayerManager.remove(player.getName());
        player.sendMessage("§cVoce saiu do evento.");

    }
}

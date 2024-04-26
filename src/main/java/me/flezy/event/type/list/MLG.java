package me.flezy.event.type.list;

import lombok.Getter;
import me.flezy.event.Event;
import me.flezy.event.type.EventType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class MLG extends Event {

    private final Set<Player> completed = new HashSet<>();

    private final Map<Player, Integer> attemptMap = new HashMap<>();

    public MLG() {
        super(EventType.MLG);
    }

    public void setAttempt(Player player) {
        attemptMap.put(player, 1 + (attemptMap.containsKey(player) ? getTotalAttempts(player) : 0));
    }

    public int getTotalAttempts(Player player) {
        return attemptMap.getOrDefault(player, 0);
    }

    public boolean isExceedAttempts(Player player) {
        if (!attemptMap.containsKey(player)) return false;

        return getTotalAttempts(player) >= 3;
    }
}

package me.flezy.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.flezy.event.stage.EventStage;
import me.flezy.event.type.EventType;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class Event {

    private final EventType type;
    private EventStage stage = EventStage.WAITING;

    private final Set<Player> players = new HashSet<>();

    private final long startedAt = System.currentTimeMillis();

    public boolean isStage(EventStage stage) {
        return this.stage.equals(stage);
    }
}

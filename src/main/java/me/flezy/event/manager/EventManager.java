package me.flezy.event.manager;

import lombok.Getter;
import lombok.Setter;
import me.flezy.Main;
import me.flezy.event.Event;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Getter
@Setter
public class EventManager {

    private Event event;

    public boolean hasEvent() {
        return event != null;
    }

    public void send(Player player) {
        event.getPlayers().add(player);

        player.sendMessage("§eVocê entrou no evento! §b" + event.getPlayers().size() + "§e jogadores ativos!");

        Location spawn = Main.getConfig().getLocation("evento");

        if (spawn != null)
            player.teleport(spawn);
        else
            player.sendMessage("§cNão foi possível encontrar o spawn do evento!");
    }

    public void leave(Player player) {
        event.getPlayers().remove(player);

        player.sendMessage("§cVocê saiu do evento!");
        player.teleport(player.getWorld().getSpawnLocation());
    }

}

package me.flezy;

import lombok.Getter;
import me.flezy.command.EventCommand;
import me.flezy.config.LocationConfig;
import me.flezy.event.Event;
import me.flezy.event.manager.EventManager;
import me.flezy.event.type.EventType;
import me.flezy.event.type.list.MLG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin implements Listener {

    @Getter
    private final static EventManager eventManager = new EventManager();

    @Getter
    private static LocationConfig locationConfig;

    @Override
    public void onLoad() {
        locationConfig = new LocationConfig(this);
    }

    @Override
    public void onEnable() {
        getServer().getCommandMap().register("evento", new EventCommand());

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void fall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (!eventManager.hasEvent() || !eventManager.getEvent().getType().equals(EventType.MLG)) return;

            Location spawn = locationConfig.getLocation("evento");

            MLG mlg = (MLG) eventManager.getEvent();

            if (mlg.getPlayers().contains(player) && event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                if (mlg.getCompleted().contains(player)) return;

                event.setCancelled(true);

                if (mlg.getTotalAttempts(player) < 3) {
                    mlg.setAttempt(player);

                    player.sendMessage("§cVocê errou o MLG! " + (3 - mlg.getTotalAttempts(player)) + " tentativas restantes!");

                    player.teleport(spawn);
                } else {
                    player.sendMessage("§cVocê perdeu o MLG!");

                    eventManager.leave(player);
                }
            }
        }
    }

    @EventHandler
    public void bucket(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();

        if (!eventManager.hasEvent() || !eventManager.getEvent().getType().equals(EventType.MLG)) return;

        Material bucket = event.getBucket();
        Location location = player.getLocation(), spawn = locationConfig.getLocation("evento");

        MLG mlg = (MLG) eventManager.getEvent();


        if (mlg.getPlayers().contains(player) && bucket.equals(Material.WATER_BUCKET) && event.getBlockFace().equals(BlockFace.UP)
                && location.getY() != spawn.getY() && location.subtract(0, 3, 0).getBlock().getType() != Material.AIR) {
            player.sendMessage("§aMLG completado com sucesso!");

            mlg.getCompleted().add(player);

            Bukkit.getScheduler().runTaskLater(this, () -> mlg.getCompleted().remove(player), 20L);
        }
    }
}

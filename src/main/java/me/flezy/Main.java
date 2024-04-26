package me.flezy;

import lombok.Getter;
import me.flezy.command.EventCommand;
import me.flezy.config.LocationConfig;
import me.flezy.event.manager.EventManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private final static EventManager eventManager = new EventManager();

    @Getter
    private static LocationConfig config;

    @Override
    public void onLoad() {
        config = new LocationConfig(this);
    }

    @Override
    public void onEnable() {
        getServer().getCommandMap().register("evento", new EventCommand());
    }
}

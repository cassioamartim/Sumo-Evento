package me.flezy;

import lombok.Getter;
import me.flezy.api.EventManager;
import me.flezy.configuration.Config;
import me.flezy.executor.Event;
import me.flezy.listener.ServerListener;
import me.flezy.listener.WaterListener;
import me.flezy.setter.Progress;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    public static Main instance;

    public  static Config config;

    static {
        Main manager;
        Config config;
    }
    @Override
    public void onEnable() {
        config = new Config(this);
        instance = this;
        load();
    }


    @Override
    public void onDisable() {
    }

    @Override
    public void onLoad() {
        EventManager.getMap().clear();
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.kickPlayer("§aServidor reniciando.");
        });
    }

    void load(){
        getCommand("evento").setExecutor(new Event());
        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);
        Bukkit.getPluginManager().registerEvents(new  WaterListener(), this);

    }
}
